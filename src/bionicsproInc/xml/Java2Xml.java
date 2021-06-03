package bionicsproInc.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import bionicsproInc.db.ifaces.DBManager;
import bionicsproInc.db.jdbc.JDBCManager;
import bionicsproInc.db.pojos.Material;
import bionicsproInc.db.pojos.Product;
import bionicsproInc.db.pojos.ProductList;

public class Java2Xml {
	
	private static DBManager dbman = new JDBCManager();
	
	public void Java2XmlProd(){
		try {
		JAXBContext jaxbcont=JAXBContext.newInstance(Product.class);
		Marshaller marshaller=jaxbcont.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		File file= new File("./xmls/Product.xml");
		file.createNewFile();
		ProductList listin= new ProductList(dbman.allProducts());
		marshaller.marshal(listin, file);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
