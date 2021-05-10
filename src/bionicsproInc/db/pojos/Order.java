package bionicsproInc.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5234210426301713165L;
	private int id;
	private Date date_order;
	private ArrayList<Product> products;
	

	public Order() {
		super();
	}

	public Order(int id, Date date_order, ArrayList<Product> products) {
		super();
		this.id = id;
		this.date_order = date_order;
		this.products = products;
	}

	public int getOrder_id() {
		return id;
	}

	public void setOrder_id(int order_id) {
		this.id = order_id;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	public Date getDate_order() {
		return date_order;
	}

	public void setDate_order(Date date_order) {
		this.date_order = date_order;
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public void removeProduct(int id) {
		for (int i = 1; i <= this.products.size() - 1; i++) {
			int pId = this.products.get(i).getId();
			if (pId == id) {
				this.products.remove(i);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date_order == null) ? 0 : date_order.hashCode());
		result = prime * result + id;
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (date_order == null) {
			if (other.date_order != null)
				return false;
		} else if (!date_order.equals(other.date_order))
			return false;
		if (id != other.id)
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date_order=" + date_order + ", products=" + products + "]";
	}
	
	
	
	

}
