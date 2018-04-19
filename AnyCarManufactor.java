/*
* Name:  Jillian Irvin
* Class: CIT-244
* Date:  9.18.17
* Description: AnyCarManufactor is the main file of your application. Main will read in the configuration file and initiate the application.
*/

import java.util.Scanner;
import java.io.*;
import java.lang.*;

class AnyCarManufactor {
	/*
	* Input: String Array args – Command line arguments
	* Return: Void
	* Description: Main Method for initiating AnyCar application. When called will read line by line AnyCar.config file and append to global variable anyCarConfig. Finally, Main will call request to initiate the interaction with the user.
	*/
	public static void main(String[] args){
		request();
	}


	/*
	* Input: None
	* Return: Void
	* Description: Requests user to input PID(String) and calls buildVehicle to start building each vehicle. Request will continue to prompt the user until the user inputs -1, then request will return.
	*/
	public static void request() {
		boolean continueUserInput = true; //variable to hold if user wants to continue entering PID#s
		boolean invalidPID;  //to check validity of user input
		String userPIDInput;  //variable to store PID#
		Scanner keyboard = new Scanner(System.in);  //create Scanner object for user input

		//prompt user to enter 12-digit PID number until they end with -1
		while (continueUserInput){

			//initialize userPIDInput
			userPIDInput = "";
			//initialize invalidPID to true for each new start
			invalidPID = true;

			//continue to prompt user for PID# until quits or valid one
			while (invalidPID){

				//prompt user
				System.out.print("Enter a PID number (-1 to exit): ");
				userPIDInput = keyboard.nextLine();

				if (userPIDInput.equals("-1")){
					//exit while loops and program
					System.out.println("Exiting program");
					System.exit(0);
				}
				else{ //check PID validity
					//if returns true then continue to prompt user
					//if returns false then exit invalidPID loop and continue with userContinue loop
					try{
						validInput(userPIDInput);
						invalidPID = false;
					}

					//catch if there are any non-digits in PID#
					catch(StringException e){
						//keep invalid PID to true
						invalidPID = true;
						System.out.println(e.getMessage());
					}
					//if 1,2,3 not inputted as first digit, catch and still request input
					catch(MakeNotFoundException e){
						invalidPID = true;
						System.out.println(e.getMessage());
					}
					//if specific make doesn't have correct length PID #
					catch(MakeFormatException e){
						invalidPID = true;
						System.out.println(e.getMessage());
					}
					//if first digit of PID doesn't identify 1 of car types
					catch(ModelNotFoundException e){
						invalidPID = true;
						System.out.println(e.getMessage());
					}
					//last catch block
					catch(Exception e){
						System.out.println("Unknown error");
						System.exit(0);
					}
				} //end else to check pid validity
			}  //end invalidPID while loop


			//call method to build vehicle
			buildVehicle(userPIDInput);
		
		}  //end continueUserInput whlie loop
	}  //end request method


