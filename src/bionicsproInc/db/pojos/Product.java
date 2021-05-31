package bionicsproInc.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import bionicsproInc.xml.utils.SQLDateAdapter;

@Entity
@Table(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name= "Product")
@XmlType(propOrder = {"name","bodypart","price","date","materials"})
public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2448117025953730410L;
	@XmlTransient
	private int id;
	@XmlElement
	private String name;
	@XmlElement
	private String bodypart;
	@XmlElement
	private Float price;
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date date_creation;
	@XmlTransient
	private byte[] photo;
	@XmlElement(name="Material_Product")
	@XmlElementWrapper(name="materials")
	private ArrayList<Material> mats;
	@XmlTransient
	private Characteristic characteristic;
	private ArrayList<Customer> customers;

	public Product() {
		super();
	}	

	public Product(int id, String name, String bodypart, Float price, Date date_creation, byte[] photo,
			ArrayList<Material> mats, Characteristic characteristic, ArrayList<Customer> customers) {
		super();
		this.id = id;
		this.name = name;
		this.bodypart = bodypart;
		this.price = price;
		this.date_creation = date_creation;
		this.photo = photo;
		this.mats = mats;
		this.characteristic = characteristic;
		this.customers = customers;
	}
	

	public Product(String name, String bodypart, Float price, Date date_creation, byte[] photo,
			ArrayList<Material> mats, Characteristic characteristic, ArrayList<Customer> customers) {
		super();
		this.name = name;
		this.bodypart = bodypart;
		this.price = price;
		this.date_creation = date_creation;
		this.photo = photo;
		this.mats = mats;
		this.characteristic = characteristic;
		this.customers = customers;
	}


	public Product(String name, String bodypart, Float price, Date date_creation) {
		super();
		this.name = name;
		this.bodypart = bodypart;
		this.price = price;
		this.date_creation = date_creation;
	}
	
	public Product(int id, String name, String bodypart, Float price, Date date_creation) {
		super();
		this.id = id;
		this.name = name;
		this.bodypart = bodypart;
		this.price = price;
		this.date_creation = date_creation;
	}
	
	
	public Product(String name) {
		super();
		this.name = name;
	}


	public Product(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBodypart() {
		return bodypart;
	}

	public void setBodypart(String bodypart) {
		this.bodypart = bodypart;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public ArrayList<Material> getMats() {
		return mats;
	}

	public void setMats(ArrayList<Material> mats) {
		this.mats = mats;
	}
	
	public Characteristic getCharacteristic() {
		return characteristic;
	}


	public void setCharacteristic(Characteristic characteristic) {
		this.characteristic = characteristic;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bodypart == null) ? 0 : bodypart.hashCode());
		result = prime * result + ((characteristic == null) ? 0 : characteristic.hashCode());
		result = prime * result + ((customers == null) ? 0 : customers.hashCode());
		result = prime * result + ((date_creation == null) ? 0 : date_creation.hashCode());
		result = prime * result + id;
		result = prime * result + ((mats == null) ? 0 : mats.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(photo);
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		Product other = (Product) obj;
		if (bodypart == null) {
			if (other.bodypart != null)
				return false;
		} else if (!bodypart.equals(other.bodypart))
			return false;
		if (characteristic == null) {
			if (other.characteristic != null)
				return false;
		} else if (!characteristic.equals(other.characteristic))
			return false;
		if (customers == null) {
			if (other.customers != null)
				return false;
		} else if (!customers.equals(other.customers))
			return false;
		if (date_creation == null) {
			if (other.date_creation != null)
				return false;
		} else if (!date_creation.equals(other.date_creation))
			return false;
		if (id != other.id)
			return false;
		if (mats == null) {
			if (other.mats != null)
				return false;
		} else if (!mats.equals(other.mats))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(photo, other.photo))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", bodypart=" + bodypart + ", price=" + price
				+ ", date_creation=" + date_creation + ", photo=" + Arrays.toString(photo) + ", mats=" + mats
				+ ", characteristic=" + characteristic + ", customers=" + customers + "]";
	}

}
