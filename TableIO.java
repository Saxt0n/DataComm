import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.lang.String;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A class to hold methods related to XML I/O
 * Got help with XML I/O from https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 * @author Saxton Stafford
 */

public class TableIO {
	
    /*
     * Filename containing database info
     */
    private String dbFileName;
	
    /*
     * List of every file in the form of a NapFile
     */
    private ArrayList<NapFile> allFiles;
	
    public static void main(String argv[]) {
    	String dbFile = "filelist.xml";

    	TableIO tio = new TableIO(dbFile);
    	tio.printAllNodes();
    	
    	tio.register("anotherfilelist.xml");
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	tio.printAllNodes();
    }
    
    /**
     * Initializes the database as an ArrayList of NapFiles
     * @param dbFileName
     */
    TableIO (String dbFileName) {
    	this.dbFileName = dbFileName;
    	this.allFiles = new ArrayList<NapFile>();
    	this.update();   
    }
    
    /**
     * Initializes the database as an ArrayList of NapFiles
     * @param dbFileName
     */
    TableIO () {
    	this.dbFileName = null;
    	this.allFiles = new ArrayList<NapFile>();  
    }
    
    /**
     * Add all of a user's files based on an XML file containing all their info
     */
    public void register(String hostFiles) {
		
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        TableIO tempTable = new TableIO(hostFiles);
        
    	try {
    	    for (int i = 0; i < tempTable.getAllFiles().size(); i++) {
    		NapFile nf = tempTable.getAllFiles().get(i);
				
    		DocumentBuilder builder = factory.newDocumentBuilder();
    		Document doc = builder.parse(this.dbFileName);
    		Node root = doc.getFirstChild();
	            
    		Element fileNode = doc.createElement("file");
	            
    		Element filenameNode = doc.createElement("name");
    		filenameNode.appendChild(doc.createTextNode(nf.getFileName()));
	            
	            
    		Element descriptionNode = doc.createElement("description");
    		descriptionNode.appendChild(doc.createTextNode(nf.getDescription()));
	            
    		Element userNode = doc.createElement("user");
	            
    		Element usernameNode = doc.createElement("username");
    		usernameNode.appendChild(doc.createTextNode(nf.getUsername()));
    		userNode.appendChild(usernameNode);
	            
    		Element ipNode = doc.createElement("ip");
    		ipNode.appendChild(doc.createTextNode(nf.getIp()));
    		userNode.appendChild(ipNode);
	            
    		Element portNode = doc.createElement("port");
    		portNode.appendChild(doc.createTextNode(nf.getPort()));
    		userNode.appendChild(portNode);
	            
    		Element speedNode = doc.createElement("connSpeed");
    		speedNode.appendChild(doc.createTextNode(nf.getConnSpeed()));
    		userNode.appendChild(speedNode);
	            
    		fileNode.appendChild(filenameNode);
    		fileNode.appendChild(descriptionNode);
    		fileNode.appendChild(userNode);
	            
    		root.appendChild(fileNode);
	            
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    		DOMSource source = new DOMSource(doc);
    		StreamResult result = new StreamResult(new File(this.getDbFileName()));
	    		
    		transformer.transform(source, result);
	    		
    		this.update();
	
    		System.out.println("File saved!");
    		System.out.println("ArrayList updated!");
    	    }
        } catch (ParserConfigurationException pce) {
    	    pce.printStackTrace();
    	} catch (TransformerException tfe) {
    	    tfe.printStackTrace();       
    	} catch (SAXException e) {
    	    e.printStackTrace();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }
    
    /**
     * Initializes the database as an ArrayList of NapFiles
     * @param dbFileName
     */
    TableIO () {
    	this.dbFileName = null;
    	this.allFiles = new ArrayList<NapFile>();  
    }
    
    /**
     * Add all of a user's files based on an XML file containing all their info
     */
	public void register(String hostFiles) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        TableIO tempTable = new TableIO(hostFiles);
        
		try {
			for (int i = 0; i < tempTable.getAllFiles().size(); i++) {
	            NapFile nf = tempTable.getAllFiles().get(i);
				
				DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(this.dbFileName);
	            Node root = doc.getFirstChild();
	            
	            Element fileNode = doc.createElement("file");
	            
	            Element filenameNode = doc.createElement("name");
	            filenameNode.appendChild(doc.createTextNode(nf.getFileName()));
	            
	            
	            Element descriptionNode = doc.createElement("description");
	            descriptionNode.appendChild(doc.createTextNode(nf.getDescription()));
	            
	            Element userNode = doc.createElement("user");
	            
	            Element usernameNode = doc.createElement("username");
	            usernameNode.appendChild(doc.createTextNode(nf.getUsername()));
	            userNode.appendChild(usernameNode);
	            
	            Element ipNode = doc.createElement("ip");
	            ipNode.appendChild(doc.createTextNode(nf.getIp()));
	            userNode.appendChild(ipNode);
	            
	            Element portNode = doc.createElement("port");
	            portNode.appendChild(doc.createTextNode(nf.getPort()));
	            userNode.appendChild(portNode);
	            
	            Element speedNode = doc.createElement("connSpeed");
	            speedNode.appendChild(doc.createTextNode(nf.getConnSpeed()));
	            userNode.appendChild(speedNode);
	            
	            fileNode.appendChild(filenameNode);
	            fileNode.appendChild(descriptionNode);
	            fileNode.appendChild(userNode);
	            
	            root.appendChild(fileNode);
	            
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    		Transformer transformer = transformerFactory.newTransformer();
	    		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    		DOMSource source = new DOMSource(doc);
	    		StreamResult result = new StreamResult(new File(this.getDbFileName()));
	    		
	    		transformer.transform(source, result);
	    		
	    		this.update();
	
	    		System.out.println("File saved!");
	    		System.out.println("ArrayList updated!");
			}
        } catch (ParserConfigurationException pce) {
    		pce.printStackTrace();
    	} catch (TransformerException tfe) {
    		tfe.printStackTrace();       
	    } catch (SAXException e) {
	        	e.printStackTrace();
	    } catch (IOException e) {
	        	e.printStackTrace();
	    }
    }
    