	/*
	* Input: String pid - Product ID number
	* Return: Void
	* Description: Create Vehicle object and call getters and setters for make, model, options, parts, and price, and print the Vehicle object using it’s toString method, and return
	* The first digit is the make and exhibits the following behavior
	*               1: ThisAuto
	*               2: ThatAuto
	*               3: OtherAuto
	* The second is vehicle type and exhibits the following behavior:
	*               1: Sadan
	*               2: Coupe
	*               3: Minivan
	*               4: SUV
	*               5: Truck
	*/
	public static void buildVehicle(String id) {

		String[] vehicleParams; //hold results of ConfigParserXML for vehicle info
		Vehicle userCar = null;

		//get second digit of PID# - use to determine which vehicle to instantiate
		char vehicleTypeChar = id.charAt(1);

		//build vehicle using vehicleParams variable with setters
		//[type, make, modle, options, parts, price, vehicle option1, vehicle option2]

		//set array equal to result of ConfigParserXML class' request method
		//do i need a for loop for each element??? - no
		vehicleParams = ConfigParserXML.request(id);

		//turn options String into an array to build vehicle - delimeter = comma
		String[] optionsArray = vehicleParams[3].split(",");

		//turn parts String into an array to build vehicle - delimeter = comma
		String[] partsArray = vehicleParams[4].split(",");

		//convert price into doulb
		double price = Double.parseDouble(vehicleParams[5]);

		//turn parts string into an array to build vehicle
		// System.out.println("vehicleParams - type: " + vehicleParams[0]);
		// System.out.println("vehicleParams - make: " + vehicleParams[1]);
		// System.out.println("vehicleParams - model: " + vehicleParams[2]);
		// System.out.println("vehicleParams - options: " + vehicleParams[3]);
		// System.out.println("vehicleParams -parts:" + vehicleParams[4]);
		// System.out.println("vehicleParams - price:" + vehicleParams[5]);
		// System.out.println("vehicleParams - vehicle_option1: " + vehicleParams[6]);
		// System.out.println("vehicleParams - vehicle_option1: " + vehicleParams[7]);

		//determine the type of vehicle to make (sedan, coupe, minivan, suv, truck)
		switch (vehicleTypeChar){

			//if case matches to vehicle type; instantiate that vehicle type
			//Sedan
			case '1':

			//convert option 1 to hatchback boolean
			Boolean hatchback = Boolean.parseBoolean(vehicleParams[6]);
			//convert option 2 to integer for engine
			int engineSedan = Integer.parseInt(vehicleParams[7]);

			//create Sedan with options to be updated with setter methods and config file
			userCar = new Sedan(vehicleParams[1], vehicleParams[2], optionsArray, partsArray, price, hatchback, engineSedan);
			System.out.println("print result...");
			//call toString
			System.out.println(userCar.toString());
			break;

			//Coupe
			case '2':
			//convert option 1 to turbo boolean
			Boolean turbo = Boolean.parseBoolean(vehicleParams[6]);
			//convert option 2 to double for engine
			double engineCoupe = Double.parseDouble(vehicleParams[7]);

			//create coupe with options to be updated with setter methods and config file
			userCar = new Coupe(vehicleParams[1], vehicleParams[2], optionsArray, partsArray, price, turbo, engineCoupe);
			System.out.println("print result...");
			//call toString
			System.out.println(userCar.toString());
			break;

			//minivan
			case '3':
			//convert option 2 to integer for engine
			int seatsMinivan = Integer.parseInt(vehicleParams[6]);

			//create coupe with options to be updated with setter methods and config file
			userCar = new Minivan(vehicleParams[1], vehicleParams[2], optionsArray, partsArray, price, seatsMinivan);
			System.out.println("print result...");
			//call toString
			System.out.println(userCar.toString());
			break;

			//SUV
			case '4':
			//convert option 1 to integer rows
			int rows = Integer.parseInt(vehicleParams[6]);
			//convert option 2 to integer for seats
			int seatsSUV = Integer.parseInt(vehicleParams[7]);

			//create coupe with options to be updated with setter methods and config file
			userCar = new SUV(vehicleParams[1], vehicleParams[2], optionsArray, partsArray, price, rows, seatsSUV);
			System.out.println("print result...");
			//call toString
			System.out.println(userCar.toString());
			break;

			//truck
			case '5':
			//convert option 1 to integer bedSize
			int bedSize = Integer.parseInt(vehicleParams[6]);
			//convert option 2 to integer for engine
			int engineTruck = Integer.parseInt(vehicleParams[7]);

			//create coupe with options to be updated with setter methods and config file
			userCar = new Truck(vehicleParams[1], vehicleParams[2], optionsArray, partsArray, price, bedSize, engineTruck);
			System.out.println("print result...");
			//call toString
			System.out.println(userCar.toString());
			break;

			default:
			break;
		}  //end switch

		//if statements to check what first element 'type' is...sedan, coupe...
		//call constructor depending on which one

	} //end buildVehicle


	/*
	* Input: String user entered PID#
	* Return: boolean
	* Description: Checks that user's PID # is valid.
	* The boolean return determines if the user should be prompted again
	*/
	public static void validInput(String id) throws StringException,
	MakeNotFoundException,
	MakeFormatException,
	ModelNotFoundException {

		char vehicleTypeChar;
		String makeInput = "";

		//loop through characters of pid input and check if is a digit
		for (int i = 0; i < id.length(); i++){
			//check if each character of pid string is a digit
			if (! Character.isDigit(id.charAt(i))) {
				throw new StringException();
			}
		}

		//check first digit of PID# for config file
		if (id.charAt(0) == '1'){
			//check pid for ThisAuto is correct length of 12 digits
			if (id.length() != 12){
				//throw exception
				throw new MakeFormatException(12);
			}
			makeInput = "ThisAuto";
		} //end outer if

		else if(id.charAt(0) == '2'){
			//check pid for ThatAuto is correct length of 15 digits
			if (id.length() != 15){
				//throw exception
				throw new MakeFormatException(15);
			}
			makeInput = "ThatAuto";
		} //end else if

		else if(id.charAt(0) == '3'){
			//check pid for ThatAuto is correct length of 15 digits
			if (id.length() != 13){
				//throw exception
				throw new MakeFormatException(13);
			}
			makeInput = "OtherAuto";
		} //end else if

		else{
			//throw MakeNotFound Exception
			throw new MakeNotFoundException();
		}

		//now check validity of vehicle type
		//get second digit of PID#
		vehicleTypeChar = id.charAt(1);

		//throw ModelNotFoundExcept for just character-element 2 in pid input number
		if ( (vehicleTypeChar != '1') && (vehicleTypeChar != '2') &&(vehicleTypeChar != '3') &&
		(vehicleTypeChar != '4') && (vehicleTypeChar != '5')) {

			//throw overload constructor exception given first character
			throw new ModelNotFoundException(Character.toString(vehicleTypeChar), makeInput);
		}
	}  //end validInput method

}
