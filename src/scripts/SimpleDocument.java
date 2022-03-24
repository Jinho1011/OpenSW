package scripts;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SimpleDocument {
    private Document document;

    public SimpleDocument() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
    }

    public SimpleDocument(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.parse(new File(fileName));
    }

    public NodeList getNodeList(String TagName) {
        document.getDocumentElement().normalize();
        return document.getElementsByTagName(TagName);
    }

    public Element addChild(String tagName) {
        Element newElement = document.createElement(tagName);
        document.appendChild(newElement);
        return newElement;
    }



    public Element addChild(Element parent, String tagName) {
        Element newElement = document.createElement(tagName);
        parent.appendChild(newElement);
        return newElement;
    }

    public void addTextChild(Element parent, String text) {
        parent.appendChild(document.createTextNode(text));
    }

    public void saveDocument(String fileName) throws TransformerException, FileNotFoundException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new FileOutputStream(new File(fileName)));
        transformer.transform(domSource, streamResult);
    }


}