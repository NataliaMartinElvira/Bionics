package bionicsproInc.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import bionicsproInc.xml.utils.CustomErrorHandler;

public class DTDCheckerProduct {
	public void DTDCheck() {
		File xmlfile = new File("./xmls/Product.xml");
		try {
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			dBF.setValidating(true);
			DocumentBuilder builder = dBF.newDocumentBuilder();
			CustomErrorHandler customErrorHandler = new CustomErrorHandler();
			builder.setErrorHandler(customErrorHandler);
			Document doc = builder.parse(xmlfile);
			if (customErrorHandler.isValid()) {
				System.out.println(xmlfile + " was valid!");
			}
		} catch (ParserConfigurationException ParEX) {
			System.out.println(xmlfile + " error while parsing!");
		} catch (SAXException SAXEx) {
			System.out.println(xmlfile + " was not well-formed!");
		} catch (IOException ex) {
			System.out.println(xmlfile + " was not accesible!");
		}
	}
}
