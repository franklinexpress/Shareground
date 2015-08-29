package delossantos_dot_franklin_at_gmail_dot_com.shareground;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * This class manages the client's Server ID History XML file, using DOM.
 */
public class DOMServerIdsHandler {

	/**
	 * The filename of the XML file that will hold the Server Id history
	 */
	private static final String XML_FILE = "xmlFiles/ServerId.xml";

	/**
	 * The DOM document that holds the serverIdHistory
	 */
	private Document serverIdHistory;

	public boolean serverIdsFileExists() {
		File serverIdsFile = new File(XML_FILE);
		return serverIdsFile.exists();
	}
	
	/**
	 * Returns a List of server Ids read from an XML file.
	 * 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public List<String> readServerIdsFromXML() throws ParserConfigurationException, SAXException, IOException {
		List<String> serverIds = new ArrayList<>();
		File serverIdsFile = new File(XML_FILE);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(serverIdsFile);
		NodeList serverIdNodes = document.getElementsByTagName("serverid");
		int numberOfIds = serverIdNodes.getLength();
		for (int i = 0; i < numberOfIds; i++) {
			Node node = serverIdNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String serverId = ((Element)node).getTextContent();
				serverIds.add(serverId);
			}
		}
		return serverIds;
	}



	/**
	 * Transforms a List of server Ids to an XML file.
	 * 
	 * @throws ParserConfigurationException 
	 */
	public void writeServerIdsToXML(List<String> serverIds) 
	throws IOException, TransformerException, ParserConfigurationException {
		File serverIdsFile = new File(XML_FILE);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement("root");
		for (String serverId: serverIds) {
			Element serverIdElement = document.createElement("serverid");
			serverIdElement.setTextContent(serverId);
			rootElement.appendChild(serverIdElement);
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(serverIdHistory);
		StreamResult result = new StreamResult(serverIdsFile);
		transformer.transform(source, result);
	}
}
