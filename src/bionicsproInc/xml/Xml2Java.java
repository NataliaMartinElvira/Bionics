package bionicsproInc.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import bionicsproInc.db.ifaces.DBManager;
import bionicsproInc.db.jdbc.JDBCManager;
import bionicsproInc.db.pojos.MaterialList;
import bionicsproInc.db.pojos.Product;
import bionicsproInc.db.pojos.ProductList;

public class Xml2Java {
	private static DBManager dbman = new JDBCManager();

	public void Xml2JavaProd() {
		try {
			JAXBContext jaxbcont=JAXBContext.newInstance(ProductList.class);
			Unmarshaller unmarshaller=jaxbcont.createUnmarshaller();
			File file= new File("./xmls/Product.xml");
			ProductList listP= (ProductList) unmarshaller.unmarshal(file);
			if (listP.equals(null)) {
				System.out.println("ERROR, there aren't any products in the xml");
			}else {
				listP.getProducts().toString();
				dbman.addProductsFromXML(listP);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Xml2JavaMat() {
		try {
			JAXBContext jaxbcont=JAXBContext.newInstance(MaterialList.class);
			Unmarshaller unmarshaller=jaxbcont.createUnmarshaller();
			File file= new File("./xmls/Material.xml");
			MaterialList listM= (MaterialList) unmarshaller.unmarshal(file);
			if (listM.equals(null)) {
				System.out.println("ERROR, there aren't any products in the xml");
			}else {
				listM.getMaterials().toString();
				dbman.addMaterialFromXML(listM);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
