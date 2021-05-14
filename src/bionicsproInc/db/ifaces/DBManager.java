package bionicsproInc.db.ifaces;

import java.util.List;

import bionicsproInc.db.pojos.*;

public interface DBManager {

	public void connect();

	public void disconnect();

	public void addProduct(Product p);

	public void addMaterial(Material m);

	public void addMatIntoProd(Product p, Material m);

	public void addCustomer(Customer cust);

	public void addEngineer(Engineer eng, Product pr);
	
	public void addCharacteristic(Characteristic ch, Product pr);
	
	public void addOrder(Customer cust, Order o);
	
	public void addProducts_orders(Product product, Order order);

	public List<String> viewBodyparts();

	public List<Product> searchProductByBody(String bodypart);

	public void removeProd(int prodId);

	public Engineer viewBonus(int engId);

	/*public void addToOrder(Product product, Order order);*/

	/*public List<String> viewCart(Order o);*/

	/*public List<Integer> viewOtherOrders(int id);*/

	public List<String> viewProjectAchieved(int engId);

	/*public void deleteProdFromCart(String name, Order o);*/

	public List<Characteristic> viewCharacteristicsFromProduct(int prodId);

	public List<Material> viewMaterialsFromProduct(int prodId);

	public List<Product> viewProductsFromOrder(int orderId);

}
