package bionicsproInc.db.ifaces;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import bionicsproInc.db.pojos.*;

public interface DBManager {

	public void connect();

	public void disconnect();

	public void addProduct(Product p, int engId);
	
	public void addMaterial(Material m);
	
	public void addMatIntoProd(int prod_id, int mat_id);
	
	public void addCharacteristic(Characteristic ch, int prod_id);
	
	public void addCustomer(Customer cust);
	
	public void addEngineer(Engineer eng);
		
	public void addOrder(Customer cust, Order o);
	
	public void addProducts_orders(Product product, Order order);
	
	public Product getProduct(String name);
	
	public float getProductById(int id);
	
	public int getProductId(String nameP);
	
	public Material getMaterial(String name);
	
	public Engineer getEngineerById(int id);
	
	public void removeProd(int prodId);
	
	public void deleteProdFromCart(String name, Order o);
	
	public void deleteEngineer(int engId);
	
	public List<String> viewBodyparts();
	
	public List<Product> searchProductByBody(String bodypart);
	
	public Engineer viewBonus(int engId);
	
	public List<String> viewCart(Order o);
	
	public List<Order> viewOtherOrders(int id);
	
	public List<String> viewProjectAchieved(int engId);
	
	public Characteristic viewCharacteristicsFromProduct(int prodId);
	
	public ArrayList<Material> viewMaterialsFromProduct(int prodId);
	
	public ArrayList<Material> ListMaterials();
	
	public List<Product> viewProductsFromOrder(int orderId);
	
	public List<Product> ListProd();
	
	public void updateProduct(Product p, float newPrice);
	
	public void updateEngineerContractDate(Engineer e, Date last_date);

}
