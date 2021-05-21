package bionicsproInc.ui;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.List;
import bionicsproInc.db.ifaces.*;
import bionicsproInc.db.jdbc.JDBCManager;
import bionicsproInc.db.jpa.JPAUserManager;
import bionicsproInc.db.pojos.*;
import bionicsproInc.db.pojos.users.*;

public class Menu {

	private static DBManager dbman = new JDBCManager();
	private static UserManager userman = new JPAUserManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Order temporaryOrder = new Order();
	private static DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static JDBCManager JDBCmethod = new JDBCManager();
	private static List<Product> p;

	public static void main(String[] args) throws Exception {
		dbman.connect();
		userman.connect();
		do {
			System.out.println("\n Choose an option:");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				register();
				break;
			case 2:
				login();
				break;
			case 0:
				dbman.disconnect();
				userman.disconnect();
				System.exit(0);
				break;
			default:
				break;
			}

		} while (true);

	}

	private static void register() throws Exception {
		System.out.println(userman.getRoles());
		System.out.println("Type the chosen role ID:");
		int id = Integer.parseInt(reader.readLine());
		Role role = userman.getRole(id);
		switch(id) {
		case 1: 
			System.out.println("Please, write your email address: ");
			String emailEng = reader.readLine();
			System.out.println("Please, write your password:");
			String passwordEng = reader.readLine();
			System.out.println("Introduce your data: \n");
			System.out.println("Enter your name: ");
			String name=reader.readLine();
			System.out.println("Contract starting date (yyyy-MM-dd): ");
			LocalDate startingDate=LocalDate.parse(reader.readLine(), formatter);
			Date startDate=Date.valueOf(startingDate);
			System.out.println("Contract ending date (yyyy-MM-dd): ");
			LocalDate endingDate=LocalDate.parse(reader.readLine(), formatter);
			Date endDate=Date.valueOf(endingDate);
			System.out.println("Salary: ");
			float salary=Float.parseFloat(reader.readLine());
			System.out.println("Bonus: ");
			float bonus=Float.parseFloat(reader.readLine());
			System.out.println("Experience in years: ");
			int experience=Integer.parseInt(reader.readLine());
			System.out.println("Date of birth(yyyy-MM-dd): ");
			LocalDate birthDate=LocalDate.parse(reader.readLine(), formatter);
			Date birth_Date=Date.valueOf(birthDate);
			Engineer eng=new Engineer(name,startDate,endDate,salary,bonus,experience,birth_Date,id);
			Product p= new Product();
			dbman.addEngineerWhithoutProd(eng);
			//necesitamos pasar un product a engineer
			MessageDigest md1 = MessageDigest.getInstance("MD5");
			md1.update(passwordEng.getBytes());
			byte[] hash1 = md1.digest();
			User userE= new User(emailEng,hash1,role);
			userman.newUser(userE);
			break;
		case 2:
			System.out.println("Please, write your email address: ");
			String emailCust = reader.readLine();
			System.out.println("Please, write your password:");
			String passwordCust = reader.readLine();
			System.out.println("Introduce your data: \n");
			System.out.println("Enter your name: ");
			String custName= reader.readLine();
			System.out.println("Enter your phone number: ");
			int phone=Integer.parseInt(reader.readLine());
			System.out.println("Street: ");
			String street=reader.readLine();
			System.out.println("City: ");
			String city=reader.readLine();
			System.out.println("Postal code: ");
			int postalCode=Integer.parseInt(reader.readLine());
			MessageDigest md2 = MessageDigest.getInstance("MD5");
			md2.update(passwordCust.getBytes());
			byte[] hash2 = md2.digest();
			User userC= new User(emailCust,hash2,role);
			Customer cust=new Customer(custName,phone,street,city,postalCode,id);
			dbman.addCustomer(cust);
			userman.newUser(userC);
			break;
		}
		System.out.println("You have registered properly \n");
	}

	private static void login() throws Exception {
		System.out.println("Please, write your email address:");
		String email = reader.readLine();
		System.out.println("Please, write your password:");
		String password = reader.readLine();
		User user = userman.checkPassword(email, password);
		if (user == null) {
			System.out.println("Wrong email or password");
			return;
		} else if (user.getRole().getName().equalsIgnoreCase("engineer") ) {
			engineerMenu();
		} else if (user.getRole().getName().equalsIgnoreCase("customer")) {
			customerMenu();
		}
	}

	private static void engineerMenu() throws Exception {
		do {
			System.out.println("\n Choose an option:");
			System.out.println("1. View product");
			System.out.println("2. Add new material");
			System.out.println("3. Add new product");
			System.out.println("4. Remove product");
			System.out.println("5. See existing projects");
			System.out.println("6. View bonus");
			System.out.println("7. Update a product's price");
			System.out.println("8. Update contract ending date");
			System.out.println("9. Quit job :) ");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				viewProduct();
				break;
			case 2:
				addMaterial();
				break;

			case 3:
				addProduct();
				break;

			case 4:
				removeProduct();
				break;

			case 5:

				seeProject();
				break;

			case 6:
				viewBonus();
				break;
				
			case 7: 
				updateProd();
				break;
			case 8: 
				updateContract();
				break;
			case 9: 
				becomeNini();
				break;

			case 0:
				return;

			default:
				break;

			}
		} while (true);
	}

	private static void customerMenu() throws Exception {
		do {
			System.out.println("\n Choose an option:");
			System.out.println("1. View product");
			System.out.println("2. Make purchase");
			System.out.println("3. Change product");
			System.out.println("4. See other purchases");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				//viewProductC();
				break;

			case 2:
				/*makePurchase();*/
				break;

			case 3:
				changeProduct();
				break;

			case 4:
				seeOtherPurchases();
				break;

			case 0:
				return;

			default:
				break;

			}
		} while (true);
	}

	// Engineer OPTION 1
	private static void viewProduct() throws Exception {
		System.out.println("Choose a bodypart:");
		List <String> bodyparts=dbman.viewBodyparts();
		for (int j=0; j<bodyparts.size(); j++) {
			System.out.println(bodyparts.get(j));
		}
		String name = reader.readLine();
		List <Product> prods=dbman.searchProductByBody(name);
		for (Product product : prods) {
			System.out.println("Id: "+product.getId() +". " + product.getName());
		}
		System.out.println("Introduce id of the product: ");
		int id = Integer.parseInt(reader.readLine());
		float price=dbman.getProductById(id);
		System.out.println("Price: "+price);
		Characteristic ch=dbman.viewCharacteristicsFromProduct(id);
		System.out.println("Dimensions: "+ch.getDimentions()+"; Weight: "+ch.getWeight()+";Number of joints: "+ch.getJoints_numb()
		+";Flexibility Scale: "+ch.getFlexibilty_scale());	
		List <Material> mats=dbman.viewMaterialsFromProduct(id);
		for (Material material : mats) {
			System.out.println("Material id: "+material.getId() +";Name of material: " + material.getName() +";Price: " 
					+ material.getPrice() +";Amount: " + material.getAmount());
		}
	}
	
	// Engineer OPTION 2
	private static void addMaterial() throws Exception{
		try {
			System.out.println("Name of material: ");
			String nameMat=reader.readLine();
			System.out.println("Price:");
			float pMat=Float.parseFloat(reader.readLine());
			System.out.println("Amount: ");
			int amount=Integer.parseInt(reader.readLine());
			Material m=new Material(nameMat,pMat,amount);
			dbman.addMaterial(m);		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	// Engineer OPTION 3
	// NEED TO SPEAK ABOUT HOW TO ADD PHOTO ATTRIBUTE FROM A STRING ...
	private static void addProduct() throws Exception {
		try {
			System.out.println("Introduce product name: ");
			String name=reader.readLine();
			System.out.println("Introduce bodypart: ");
			String bodypart=reader.readLine();
			System.out.println("Introduce price: ");
			float price=Float.parseFloat(reader.readLine());
			System.out.println("Introduce date of creation (yyyy-MM-dd): ");
			LocalDate creation_date=LocalDate.parse(reader.readLine(),formatter);
			Product np=new Product(name,bodypart,price,Date.valueOf(creation_date));
			dbman.addProduct(np);
			System.out.println("Now you need to list the materials\n");
			System.out.println("How many materials does the product have? ");
			int cont=Integer.parseInt(reader.readLine());
			for(int i=0;i<cont;i++){
				List <Material> mats=dbman.ListMaterials();
				for (Material material : mats) {
					System.out.println("Material id: "+material.getId() +";Name of material: " + material.getName() +";Price: " 
							+ material.getPrice() +";Amount: " + material.getAmount());
				}
				System.out.println("Choose a material: ");
				System.out.println("CHECK THE AMOUNT ");
				int mater_id = Integer.parseInt(reader.readLine());
				dbman.addMatIntoProd(dbman.getProduct(name).getId(), mater_id);
			}
			/*check this*/
			System.out.println("Describe characteristics: ");
			System.out.println("Dimensions (Length x Width x Height ): ");
			String dimentions=reader.readLine();
			System.out.println("Weight:");
			float weight=Float.parseFloat(reader.readLine());
			System.out.println("Number of joints: ");
			int nJoints=Integer.parseInt(reader.readLine());
			System.out.println("Flexibility Scale: ");
			int fScale=Integer.parseInt(reader.readLine());
			Characteristic cha=new Characteristic(dimentions,weight,nJoints,fScale);
			dbman.addCharacteristic(cha,dbman.getProduct(name).getId());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	// Engineer OPTION 5
	private static void seeProject() throws Exception {
		try {
			System.out.println("Introduce your ID: ");
			int id = Integer.parseInt(reader.readLine());
			dbman.viewProjectAchieved(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Engineer OPTION 4
	
	private static void removeProduct() throws Exception {
		try {
			List <Product> prods=dbman.ListProd();
			for (Product product : prods) {
				System.out.println("Id: "+product.getId() +". " + product.getName());
			}
			System.out.println("Introduce the product ID: ");
			int id = Integer.parseInt(reader.readLine());
			dbman.removeProd(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Engineer OPTION 6
	private static void viewBonus() throws Exception {
		System.out.println("Introduce your ID:");
		int id = Integer.parseInt(reader.readLine());
		dbman.viewBonus(id);
	}
	
	//Engineer OPTION 7
	
	private static void updateProd() throws Exception{
		List <Product> prods=dbman.ListProd();
		for (Product product : prods) {
			System.out.println("Id: "+product.getId() +". " + product.getName());
		}
		System.out.println("Choose name of the product you want to update the price");
		String name = reader.readLine();
		System.out.println("Give me the new price");
		float price=Float.parseFloat(reader.readLine());
		dbman.updateProduct(dbman.getProduct(name),price);
	}
	
	//Engineer OPTION 8
	private static void updateContract()throws Exception{
		System.out.println("Renew Contract date: ");
		LocalDate contractEndingDate=LocalDate.parse(reader.readLine(), formatter);
		Date contract_end=Date.valueOf(contractEndingDate);
		System.out.println("Confirm your id: ");
		int e_id=Integer.parseInt(reader.readLine());
		dbman.updateEngineerContractDate(dbman.getEngineerById(e_id),contract_end);
	}
	
	//Engineer OPTION 9
	private static void becomeNini() {
		try {
			System.out.println("Confirm your id for freedom: ");
			int id=Integer.parseInt(reader.readLine());
			userman.quitEngineer(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// CUSTOMER OPTION 1
	/*private static void viewProductC() throws Exception {
		try {
			System.out.println("Choose a bodypart:");
			dbman.viewBodyparts();
			String name = reader.readLine();
			dbman.searchProductByBody(name);
			System.out.println("Choose a product: ");
			int id = Integer.parseInt(reader.readLine());
			dbman.viewCharacteristicsFromProduct(id);
			dbman.viewMaterialsFromProduct(id);
			System.out.println("Do you want to add it to your cart? 1->YES 0->NO");
			int op=Integer.parseInt(reader.readLine());
			if(op==1) {
				for(int i=1;i<p.size(); i++) {
					if (p.get(i).getId()==id) {
						Product pr=p.get(i);
						dbman.addProducts_orders(pr, temporaryOrder);
					}
				}
			}
			else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	// Customer OPTION 2
	/*private static void makePurchase() throws Exception {
		try {
			System.out.println("These are the products: \n");
			dbman.viewCart(temporaryOrder);
			System.out.println("Are you sure? " + " 1->YES 0->NO");
			int option = Integer.parseInt(reader.readLine());
			if (option == 1) {
				dbman.addOrder(temporaryOrder);
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	// Customer OPTION 3
	private static void changeProduct() throws Exception {
		try {
			System.out.println("Choose the product you want to remove from cart:");
			dbman.viewCart(temporaryOrder);
			String pName = reader.readLine();
			System.out.println("Are you sure you want to delete that product? " + " 1->YES 0->NO");
			int option = Integer.parseInt(reader.readLine());
			if (option == 1) {
				dbman.deleteProdFromCart(pName, temporaryOrder);
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CUSTOMER OPTION 4
	private static void seeOtherPurchases() throws Exception {
		try {
			System.out.println("Confirm your ID: ");
			int id = Integer.parseInt(reader.readLine());
			dbman.viewOtherOrders(id);
			System.out.println("Do you want to select a product? 1->YES 0->NO");
			int option = Integer.parseInt(reader.readLine());
			if (option == 1) {
				System.out.println("Select id of product: ");
				int p_id = Integer.parseInt(reader.readLine());
				dbman.viewCharacteristicsFromProduct(p_id);
				dbman.viewMaterialsFromProduct(p_id);
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}