    /**
     * A method to help with debugging that prints every file in the database
     */
    public void printAllNodes() {
    	for (int i = 0; i < this.allFiles.size(); i++){
	    System.out.println(this.allFiles.get(i));
    	}   	
    }
     
    /*
     * A helper method to keep the ArrayList up to date
     */
    private void update() {
    	this.allFiles = new ArrayList<NapFile>();
	try
	    {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(dbFileName);
		NodeList fileList = doc.getElementsByTagName("file");
            
		//Loop through all files in DB
		for (int i = 0; i< fileList.getLength(); i++) {
            	
		    //Grabs a file tag
		    Node n = fileList.item(i);
            	
		    //Turn it into a NapFile
		    NapFile nf = nodeToNapFile(n);
		    allFiles.add(nf);
		}
	    } catch (ParserConfigurationException e) {
	    e.printStackTrace();
        } catch (SAXException e) {
	    e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
        }    
    }
    
    /**
     * A helper method to convert an XML node into a NapFile
     *
     * @param n Node
     * @return A Node in NapFile format
     */
    private static NapFile nodeToNapFile(Node n) {
    	NodeList nl = n.getChildNodes();
    	String name = null; 
    	String description = null; 
    	String ip = null;
    	String port = null;
    	String username = null;
    	String connSpeed = null;
    	
    	for (int i = 0; i < nl.getLength(); i++ ){
	    if (nl.item(i).getNodeName().equals("name")) {
		name = nl.item(i).getTextContent();
	    }
	    if (nl.item(i).getNodeName().equals("description")) {
		description = nl.item(i).getTextContent();
	    }
	    if (nl.item(i).getNodeName().equals("user")) {
		NodeList user = nl.item(i).getChildNodes();
		for (int k = 0; k < user.getLength(); k++ ){
		    if (user.item(k).getNodeName().equals("username")) {
			username = user.item(k).getTextContent();
		    }
		    if (user.item(k).getNodeName().equals("ip")) {
			ip = user.item(k).getTextContent();
		    }
		    if (user.item(k).getNodeName().equals("port")) {
			port = user.item(k).getTextContent();
		    }
		    if (user.item(k).getNodeName().equals("connSpeed")) {
			connSpeed = user.item(k).getTextContent();
		    }
		}
    	    }
    	}
    
    	NapFile returnFile = new NapFile(name, description, username, ip, port, connSpeed);
    	return returnFile;
    }
    
