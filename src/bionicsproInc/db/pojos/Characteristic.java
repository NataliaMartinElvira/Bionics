package bionicsproInc.db.pojos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "characteristics")
public class Characteristic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8095684428083579778L;
	private int id;
	private String dimentions;
	private float weight;
	private int joints_numb;
	private int flexibilty_scale;
	private Product products;

	public Characteristic(String dimentions, float weigth, int joints_numb, int flexibilty_scale) {
		super();
		this.dimentions = dimentions;
		this.weight=weigth;
		this.joints_numb = joints_numb;
		this.flexibilty_scale = flexibilty_scale;
	}

	public int getId() {
		return id;
	}

	public Product getProducts() {
		return products;
	}

	public void setProducts(Product products) {
		this.products = products;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDimentions() {
		return dimentions;
	}

	public void setDimentions(String dimentions) {
		this.dimentions = dimentions;
	}

	public int getJoints_numb() {
		return joints_numb;
	}

	public void setJoints_numb(int joints_numb) {
		this.joints_numb = joints_numb;
	}

	public int getFlexibilty_scale() {
		return flexibilty_scale;
	}

	public void setFlexibilty_scale(int flexibilty_scale) {
		this.flexibilty_scale = flexibilty_scale;
	}
	

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dimentions == null) ? 0 : dimentions.hashCode());
		result = prime * result + flexibilty_scale;
		result = prime * result + id;
		result = prime * result + joints_numb;
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + Float.floatToIntBits(weight);
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
		Characteristic other = (Characteristic) obj;
		if (dimentions == null) {
			if (other.dimentions != null)
				return false;
		} else if (!dimentions.equals(other.dimentions))
			return false;
		if (flexibilty_scale != other.flexibilty_scale)
			return false;
		if (id != other.id)
			return false;
		if (joints_numb != other.joints_numb)
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (Float.floatToIntBits(weight) != Float.floatToIntBits(other.weight))
			return false;
		return true;
	}

	

}