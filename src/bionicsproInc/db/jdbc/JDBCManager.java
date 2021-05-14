package bionicsproInc.db.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bionicsproInc.db.ifaces.DBManager;
import bionicsproInc.db.pojos.*;

public class JDBCManager implements DBManager {
	private Connection c;

	public void connect() {
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/bionicsproInc.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			this.createTables();
		} catch (SQLException sqlE) {
			System.out.println("There was a database exception.");
			sqlE.printStackTrace();
		} catch (Exception e) {
			System.out.println("There was a general exception.");
			e.printStackTrace();
		}
	}

	private void createTables() {

		try {
			Statement stmt1 = c.createStatement();
			String sql1 = "CREATE TABLE products " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL UNIQUE, "
					+ " bodypart  TEXT NOT NULL," + " price REAL NOT NULL," + " date_creation DATE NOT NULL)";
			stmt1.executeUpdate(sql1);
			stmt1.close();

			Statement stmt2 = c.createStatement();
			String sql2 = "CREATE TABLE material " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name     TEXT     NOT NULL UNIQUE, " + " price REAL NOT NULL," + " amount   INTEGER	 NOT NULL)";
			stmt2.executeUpdate(sql2);
			stmt2.close();

			Statement stmt3 = c.createStatement();
			String sql3 = "CREATE TABLE customer " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name     TEXT     NOT NULL, " + " phone INTEGER NOT NULL," + " email TEXT NOT NULL,"
					+ " street TEXT NOT NULL," + " city TEXT NOT NULL," + " postal_code INTEGER NOT NULL)";
			stmt3.executeUpdate(sql3);
			stmt3.close();

			Statement stmt4 = c.createStatement();
			String sql4 = "CREATE TABLE engineer " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ "product_id INTEGER NOT NULL," + " name_surname     TEXT     NOT NULL UNIQUE, "
					+ " contract_starting_date DATE NOT NULL UNIQUE," + " contract_ending_date DATE NOT NULL,"
					+ " salary REAL NOT NULL," + " bonus REAL NOT NULL," + " experience_in_years INTEGER NOT NULL,"
					+ " date_of_birth DATE NOT NULL," + " FOREIGN KEY (product_id) REFERENCES products(id))";
			stmt4.executeUpdate(sql4);
			stmt4.close();

			Statement stmt5 = c.createStatement();
			String sql5 = "CREATE TABLE characteristics " + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " product_id INTEGER NOT NULL," + " dimentions TEXT NOT NULL," + " weight REAL NOT NULL,"
					+ " joint_numb INTEGER NOT NULL," + " flexibility_scale INTEGER NOT NULL,"
					+ " FOREIGN KEY (product_id) REFERENCES products(id))";
			stmt5.executeUpdate(sql5);
			stmt5.close();

			Statement stmt6 = c.createStatement();
			String sql6 = "CREATE TABLE orders " + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " customer_id INTEGER NOT NULL," + " date_order DATE NOT NULL,"
					+ " FOREIGN KEY (customer_id) REFERENCES customer(id))";
			stmt6.executeUpdate(sql6);
			stmt6.close();

			// now we create the table that references the N-N relationships

			Statement stmt7 = c.createStatement();
			String sql7 = "CREATE TABLE products_materials " + "(product_id  INTEGER REFERENCES products(id),"
					+ " material_id INTEGER REFERENCES material(id))";
			stmt7.executeUpdate(sql7);
			stmt7.close();

			Statement stmt8 = c.createStatement();
			String sql8 = "CREATE TABLE products_orders " + "(product_id INTEGER REFERENCES products(id),"
					+ " order_id INTEGER REFERENCES orders(id))";
			stmt8.executeUpdate(sql8);
			stmt8.close();

		} catch (SQLException e) {
			if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}
		}
	}

	public void disconnect() {
		try {
			// Close database connection
			c.close();
			System.out.println("Database is closed .");
		} catch (SQLException e) {
			System.out.println("There was a problem while closing the database connection.");
			e.printStackTrace();
		}
	}

	public void addProduct(Product p) {
		try {
			String sql = "INSERT INTO products (name,bodypart,price,date_creation) " + " VALUES(?,?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, p.getName());
			prep.setString(2, p.getBodypart());
			prep.setFloat(3, p.getPrice());
			prep.setDate(4, p.getDate_creation());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Product getProduct (String nameP) {
		try {
			String sql="SELECT * FROM products WHERE name=?";
			PreparedStatement prep= c.prepareStatement(sql);
			prep.setString(1,"%"+nameP+"%");
			ResultSet rs=prep.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String bodypart=rs.getString("bodypart");
				float price=rs.getFloat("price");
				Date date_creation=rs.getDate("date_creation");
				Product p=new Product(id,name,bodypart,price,date_creation);
				return p;
			}
			prep.close();
			rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addMaterial(Material m) {
		try {
			String sql = "INSERT INTO material (name,price,amount) " + " VALUES(?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, m.getName());
			prep.setFloat(2, m.getPrice());
			prep.setInt(3, m.getAmount());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void addMatIntoProd(Product p, Material m) {
		try {
			String sql = "INSERT INTO products_materials (product_id,material_id) " + "VALUES(?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, p.getId());
			prep.setInt(2, m.getId());
			prep.executeUpdate(sql);
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addCharacteristic(Characteristic ch, Product pr) {
		try {
			String sql = "INSERT INTO characteristics (product_id, dimentions,weight,joints_numb,flexibility_scale) "
					+ "VALUES(?,?,?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, pr.getId());
			prep.setString(2, ch.getDimentions());
			prep.setFloat(3, ch.getWeight());
			prep.setInt(4, ch.getJoints_numb());
			prep.setInt(5, ch.getFlexibilty_scale());
			prep.executeUpdate(sql);
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void addCustomer(Customer cust) {
		try {
			Statement st = c.createStatement();
			String sql = "INSERT INTO customer (name, phone, email, street, city, postal_code) " + " VALUES ('"
					+ cust.getName() + "', '" + cust.getPhone() + "', '" + cust.getEmail() + "','" + cust.getStreet()
					+ "','" + cust.getCity() + "','" + cust.getPostal_code() + "')'";
			st.executeUpdate(sql);
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addEngineer(Engineer eng, Product pr) {
		try {

			Statement stmt = c.createStatement();
			String sql = " INSERT INTO Engineer (product_id, name_surname, contract_starting_date, contract_ending_date,salary, bonus,"
					+ " experience_in_years, date_of_birth) " + " VALUES ('" + pr.getId() + "','"
					+ eng.getName_surname() + "','" + eng.getContract_strating_date() + "','"
					+ eng.getContract_ending_date() + "','" + eng.getSalary() + "','" + eng.getBonus() + "','"
					+ eng.getExperience_in_years() + "','" + eng.getDate_of_birth() + "')'";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addOrder(Customer cust, Order o) {
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO orders (date_order,customer_id) VALUES ('" + o.getDate_order() + "','"
					+ cust.getId() + "')'";
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addProducts_orders(Product product, Order order) {

		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO products_orders (product_id,order_id) " + " VALUES ('" + order.getOrder_id()
					+ "','" + product.getId() + "')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		order.addProduct(product);

	}
	//METHODS FOR THE MENU
	@Override
	public List<String> viewBodyparts() {
		List<String> bodyPart = new ArrayList<String>();
		try {
			String sql = " SELECT DISTINCT bodypart FROM products ";
			PreparedStatement stm = c.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				String part = rs.getString("bodypart");
				bodyPart.add(part);
			}
			rs.close();
			stm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bodyPart;
	}

	@Override
	public List<Product> searchProductByBody(String bodypart) {
		List<Product> products = new ArrayList<Product>();
		try {
			String sql = "SELECT id,name FROM products WHERE bodypart LIKE ?";
			PreparedStatement stm = c.prepareStatement(sql);
			stm.setString(1, "%" + bodypart + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String productname = rs.getString("name");
				Product p = new Product(id, productname);
				products.add(p);
			}
			rs.close();
			stm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void removeProd(int prodId) {
		try {
			String sql = "DELETE FROM products WHERE id = ? ";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, prodId);
			stmt.executeUpdate();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Engineer viewBonus(int engId) {
		try {
			String sql = "SELECT bonus FROM Engineer WHERE id= ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, engId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Engineer(engId, rs.getFloat("bonus"));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// LIKE ADDTOCART- THE SAME FUNCTION
	public List<String> viewCart(Order o) {
		List<String> p_names = new ArrayList<String>();
		try {
			String sql = " SELECT p.name FROM products AS p JOIN order AS or ON or.product_id=p.id WHERE or.order_id= ? ";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, o.getOrder_id());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String productName = rs.getString("name");
				p_names.add(productName);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p_names;
	}

	@Override
	public List<Order> viewOtherOrders(int id) {
		List<Order> orders = new ArrayList<Order>();
		try {
			String sql = " SELECT id,date_order FROM orders WHERE customer_id= ? ";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int ids = rs.getInt("id");
				Date dateO=rs.getDate("date_order");
				Order o=new Order(ids,dateO);
				orders.add(o);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	public List<String> viewProjectAchieved(int engId) {
		List<String> prodname = new ArrayList<String>();
		try {
			String sql = "SELECT p.name FROM Engineer as e JOIN product as p ON p.id=e.product_id WHERE id= ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, engId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String pname = rs.getString("name");
				prodname.add(pname);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prodname;
	}

	public void deleteProdFromCart(String name, Order o) {
		try {
			String sql = "SELECT id FROM products WHERE name = ? ";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("product_id");
				o.removeProduct(id);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Characteristic> viewCharacteristicsFromProduct(int prodId) {
		ArrayList<Characteristic> characteristics = new ArrayList<Characteristic>();
		try {
			String sql = "SELECT c.* FROM characteristics_product as cp JOIN characteristics as c "
					+ "ON cp.characteristic_id = c.id JOIN products as p ON cp.products_id = p.id WHERE p.id = ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, prodId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String dimentions = rs.getString("dimentions");
				float weight = rs.getFloat("weight");
				int joints_numb = rs.getInt("joints_numb");
				int flexibilty_scale = rs.getInt("flexibilty_scale");
				Characteristic ch = new Characteristic(dimentions, weight, joints_numb, flexibilty_scale);
				characteristics.add(ch);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return characteristics;
	}

	public ArrayList<Material> viewMaterialsFromProduct(int prodId) {
		ArrayList<Material> materials = new ArrayList<Material>();
		try {
			String sql = "SELECT m.* FROM products_materials as pm JOIN materials as m "
					+ "ON pm.material_id = m.id JOIN products as p ON pm.products_id = p.id WHERE p.id = ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, prodId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				int amount = rs.getInt("amount");
				Material m = new Material(name, price, amount);

				materials.add(m);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return materials;
	}

	// METHOD USED BY CUSTOMER TO VIEW PRODUCTS OF OTHER ORDERS ALREADY MADE
	public List<Product> viewProductsFromOrder(int orderId) {
		List<Product> products = new ArrayList<Product>();
		try {
			String sql = "SELECT p.*, o.order_id, p.product_id FROM order as o JOIN product as p ON o.product_id=p.id WHERE o.order_id = ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, orderId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String productname = rs.getString("name");
				Product p = new Product(id, productname);
				products.add(p);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;

	}
	//UPDATE PRODUCT
	public void updateProduct(Product p, float newPrice) {
		try {
			String sql= "UPDATE products SET price=? WHERE id=?";
			PreparedStatement stmt=c.prepareStatement(sql);
			stmt.setFloat(1, newPrice);
			stmt.setInt(2, p.getId());
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//UPDATE CHARACTERISTIC
	public void updateCharact(Characteristic ch, String newDimentions) {
		try {
			String sql="UPDATE characteristics SET dimentions=? WHERE id=?";
			PreparedStatement stmt=c.prepareStatement(sql);
			stmt.setString(1, "%"+newDimentions+"%");
			stmt.setInt(2, ch.getId());
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}