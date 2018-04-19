import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


public class Week4Example {
	public static void main(String[] args){
		switch (args[0]){
			case "1":
				example_1();
				break;
			case "2":
				//example_2();
				break;

		}
	}

	/*
	xml
	*/
	public static void example_1(){
		try {
		 	File file = new File("ThatAuto.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			document.getDocumentElement().normalize();
    		System.out.println("Root element :" + document.getDocumentElement().getNodeName());


    		// Call Sedan Object
    		NodeList nList = document.getElementsByTagName("Sedan");
		    System.out.println("----------------------------");

		    // Iterate through Sedans
	    	for (int temp = 0; temp < nList.getLength(); temp++) {
		        Node nNode = nList.item(temp);
		        // Parent Node
		        System.out.println("\nCurrent Element :" + nNode.getNodeName());
		        // Create Parent Node
	            Element eElement = (Element) nNode;
	            System.out.println("Vehicle id : "
	                               + eElement.getAttribute("id"));
	            System.out.println("Value : "
	                               + eElement.getElementsByTagName("Value")
	                                 .item(0).getTextContent());
	            System.out.println("Cost: "
	                               + eElement.getElementsByTagName("Cost")
	                                 .item(0).getTextContent());
	            // Step into child Node
	            Node hatchback = eElement.getElementsByTagName("hatchback").item(0);
	            Element hElement = (Element) hatchback;
	            System.out.println("Hatchback Value : " + hElement.getElementsByTagName("Value").item(0).getTextContent());
	            System.out.println("Hatchback Cost : " + hElement.getElementsByTagName("Cost").item(0).getTextContent());
		    }
		} catch (Exception e){
			System.out.println(e);
		}
	}
}
