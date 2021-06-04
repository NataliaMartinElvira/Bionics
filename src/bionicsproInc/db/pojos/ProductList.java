package bionicsproInc.db.pojos;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name= "ProductList")
@XmlType(propOrder = {"products"})
public class ProductList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2299331525295857688L;
	
	@XmlElement
	private ArrayList<Product> products;
	
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	public ProductList() {
		super();
	}
	public ProductList(ArrayList<Product> products) {
		super();
		this.products = products;
	}
	public int getNumProds() {
		int cont=products.size();
		return cont;
	}
	public Product getProd(int i) {
		Product p= new Product();
		p=products.get(i);
		return p;
	}
	@Override
	public String toString() {
		return "ProductList [products=" + products + "]";
	}
	
}
