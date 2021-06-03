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
}
