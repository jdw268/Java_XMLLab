/*
* Name:  Jillian Irvin
* Class: CIT-244
* Date:  10.8.17
* Description: This class is a helper class for reading xml configuration files and extracting
* the necessary data.  It processes all requests for the car to be built.
*/
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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConfigParserXML{

	/*
	* Input: String user entered PID#
	* Return: string arrray of vehicle information
	* Description: Reads produce ID and creates a string array with the following information:
	* [type, make, model, options, parts, price, vehicle_option1, vehicle_option2 (if exists)]
	*/
	public static String[] request(String pid){

		//String[] vehicleParams = new String[8];  //to return to Build method
		String[] vehicleParams = new String[8];
		File file;  //config file to use
		//int[] pidFormatted;

		//format the pid#
		int[] pidFormatted = pidFormat(Character.getNumericValue(pid.charAt(0)));

		//determine the make of the vehicle:  this, that, other - 2nd element
		vehicleParams[1] = getMake(pid);

		//determine the type of vehicle:  Sedan, Coupe, Minivan, SUV, Truck
		vehicleParams[0] = getType(pid);

		//instantiate the file
		//reference Joshua Aklin
		try{
			file = new File(vehicleParams[1] + ".xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			document.getDocumentElement().normalize();

			//hold cost of option selected
			ArrayList<String> partsList = new ArrayList<String>();

			//determine the model using the type of vehicle and its model number
			vehicleParams[2] = searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "", false);

			partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "", false));

			//determine the options using a for loop against type of options and pidformatted options
			//first build array list then set array list to vehicleParams[3]
			//array for optoins to loop through - not pidFormatted...must add 1 to it
			String[] optionsArray = {"Exterior-Color", "Interior-Color", "Powertrain", "Seat", "Radio",
			"Tire", "Rim", "Miscellaneous"};

			//hold option selections
			ArrayList<String> optionsList = new ArrayList<String>();

			//	optionsList.add("[");

			//loop through optionsArray and find in xml doc
			for (int i = 0; i < optionsArray.length; i++){

				//check if at beginning of loop
				if(i==0){
					//check if none '0' is entered
					if(pid.substring(pidFormatted[i], pidFormatted[i+1]).contains("0")){
						//add to optionsList
						optionsList.add(optionsArray[i] + ": None" + " , ");

						//keep arrays together
						partsList.add("");
					}
					else{
						optionsList.add(optionsArray[i] + ": " + searchXML(document, optionsArray[i], pid.substring(pidFormatted[i], pidFormatted[i+1]), "Value", "", false));
						partsList.add(" $ " +
						searchXML(document, optionsArray[i], pid.substring(pidFormatted[i], pidFormatted[i+1]), "Cost", "", false));

					}
				} //end if for beginning of for loop = 0

				//check if at end of optionsArray for misc option
				else if( i == (optionsArray.length -1)){
					//check if first char of substring is 0 for no misc option
					if(pid.substring(pidFormatted[i]).contains("0")){

						optionsList.add(optionsArray[i] + ": None" + " , ");
						partsList.add("");
					}
					else{
						//check how many digits are in substring
						//check if only 1 digit for misc. option
						if(pid.substring(pidFormatted[i]).length() == 1){
							//regular search for xml; just search pidFormatted of i
							optionsList.add(optionsArray[i] + ": " + searchXML(document, optionsArray[i], pid.substring(pidFormatted[i]), "Value", "", false));

							partsList.add(" $ " +
							searchXML(document, optionsArray[i], pid.substring(pidFormatted[i], pidFormatted[i+1]), "Cost", "", false));


						}
						//else call childNodeSearch - only 2 digit code for misc
						else{
							optionsList.add(optionsArray[i] + ": " + childNodeSearch(document, optionsArray[i], optionsArray[i],
							Character.toString((pid.substring(pidFormatted[i])).charAt(0)),
							Character.toString((pid.substring(pidFormatted[i])).charAt(1)),
							"Value"));

							partsList.add(" $ " +
							childNodeSearch(document, optionsArray[i], optionsArray[i],
							Character.toString((pid.substring(pidFormatted[i])).charAt(0)),
							Character.toString((pid.substring(pidFormatted[i])).charAt(1)),
							"Cost"));

						}
						//if digits = 1 then do searchXML
					} //end else for matched misc option
				}  //end initial else if if at end of for loop

				//not at beginning or end of for loop
				else {

					//check that none is entered for option
					if((pid.substring(pidFormatted[i], pidFormatted[i+1])).equalsIgnoreCase("0")){
						optionsList.add(optionsArray[i] + ": None");
						partsList.add("");

					}
					//else find match for id with pid substring
					else{
						//add value listed for matched numeric value
						//optionsList.add(optionsArray[i] + ": " + searchXML(document, optionsArray[i], pid.substring(pidFormatted[i+1], pidFormatted[i+2]), "Value"));
						optionsList.add(optionsArray[i] + ": " + searchXML(document, optionsArray[i], pid.substring(pidFormatted[i], pidFormatted[i+1]), "Value", "", false));
						partsList.add(" $ " + searchXML(document, optionsArray[i], pid.substring(pidFormatted[i], pidFormatted[i+1]), "Cost", "", false));
						//optionsList.add(optionsArray[i-2] + ": " + searchXML(document, optionsArray[i-2], pid.substring(pidFormatted[i-1], pidFormatted[i]), "Value"));
					}
				}  //end else for for loop
			}//end for loop - that built optionsList and associated partsList

			//finish partsList for vehicle options - hatchback, engine, etc.
			if(vehicleParams[0].equalsIgnoreCase("Sedan")){
				//add hatchback and engine options to partsList
				partsList.add("Hatchback: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "hatchback", true));

				//store option 1
				vehicleParams[6] = getOption(partsList);

				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "hatchback", true));
				partsList.add("Engine: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "engine", true));

				//store option 2
				vehicleParams[7] = getOption(partsList);

				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "engine", true));
			}
			else if(vehicleParams[0].equalsIgnoreCase("Coupe")){
				//add hatchback and engine options to partsList
				partsList.add("Turbo: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "turbo", true));
				//store option 1
				vehicleParams[6] = getOption(partsList);
				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "turbo", true));
				partsList.add("Engine: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "engine", true));
				//store option 2
				vehicleParams[7] = getOption(partsList);
				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "engine", true));
			}
			else if(vehicleParams[0].equalsIgnoreCase("MiniVan")){
				//add hatchback and engine options to partsList
				partsList.add("Seats: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "seats", true));
				//store option 1
				vehicleParams[6] = getOption(partsList);
				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "seats", true));
				//store optoin 2
				vehicleParams[7] = null;

			}
			else if(vehicleParams[0].equalsIgnoreCase("SUV")){
				//add hatchback and engine options to partsList
				partsList.add("Rows: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "rows", true));
				//store option 1
				vehicleParams[6] = getOption(partsList);
				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "rows", true));
				partsList.add("Seats: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "seats", true));
				//store option 2
				vehicleParams[7] = getOption(partsList);
				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "seats", true));
			}
			else if(vehicleParams[0].equalsIgnoreCase("Truck")){
				//add hatchback and engine options to partsList
				partsList.add("Bedsize: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "bedSize", true));
				//store option 1
				vehicleParams[6] = getOption(partsList);
				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "bedSize", true));
				partsList.add("Engine: " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Value", "engine", true));
				//store option 2
				vehicleParams[7] = getOption(partsList);
				partsList.add(" $ " + searchXML(document, vehicleParams[0], pid.substring(0, pidFormatted[0]), "Cost", "engine", true));
			}
			else{
				System.out.println("error");
			}

			//////////////////////////////////////////////////////////////////////////////////////////////////////////
			//put optionsList into string
			String optionsListString = "[";
			for(int i = 0; i < optionsList.size(); i++){
				optionsListString = optionsListString + ", " + optionsList.get(i);
			}
			optionsListString = optionsListString + "]";

			//set vecihclParam[3] to optionsList - make options list into string array
			vehicleParams[3] = optionsListString;



			//put partsList into string
			String partsListString = "[";

			//add model cost to first part of string
			partsListString = partsListString + "Model: " + vehicleParams[2] + " " + partsList.get(0);

			int leftOff = 0;
			for(int i = 0; i < optionsList.size(); i++){

				//check if option none
				if(partsList.get(i+1).contains("$")){
					//apppend with partsList
					partsListString = partsListString + ", " + optionsList.get(i) + " " + partsList.get(i+1);
				}
				leftOff = i+1;

			}

			//add last part of partsList to partsListString (for vehicle options 1 & 2)
			for(int i = leftOff+1; i <partsList.size(); i++){

				partsListString = partsListString + ", " + partsList.get(i);
			}

			//add closing brack
			partsListString = partsListString + "]";

			//set vecihclParam[4] to partsList - make options list into string array
			vehicleParams[4] = partsListString;

			//use to format total cost
			//	DecimalFormat formatter = new DecimalFormat("$###,###,##0.00");
			//reference:  http://javadevnotes.com/java-double-to-string-examples
			vehicleParams[5] = Double.toString(getCost(partsList));
		} //end try
		catch (OptionNotFoundException e){
			System.out.println(e.getMessage());

			//call request again
			AnyCarManufactor.request();
		}
		catch(Exception e){
			System.out.println(e);
		}
		//return the vehicle string array
		return vehicleParams;
	}  //end request

	/*
	* Inputs: partsList
	* Return: string of option information
	* Description: String filled with parts list and costs and calculates total cost
	*/
	public static String getOption(ArrayList<String> partsList){
		String line;  //variable to hold each line of parts string array

		//get last item of partsList
		line = partsList.get(partsList.size()-1);

		//break into string array
		String[] lineArray = line.split("\\s+");

		//return last item of string array
		return lineArray[lineArray.length - 1];
	}

	/*
	* Inputs: partsList that contains everything with $
	* Return: string of cost information
	* Description: String filled with parts list and costs and calculates total cost
	*/
	public static Double getCost(ArrayList<String> partsList){
		//variable to hold total cost of car and options
		double total = 0.0;
		double lineCost;

		String line;  //variable to hold each line of parts string array

		//loop through each element of parts array p and break into line
		for(int i =0; i < partsList.size(); i++){
			line = partsList.get(i);

			//break line into separate array
			String[] lineArray = line.split("\\s+");
			//try to parse each element of lineArray into Double
			for(int y =0; y < lineArray.length; y++){
				try{
					lineCost = Double.parseDouble(lineArray[y]);

					//if successfurl and less than 50.00
					if(lineCost > 50.00){
						total = total + lineCost;
					}
				}//end try
				catch(Exception e){
					//do not change total
					total = total;
				}
			}//end inner for
		} //end outer for

		return total;
	} // end getCost


	/*
	* Inputs: File to search, parent node to search, pid#, tagName to search, option to search for child node with optionNode
	* Return: string of vehicle information
	* Description: Reads file and search criteria to return required vehicle info
	*/
	public static String searchXML(Document document, String parentNode, String pidSearch, String tagName, String optionNode, Boolean childSearch) throws OptionNotFoundException {

		String vehicleParam = "null";  //hold vehicle param

		//reference Joshua Aklin
		// Call type to search for
		NodeList nList = document.getElementsByTagName(parentNode);

		// Iterate through parentNodes to search against
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			vehicleParam = "null"; //hold vehicle param
			// Parent Node
			// Create Parent Node
			Element eElement = (Element) nNode;

			//find match then return the string value for tagName
			if(eElement.getAttribute("id").equalsIgnoreCase(pidSearch)) {

				//determine if stopping here or looking for child node within node (example hatchback optoin)
				if(childSearch){

					//find child node
					try{
						Node childOption = eElement.getElementsByTagName(optionNode).item(0);
						Element hElement = (Element) childOption;
						vehicleParam = hElement.getElementsByTagName(tagName).item(0).getTextContent();
					}
					catch(Exception e){
						System.out.println("Invalid vehicle option search");
						throw new OptionNotFoundException(pidSearch, optionNode, getOptionsList(document, optionNode));
					}
				}
				else{
					vehicleParam = eElement.getElementsByTagName(tagName).item(0).getTextContent();
				}
				//match found - end for loop
				break;
			}

			//add check that if at end of length and vehicle param still null, then throw exception
			if((temp == (nList.getLength() -1)) && (vehicleParam.equalsIgnoreCase("null"))) {
				//throw option found exception
				throw new OptionNotFoundException(pidSearch, parentNode, getOptionsList(document, parentNode));
			}
		} //end for

		return vehicleParam;
	}  //end searchXML method


	/*
	* Inputs: File to search, parent node to search, child node to step into pid#, tagName to search
	* Return: string of vehicle information
	* Description: Reads file and search criteria for child node to return required vehicle info
	*/
	public static String childNodeSearch(Document document, String parentNode,
	String childNode, String pidSearch1, String pidSearch2, String tagName) throws OptionNotFoundException {

		String vehicleParam = "null";  //hold vehicle param
		//reference Joshua Aklin
		// Call type to search for
		NodeList nList = document.getElementsByTagName(parentNode);

		//create node
		Node options = nList.item(0);

		// Iterate through option nodes children to search against
		//reference https://stackoverflow.com/questions/13356534/how-to-read-xml-child-node-values-by-using-a-loop-in-java
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node cNode = nList.item(temp);
			vehicleParam = "null";

			Element eElement = (Element) cNode;

			//if element doesn't match then skip ahead by that elements' child elements
			if(!(eElement.getAttribute("id").equalsIgnoreCase(pidSearch1))){
				//customized to number of child misc options
				temp = temp + 3;

				//add check that if at end of length and vehicle param still null, then throw exception
				//	if((temp >= (nList.getLength() -1)) && (vehicleParam.equalsIgnoreCase("null"))) {
				if(temp >= nList.getLength() -1){
					//throw option found exception
					throw new OptionNotFoundException(pidSearch1, parentNode, getOptionsList(document, parentNode));
				}
			}

			//find match then return the string value for tagName
			else if(eElement.getAttribute("id").equalsIgnoreCase(pidSearch1)) {
				//now run search for child nodes
				NodeList vList = eElement.getElementsByTagName(childNode);

				// Iterate through childNode list to search against
				for (int count = 0; count < vList.getLength(); count++) {

					Node mNode = vList.item(count);
					vehicleParam = "null"; //hold vehicle param

					// step into child node
					Element mElement = (Element) mNode;

					//find match then return the string value for tagName
					if(mElement.getAttribute("id").equalsIgnoreCase(pidSearch2)) {
						vehicleParam = mElement.getElementsByTagName(tagName).item(0).getTextContent();
						//match found - end for loop
						break;
					}  //end if for child node match

					//add check that if at end of length and vehicle param still null, then throw exception
					if((count >= (vList.getLength() -1)) && (vehicleParam.equalsIgnoreCase("null"))) {
						//throw option found exception
						throw new OptionNotFoundException(pidSearch2, parentNode, getOptionsList(document, parentNode));
					}
				}  //end for loop through child node list

				//match found - end for loop
				break;
			}  //end else if match for parent node
			else{
				//do nothing
			}
		} //end for


		return vehicleParam;
	}  //end searchChlidXML method


	/*
	* Input: String user entered PID#
	* Return: string of vehicle make information
	* Description: Reads product ID and determines if ThisAuto, ThatAuto, or OtherAuto
	*/
	public static String getMake(String id){

		//check first digit of PID# for config file
		if (id.charAt(0) == '1'){
			return "ThisAuto";
		}
		else if(id.charAt(0) == '2'){
			return "ThatAuto";
		}
		//don't need to check char b/c we already know its a valid input
		else{
			return "OtherAuto";
		}
	} //end getMake

	/*
	* Input: String user entered PID#
	* Return: string of vehicle type information
	* Description: Reads product ID and determines if Sedan, Coupe, Minivan, SUV, or Truck
	*/
	public static String getType(String id){

		char vehicleTypeChar = id.charAt(1);  //get second digit of PID# for type
		String userCarType;

		//determine the type of vehicle to make (sedan, coupe, minivan, suv, truck)
		switch (vehicleTypeChar){

			//if case matches to vehicle type; set the String variable
			case '1':
			userCarType = "Sedan";
			break;

			case '2':
			userCarType = "Coupe";
			break;

			case '3':
			userCarType = "MiniVan";
			break;

			case '4':
			userCarType = "SUV";
			break;

			case '5':
			userCarType = "Truck";
			break;

			default:
			userCarType = "Type not found!";
			break;
		}  //end switch

		return userCarType;
	} //end getType

	/*
	* Input: Int for config file to use
	* Return: int array of pid# formatted
	* Description:  Reads int for config file and formats the pid number
	* Reference:  Joshua Aklin
	*/

	public static int[] pidFormat(int m) {
		if(m == 1){
			int[] a = {4,5,6,7,8,9,10,11,12};
			return a;
		}
		else if(m == 2){
			int[] a = {6,7,8,9,10,11,12,13,15};
			return a;
		}
		else if(m == 3){
			int[] a = {4,5,6,7,8,9,10,11,13};
			return a;
		}
		else{
			System.out.println("Error");
			int[] a = null;
			return a;
		}
	}  //end pidFormat method


	/*method to return set of options not seleected
	getOptionsList(counter, optionNameIndexes[i+1])
	/*
	* Input: String - counter (that equals optionName option searching for) & end index to go through
	* Return: String with available list of options for optionName
	* Description: builds a string of options available for specific option type such as Exterior, interior, misc..
	*/
	public static String getOptionsList(Document document, String parentNode){
		//String to build-out with options list
		String optionsList = "";

		// Call type to search for
		NodeList nList = document.getElementsByTagName(parentNode);

		// Iterate through parentNodes to search against
		//reference:  Joshua Aklin
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			// Create Parent Node
			Element eElement = (Element) nNode;

			optionsList = optionsList + "\n" + parentNode + " - id: " + eElement.getAttribute("id") +
			" with Value of " + eElement.getElementsByTagName("Value").item(0).getTextContent();
		}  //end for

		return optionsList;
	}

}  //end ConfigParseXML class
