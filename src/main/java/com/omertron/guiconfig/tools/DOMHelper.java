package com.omertron.guiconfig.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Generic set of routines to process the DOM model data
 * Used for read XML files.
 * @author Stuart.Boston
 *
 */
public class DOMHelper {

    private static final Logger LOGGER = Logger.getLogger(DOMHelper.class);

    /**
     * Add a child element to a parent element
     * @param doc
     * @param parentElement
     * @param elementName
     * @param elementValue
     */
    public static void appendChild(Document doc, Element parentElement, String elementName, String elementValue) {
        appendChild(doc, parentElement, elementName, elementValue, null);
    }

    /**
     * Add a child element to a parent element with a set of attributes
     * @param doc
     * @param parentElement
     * @param elementName
     * @param elementValue
     * @param childAttributes
     */
    public static void appendChild(Document doc, Element parentElement, String elementName, String elementValue, Map<String, String> childAttributes) {
        Element child = doc.createElement(elementName);
        Text text = doc.createTextNode(elementValue);
        child.appendChild(text);

        if (childAttributes != null && !childAttributes.isEmpty()) {
            for (String attrib : childAttributes.keySet()) {
                child.setAttribute(attrib, childAttributes.get(attrib));
            }
        }

        parentElement.appendChild(child);
    }

    /**
     * Append a child element to a parent element with a single attribute/value pair
     * @param doc
     * @param parentElement
     * @param elementName
     * @param elementValue
     * @param attribName
     * @param attribValue
     */
    public static void appendChild(Document doc, Element parentElement, String elementName, String elementValue, String attribName, String attribValue) {
        Element child = doc.createElement(elementName);
        Text text = doc.createTextNode(elementValue);
        child.appendChild(text);
        child.setAttribute(attribName, attribValue);
        parentElement.appendChild(child);
    }

    /**
     * Convert a DOM document to a string
     * @param doc
     * @return
     * @throws TransformerException
     */
    public static String convertDocToString(Document doc) throws TransformerException {
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        //create string from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        return sw.toString();
    }

    /**
     * Create a blank Document
     * @return a Document
     * @throws ParserConfigurationException
     */
    public static Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        return doc;
    }

    /**
     * Get a DOM document from the supplied file
     * @param file
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static Document getEventDocFromUrl(File file) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        return getEventDocFromUrl(file.toURI().toURL().toString());
    }

    /**
     * Get a DOM document from the supplied URL
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static Document getEventDocFromUrl(String url) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        InputStream in = (new URL(url)).openStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(in);
        doc.getDocumentElement().normalize();
        return doc;
    }

    /**
     * Gets the string value of the tag element name passed
     * @param element
     * @param tagName
     * @return
     */
    public static String getValueFromElement(Element element, String tagName) {
        String returnValue = "";

        try {
            NodeList nlElement = element.getElementsByTagName(tagName);
            Element tagElement = (Element) nlElement.item(0);
            NodeList tagNodeList = tagElement.getChildNodes();
            returnValue = ((Node) tagNodeList.item(0)).getNodeValue();
        } catch (Exception ignore) {
            return returnValue;
        }

        return returnValue;
    }

    /**
     * Get an element from a parent element node
     * @param eParent
     * @param elementName
     * @return
     */
    public static Element getElementByName(Element eParent, String elementName) {
        NodeList nlParent = eParent.getElementsByTagName(elementName);
        for (int looper = 0; looper < nlParent.getLength(); looper++) {
            if (nlParent.item(looper).getNodeType() == Node.ELEMENT_NODE) {
                return (Element) nlParent.item(looper);
            }
        }
        return null;
    }

    /**
     * Write the Document out to a file using nice formatting
     * @param doc   The document to save
     * @param localFilename The file to write to
     * @return
     */
    public static boolean writeDocumentToFile(Document doc, String localFilename) {
        return writeDocumentToFile(doc, new File(localFilename));
    }

    /**
     * Write the Document out to a file using nice formatting
     * @param doc   The document to save
     * @param localFile The file to write to
     * @return
     */
    public static boolean writeDocumentToFile(Document doc, File localFile) {
        try {
            Transformer trans = TransformerFactory.newInstance().newTransformer();

            // Define the output properties
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            doc.setXmlStandalone(true);

            trans.transform(new DOMSource(doc), new StreamResult(localFile));
            return true;
        } catch (Exception error) {
            LOGGER.error("Error writing the document to " + localFile.getAbsolutePath());
            LOGGER.error("Message: " + error.getMessage());
            return false;
        }
    }
}
