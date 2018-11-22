import java.io.IOException;

import javax.xml.parsers.*;
import java.lang.String;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A class to hold methods related to XML I/O
 * @author Saxton Stafford
 */

public class TableIO {

	/**
	 * TODO: User registration, User deletion
	 * 
	 */
	
    public static void main(String argv[]) {
    	String dbFile = "filelist.xml";
    	String searchTerm = "file";
    	
    	ArrayList<NapFile> n = searchByDescription(dbFile, searchTerm);
    	for (int i = 0; i < n.size(); i++){
    		System.out.println(n.get(i).toString());
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
    	    		if (user.item(k).getNodeName().equals("ip")) {
    	    			ip = user.item(k).getTextContent();
    	    		}
    	    		if (user.item(k).getNodeName().equals("port")) {
    	    			port = user.item(k).getTextContent();
    	    		}
    	    		if (user.item(k).getNodeName().equals("username")) {
    	    			username = user.item(k).getTextContent();
    	    		}
    	    		if (user.item(k).getNodeName().equals("connSpeed")) {
    	    			connSpeed = user.item(k).getTextContent();
    	    		}
	        	}
    	    }
    	}
    
    	NapFile returnFile = new NapFile(name, description, ip, port, username, connSpeed);
    	return returnFile;
    }
    
    /**
     * Searches all files in the XML tree and returns ones whose descriptions contain the searchTerm 
     * @param dbFile The XML File to be parsed
     * @param searchTerm What to search for in file descriptions
     * @return An ArrayList of NapFiles whose descriptions contain the searchTerm
     */
    public static ArrayList<NapFile> searchByDescription(String dbFile, String searchTerm) {
        ArrayList<NapFile> searchResult = new ArrayList<NapFile>();  
        
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(dbFile);
            NodeList fileList = doc.getElementsByTagName("file");
            
            //Loop through all files in DB
            for (int i = 0; i< fileList.getLength(); i++) {
            	
            	//Grabs a file tag
            	Node n = fileList.item(i);
         	
            	//Put name, description, and owner of a file in a list
            	NodeList fileChildren = n.getChildNodes();
            	
            	//Search for file description specifically
            	for (int j = 0; j < fileChildren.getLength(); j++) {
	            	Node m = fileChildren.item(j);
	            	//System.out.println(m.getTextContent());
	            	
	            	//If file is found, make an object and add the file/owner data to list
	            	if (m.getNodeName().equals("description") && m.getTextContent().contains(searchTerm)) {
	            		NapFile napfile = nodeToNapFile(n);	            		
	            		searchResult.add(napfile);
	            	}
            	}

            }
        } catch (ParserConfigurationException e) {
        	e.printStackTrace();
        } catch (SAXException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }       
    	return searchResult;
    }
    

}

