package bionicsproInc.db.pojos;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name= "MaterialList")
@XmlType(propOrder = {"materials"})
public class MaterialList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971876261740055733L;
	
	@XmlElement
	private ArrayList<Material> materials;
	
	
	public MaterialList() {
		super();
	}
	
	public MaterialList(ArrayList<Material> materials) {
		super();
		this.materials = materials;
	}

	public ArrayList<Material> getMaterials() {
		return materials;
	}
	public void setMaterials(ArrayList<Material> materials) {
		this.materials = materials;
	}
	public int getNumMats() {
		int cont=materials.size();
		return cont;
	}
	public Material getMat(int i) {
		Material p= new Material();
		p=materials.get(i);
		return p;
	}
	@Override
	public String toString() {
		return "MaterialList [materials=" + materials + "]";
	}
	
	
	

}
