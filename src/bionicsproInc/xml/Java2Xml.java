package bionicsproInc.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import bionicsproInc.db.pojos.Material;
import bionicsproInc.db.pojos.Product;

public class Java2Xml {
	
	public void Java2XmlProd(){
		try {
		JAXBContext jaxbcont=JAXBContext.newInstance(Product.class);
		Marshaller marshaller=jaxbcont.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		File file= new File("./xml/ProdMarsh.xml");
		file.createNewFile();
		//faltan cosas
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Java2XmlMat(){
		try {
		JAXBContext jaxbcont=JAXBContext.newInstance(Material.class);
		Marshaller marshaller=jaxbcont.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		File file= new File("./xml/MatMarsh.xml");
		file.createNewFile();
		//faltan cosas
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
