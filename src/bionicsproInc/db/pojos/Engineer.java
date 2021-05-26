package bionicsproInc.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "engineer")
public class Engineer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7354779387678883946L;
	private int id;
	private String name_surname;
	private Date date_of_birth;
	private Date contract_strating_date;
	private Date contract_ending_date;
	private float salary;
	private float bonus;
	private int experience_in_years;
	private int role_id;
	private ArrayList<Product> products;
	
	//Constructors
	public Engineer(int id, String name_surname, Date date_of_birth, Date contract_strating_date,
			Date contract_ending_date, float salary, float bonus, int experience_in_years,
			ArrayList<Product> products) {
		super();
		this.id = id;
		this.name_surname = name_surname;
		this.date_of_birth = date_of_birth;
		this.contract_strating_date = contract_strating_date;
		this.contract_ending_date = contract_ending_date;
		this.salary = salary;
		this.bonus = bonus;
		this.experience_in_years = experience_in_years;
		this.products = products;
	}

	public Engineer(int id, float bonus) {
		super();
		this.id = id;
		this.bonus = bonus;
	}
	
	
	
	
	public Engineer(String name_surname, Date contract_ending_date) {
		super();
		this.name_surname = name_surname;
		this.contract_ending_date = contract_ending_date;
	}

	public Engineer(String name_surname, Date contract_strating_date, Date contract_ending_date,
			float salary, float bonus, int experience_in_years, Date date_of_birth, int role_id) {
		super();
		this.name_surname = name_surname;
		this.contract_strating_date = contract_strating_date;
		this.contract_ending_date = contract_ending_date;
		this.salary = salary;
		this.bonus = bonus;
		this.experience_in_years = experience_in_years;
		this.date_of_birth = date_of_birth;
		this.role_id = role_id;
	}

	public Engineer() {
		super();
	}
	
	//Getters and Setters
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName_surname() {
		return name_surname;
	}

	public void setName_surname(String name_surname) {
		this.name_surname = name_surname;
	}

	public Date getContract_strating_date() {
		return contract_strating_date;
	}

	public void setContract_strating_date(Date contract_strating_date) {
		this.contract_strating_date = contract_strating_date;
	}

	public Date getContract_ending_date() {
		return contract_ending_date;
	}

	public void setContract_ending_date(Date contract_ending_date) {
		this.contract_ending_date = contract_ending_date;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public float getBonus() {
		return bonus;
	}

	public void setBonus(float bonus) {
		this.bonus = bonus;
	}

	public int getExperience_in_years() {
		return experience_in_years;
	}

	public void setExperience_in_years(int experience_in_years) {
		this.experience_in_years = experience_in_years;
	}

	public Date getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	
	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	//HashCode and equals

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(bonus);
		result = prime * result + ((contract_ending_date == null) ? 0 : contract_ending_date.hashCode());
		result = prime * result + ((contract_strating_date == null) ? 0 : contract_strating_date.hashCode());
		result = prime * result + ((date_of_birth == null) ? 0 : date_of_birth.hashCode());
		result = prime * result + experience_in_years;
		result = prime * result + id;
		result = prime * result + ((name_surname == null) ? 0 : name_surname.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + role_id;
		result = prime * result + Float.floatToIntBits(salary);
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
		Engineer other = (Engineer) obj;
		if (Float.floatToIntBits(bonus) != Float.floatToIntBits(other.bonus))
			return false;
		if (contract_ending_date == null) {
			if (other.contract_ending_date != null)
				return false;
		} else if (!contract_ending_date.equals(other.contract_ending_date))
			return false;
		if (contract_strating_date == null) {
			if (other.contract_strating_date != null)
				return false;
		} else if (!contract_strating_date.equals(other.contract_strating_date))
			return false;
		if (date_of_birth == null) {
			if (other.date_of_birth != null)
				return false;
		} else if (!date_of_birth.equals(other.date_of_birth))
			return false;
		if (experience_in_years != other.experience_in_years)
			return false;
		if (id != other.id)
			return false;
		if (name_surname == null) {
			if (other.name_surname != null)
				return false;
		} else if (!name_surname.equals(other.name_surname))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (role_id != other.role_id)
			return false;
		if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
			return false;
		return true;
	}
	
}
