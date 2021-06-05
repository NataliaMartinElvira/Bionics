package bionicsproInc.xml;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Xml2Html {
	
	public static void Xml2HtmlProduct() {
		String xmlPath="./xmls/Product.xml";
		String xsltPath="./xmls/Products.xslt";
		String htmlPath="./xmls/Product.html";
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(xmlPath)),new StreamResult(new File(htmlPath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void Xml2HtmlMaterial() {
		String xmlPath="./xmls/Material.xml";
		String xsltPath="./xmls/Materials.xslt";
		String htmlPath="./xmls/Material.html";
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(xmlPath)),new StreamResult(new File(htmlPath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
