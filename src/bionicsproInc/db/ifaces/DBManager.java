package bionicsproInc.db.ifaces;

import java.util.List;

import bionicsproInc.db.pojos.*;

public interface DBManager {

	public void connect();

	public void disconnect();

	public void addProduct(Product p);
	
	public Product getProduct (String name);

	public void addMaterial(Material m);
	
	public Material getMaterial(String name);

	public void addMatIntoProd(int prod_id, int mat_id);

	public void addCustomer(Customer cust);

	public void addEngineer(Engineer eng, Product pr);
	
	public void addCharacteristic(Characteristic ch, int prod_id);
	
	public void addOrder(Customer cust, Order o);
	
	public void addProducts_orders(Product product, Order order);

	public List<String> viewBodyparts();

	public List<Product> searchProductByBody(String bodypart);

	public void removeProd(int prodId);

	public Engineer viewBonus(int engId);

	public List<String> viewCart(Order o);

	public List<Order> viewOtherOrders(int id);

	public List<String> viewProjectAchieved(int engId);

	public void deleteProdFromCart(String name, Order o);

	public List<Characteristic> viewCharacteristicsFromProduct(int prodId);

	public List<Material> viewMaterialsFromProduct(int prodId);

	public List<Product> viewProductsFromOrder(int orderId);
	
	public void updateProduct(Product p, float newPrice);
	
	public void updateCharact(Characteristic ch, String newDimentions);

}
