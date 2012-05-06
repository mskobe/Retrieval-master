package com.zj.retrieval.master.test;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.jamesmurty.utils.XMLBuilder;

public class XmlIOTest {
	private Log log = LogFactory.getLog(XmlIOTest.class);
	
	@Test
	public void simpleReadNodeTest() throws Exception {
		String owl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rdf:RDF xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl = \"http://www.w3.org/2002/07/owl#\" xmlns:rdfs= \"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd = \"http://www.w3.org/2001/XMLSchema#\"> <owl:Class rdf:ID=\"Genus_Eptatretus\"><rdfs:subClassOf rdf:resource=\"#001_Family_Myxinidae\"/><rdfs:label>粘盲鳗属</rdfs:label><childNodes><node>12</node><node>13</node></childNodes></owl:Class></rdf:RDF>";
		XMLBuilder builder = XMLBuilder.parse(
				   new InputSource(new StringReader(owl)));
		Element elem = builder.xpathFind("/RDF/Class/childNodes").getElement();
		for(int i = 0; i < elem.getChildNodes().getLength(); i++) {
			Element elemNode = (Element)elem.getChildNodes().item(i);
			String text = elemNode.getTextContent();
			System.out.println(text);
		}
	}
	
	@Test
	public void getEveryChildNodeTest() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		String owl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rdf:RDF><attributes><attr><desc>desc0</desc><image>img0</image></attr><attr><desc>Here is desc</desc><image>Here is image</image></attr></attributes></rdf:RDF>";
		XMLBuilder builder = XMLBuilder.parse(
				   new InputSource(new StringReader(owl)));
		Element eAttributes = builder.xpathFind("/RDF/attributes").getElement();
		int eImageCnt = eAttributes.getElementsByTagName("image").getLength();
		log.info("eImageCnt=" + eImageCnt);
		Node eImage = ((Element)eAttributes.getChildNodes().item(0)).getElementsByTagName("desc").item(0);
		log.info(eImage.getTextContent());
	}
}