    /**
     * Searches all files in the XML tree and returns ones whose descriptions contain the searchTerm.
     * Case sensitive.
     * @param dbFile The XML File to be parsed
     * @param searchTerm What to search for in file descriptions
     * @return An ArrayList of NapFiles whose descriptions contain the searchTerm
     */
    public ArrayList<NapFile> searchByDescription(String term) {
        ArrayList<NapFile> searchResult = new ArrayList<NapFile>();  
        
        for (int i = 0; i < this.getAllFiles().size(); i++) {
	    if (this.getAllFiles().get(i).getDescription().contains(term)) {
		searchResult.add(this.getAllFiles().get(i));
	    }
    	}	            
    	return searchResult;
    }
    
    public String getDbFileName() {
	return dbFileName;
    }

    public void setDbFileName(String dbFileName) {
	this.dbFileName = dbFileName;
    }

    public ArrayList<NapFile> getAllFiles() {
	return allFiles;
    }

    public void setAllFiles(ArrayList<NapFile> allFiles) {
	this.allFiles = allFiles;
    }
    

    /**
     * TODO Check through directory and repeat this action for as many files as there are
     */
    public void register(String filename, String description, String username, String ip, String port, String connSpeed) {
		
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(dbFileName);
            Node root = doc.getFirstChild();
            
            Element fileNode = doc.createElement("file");
            
            Element filenameNode = doc.createElement("name");
            filenameNode.appendChild(doc.createTextNode(filename));
            
            
            Element descriptionNode = doc.createElement("description");
            descriptionNode.appendChild(doc.createTextNode(description));
            
            Element userNode = doc.createElement("user");
            
            Element usernameNode = doc.createElement("username");
            usernameNode.appendChild(doc.createTextNode(username));
            userNode.appendChild(usernameNode);
            
            Element ipNode = doc.createElement("ip");
            ipNode.appendChild(doc.createTextNode(ip));
            userNode.appendChild(ipNode);
            
            Element portNode = doc.createElement("port");
            portNode.appendChild(doc.createTextNode(port));
            userNode.appendChild(portNode);
            
            Element speedNode = doc.createElement("connSpeed");
            speedNode.appendChild(doc.createTextNode(connSpeed));
            userNode.appendChild(speedNode);
            
            fileNode.appendChild(filenameNode);
            fileNode.appendChild(descriptionNode);
            fileNode.appendChild(userNode);
            
            root.appendChild(fileNode);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
    	    Transformer transformer = transformerFactory.newTransformer();
    	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	    DOMSource source = new DOMSource(doc);
    	    StreamResult result = new StreamResult(new File(this.getDbFileName()));
    		
    	    transformer.transform(source, result);
    		
    	    this.update();

    	    System.out.println("File saved!");
    	    System.out.println("ArrayList updated!");
            
        } catch (ParserConfigurationException pce) {
    	    pce.printStackTrace();
    	} catch (TransformerException tfe) {
    	    tfe.printStackTrace();       
    	} catch (SAXException e) {
    	    e.printStackTrace();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }
	
    /**
     * Deletes all files in the database that belong to username
     * @param username
     */
    public void deleteByUsername(String username) {
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(dbFileName);
            //Node root = doc.getFirstChild();
            NodeList fileList = doc.getElementsByTagName("file");
            int numFiles = fileList.getLength();
            
            //Loop through all files in DB
            for (int i = numFiles; i > 0; i--) {
            	
            	//Grabs a file tag
            	Node n = fileList.item(i - 1);
            	
            	//Turn it into a NapFile
            	NapFile nf = nodeToNapFile(n);

            	if (nf.getUsername().equals(username)) {
		    n.getParentNode().removeChild(n);
		    System.out.println("DELETING FILE FROM " + nf.getUsername());
            	}            	
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File(this.getDbFileName()));
	    transformer.transform(source, result);
            
            this.update();
        } catch (ParserConfigurationException e) {
	    e.printStackTrace();
        } catch (TransformerException tfe) {
	    tfe.printStackTrace();   
        } catch (SAXException e) {
	    e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
        } 
    }
}

