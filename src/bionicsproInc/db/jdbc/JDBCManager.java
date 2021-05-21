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
			String sql1 = "CREATE TABLE products " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL UNIQUE, " + " bodypart  TEXT NOT NULL," + " price REAL NOT NULL,"
					+ " date_creation DATE NOT NULL,"+ " engineer_id INTEGER NOT NULL,"
					+ " FOREIGN KEY (engineer_id) REFERENCES engineer(id))";
			stmt1.executeUpdate(sql1);
			stmt1.close();

			Statement stmt2 = c.createStatement();
			String sql2 = "CREATE TABLE material " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name     TEXT     NOT NULL, " + " price REAL NOT NULL," + " amount   INTEGER	 NOT NULL)";
			stmt2.executeUpdate(sql2);
			stmt2.close();

			Statement stmt3 = c.createStatement();
			String sql3 = "CREATE TABLE customer " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name     TEXT     NOT NULL, " + " phone INTEGER NOT NULL," + " street TEXT NOT NULL," 
					+ " city TEXT NOT NULL," + " postal_code INTEGER NOT NULL,"
					+ " role_id INTEGER NOT NULL)";
			stmt3.executeUpdate(sql3);
			stmt3.close();

			Statement stmt4 = c.createStatement();
			String sql4 = "CREATE TABLE engineer " + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name_surname     TEXT     NOT NULL UNIQUE, " + " contract_starting_date DATE NOT NULL UNIQUE," 
					+ " contract_ending_date DATE NOT NULL," + " salary REAL NOT NULL," + " bonus REAL NOT NULL," 
					+ " experience_in_years INTEGER NOT NULL," + " date_of_birth DATE NOT NULL," + " role_id INTEGER NOT NULL)";
			stmt4.executeUpdate(sql4);
			stmt4.close();

			Statement stmt5 = c.createStatement();
			String sql5 = "CREATE TABLE characteristics " + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " product_id INTEGER NOT NULL," + " dimentions TEXT NOT NULL," + " weight REAL NOT NULL,"
					+ " joint_numb INTEGER NOT NULL," + " flexibility_scale INTEGER NOT NULL,"
					+ " FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE)";
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
			String sql7 = "CREATE TABLE products_materials "
					+ "(product_id  INTEGER REFERENCES products(id) ON DELETE CASCADE,"
					+ " material_id INTEGER REFERENCES material(id)," + " PRIMARY KEY (product_id,material_id))";
			stmt7.executeUpdate(sql7);
			stmt7.close();

			Statement stmt8 = c.createStatement();
			String sql8 = "CREATE TABLE products_orders " + "(product_id INTEGER NOT NULL,"
					+ " order_id INTEGER NOT NULL," + " FOREIGN KEY (product_id) REFERENCES products(id),"
					+ " FOREIGN KEY (order_id) REFERENCES orders(id)," + " UNIQUE (product_id, order_id))";
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

	// ADD NEW THINGS IN DATABASE

	public void addProduct(Product p, int engId) {
		try {
			String sql = "INSERT INTO products (name,bodypart,price,date_creation,engineer_id) " + " VALUES(?,?,?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, p.getName());
			prep.setString(2, p.getBodypart());
			prep.setFloat(3, p.getPrice());
			prep.setDate(4, p.getDate_creation());
			prep.setInt(5, engId);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void addMatIntoProd(int prod_id, int mat_id) {
		try {
			String sql = "INSERT INTO products_materials (product_id, material_id) VALUES (?, ?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, prod_id);
			prep.setInt(2, mat_id);
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addCharacteristic(Characteristic ch, int prod_id) {
		try {
			String sql = "INSERT INTO characteristics (product_id, dimentions,weight,joint_numb,flexibility_scale) "
					+ "VALUES(?,?,?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, prod_id);
			prep.setString(2, ch.getDimentions());
			prep.setFloat(3, ch.getWeight());
			prep.setInt(4, ch.getJoints_numb());
			prep.setInt(5, ch.getFlexibilty_scale());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCustomer(Customer cust) {
		try {
			String sql = "INSERT INTO customer (name, phone, street, city, postal_code, role_id) "
					+ " VALUES (?,?,?,?,?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, cust.getName());
			prep.setInt(2, cust.getPhone());
			prep.setString(3, cust.getStreet());
			prep.setString(4, cust.getCity());
			prep.setInt(5, cust.getPostal_code());
			prep.setInt(6, cust.getRole_id());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void addEngineer(Engineer eng) {
		try {
			String sql = " INSERT INTO engineer (name_surname, contract_starting_date, contract_ending_date,salary, bonus,"
					+ " experience_in_years, date_of_birth, role_id) " + " VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, eng.getName_surname());
			prep.setDate(2, eng.getContract_strating_date());
			prep.setDate(3, eng.getContract_ending_date());
			prep.setFloat(4, eng.getSalary());
			prep.setFloat(5, eng.getBonus());
			prep.setInt(6, eng.getExperience_in_years());
			prep.setDate(7, eng.getDate_of_birth());
			prep.setInt(8, eng.getRole_id());
			prep.executeUpdate();
			prep.close();
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

	// GETS
	public Product getProduct(String name) {
		try {
			String sql = "SELECT * FROM products WHERE name=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bodypart = rs.getString("bodypart");
				float price = rs.getFloat("price");
				Date date_creation = rs.getDate("date_creation");
				return new Product(id, name, bodypart, price, date_creation);
			}
			prep.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getProductId(String nameP) {
		int id = 0;
		try {
			String sql = "SELECT id FROM products WHERE name=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%" + nameP + "%");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
			}
			prep.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public float getProductById(int id) {
		float price = 0;
		try {
			String sql = "SELECT price FROM products WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				price = rs.getFloat("price");
			}
			prep.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return price;
	}

	public Material getMaterial(String name) {
		try {
			String sql = "SELECT * FROM material WHERE name= ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				float price = rs.getFloat("price");
				int amount = rs.getInt("amount");

				return new Material(id, name, price, amount);
			}
			prep.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Engineer getEngineerById(int id) {
		try {
			String sql = "SELECT name_surname,contract_ending_date FROM engineer WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				String name=rs.getString("name_surname");
				Date contract_end_date= rs.getDate("contract_ending_date d");
				return new Engineer(name,contract_end_date);
			}
			prep.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	// METHODS FOR THE MENU

	// DELETES
	// REMOVES THE PRODUCT FROM THE DATABASE
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

	// DELETES A PRODUCT FROM THE CART
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
	
	//DELETE ENGINEER
	public void deleteEngineer(int engId) {
		try {
			String sql = "DELETE FROM engineer WHERE id = ? ";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, engId);
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// LISTS AND VIEW
	// LISTS BODYPARTS
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

	// LIST PRODUCTS WHICH BODYPART IS THE ONE CHOSEN BY THE USER
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

	// METHOD THAT SHOWS US THE BONUS OF THE ENGINEER
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

	// METHOD THAT SHOWS US THE CART
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

	// METHOD THAT LET THE CUSTOMER SEE ORDERS THAT HE ALREDY DONE
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
				Date dateO = rs.getDate("date_order");
				Order o = new Order(ids, dateO);
				orders.add(o);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	// METHOD USE TO SEE THE PRODUCTS CREATED BY THAT ENGINEER
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

	// METHOD USE TO SEE THE CHARACTERISTIC IN CERTAIN PRODUCT
	public Characteristic viewCharacteristicsFromProduct(int prodId) {
		try {
			String sql = "SELECT * FROM characteristics WHERE product_id = ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, prodId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String dimentions = rs.getString("dimentions");
				float weight = rs.getFloat("weight");
				int joints_numb = rs.getInt("joint_numb");
				int flexibilty_scale = rs.getInt("flexibility_scale");
				return new Characteristic(dimentions, weight, joints_numb, flexibilty_scale);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// METHOD USE TO SEE THE MATERIALS IN CERTAIN PRODUCT
	public ArrayList<Material> viewMaterialsFromProduct(int prodId) {
		ArrayList<Material> materials = new ArrayList<Material>();
		try {
			String sql = "SELECT m.* FROM products_materials as pm JOIN material as m "
					+ "ON pm.material_id = m.id JOIN products as p ON pm.product_id = p.id WHERE p.id = ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, prodId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int mat_id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				int amount = rs.getInt("amount");
				Material m = new Material(mat_id, name, price, amount);

				materials.add(m);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return materials;
	}

	// METHOD USE TO GET THE MATERIALS SAVE IN THE DATABASE
	public ArrayList<Material> ListMaterials() {
		ArrayList<Material> materials = new ArrayList<Material>();
		try {
			String sql = "SELECT * FROM material";
			PreparedStatement stmt = c.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				int amount = rs.getInt("amount");
				Material m = new Material(id, name, price, amount);
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

	// List of all products in database
	public List<Product> ListProd() {
		List<Product> products = new ArrayList<Product>();
		try {
			String sql = "SELECT id,name FROM products";
			PreparedStatement stm = c.prepareStatement(sql);
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

	// UPDATE PRODUCT
	public void updateProduct(Product p, float newPrice) {
		try {
			String sql = "UPDATE products SET price=? WHERE name=?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setFloat(1, newPrice);
			stmt.setString(2, p.getName());
			stmt.executeUpdate();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateEngineerContractDate(Engineer en, Date last_date) {
		try {
			String sql = "UPDATE engineer SET contract_ending_date=? WHERE name=?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setDate(1, last_date);
			stmt.setString(2, en.getName_surname());
			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}