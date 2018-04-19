/*
* Name:  Jillian Irvin
* Class: CIT-244
* Date:  9.18.17
* Description: This class is an abstract class.
* It's used to instantiate common characteristics of a vehicle using an inputted PID#.
*/

import java.io.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

public abstract class Vehicle {
	private String make;
	private String model;
	private String[] options;
	protected String[] parts;
	private double price;

	//construtor method
	public Vehicle(String makeInput, String modelInput, String[] optionsInput, double priceInput){

		//call setters with input parameters from constructor
			setMake(makeInput);
			setModel(modelInput);
			setOptions(optionsInput);
			setPrice(priceInput);

	}  //end constructor

	//sets instance variable make
	public void setMake(String m){
		make = m;
	}

	//returns instance variable make
	public String getMake(){
		return make;
	}

	//sets instance variable model
	public void setModel(String m){
		model = m;
}
	//getter for object model instance variable
	public String getModel(){
		return model;
	}

	//sets instance variable model
	public void setOptions(String[] o){
		//clone input array to instance variable
		//reference https://stackoverflow.com/questions/7350173/problem-with-assigning-an-array-to-other-array-in-java
		options = o.clone();

	}

	//getter for options instance variable
	public String[] getOptions(){
		return options;
	}

	//to be overridden by child class
	public abstract void setParts(String[] p);

	//getter for parts array
	public String[] getParts(){
		return parts;
	}

	//setter for price
	public void setPrice(double p){
		price = p;
	}

	//getter for price
	public double getPrice(){
		return price;
	}


	public String toString(){
		//strings to join together for return statement
		String userCarMake;
		String userCarModel;

		//create StringBuilder object to get strings from arrays
		StringBuilder userCar = new StringBuilder();

		//create format object
		DecimalFormat formatter = new DecimalFormat("$###,###,##0.00");

		//first part of string is make
		userCarMake = "Make: " + this.make;

		//define userCarModel string
		userCarModel = "\nModel: " + this.model;

		//add Make and Model strings to string builder
		userCar.append(userCarMake + userCarModel);

		//format with "Options" section
		userCar.append("\nOptions:");

		//loop through options string array to append to userCar string
		for (int i =0; i < this.options.length; i ++){
			userCar.append("\n"+options[i]);
		}

		//format with "Parts" section
		userCar.append("\nParts:");

		//loop through parts string array to append to userCar string
		for (int i =0; i < this.parts.length; i ++){
			userCar.append("\n"+ this.parts[i]);
		}

		//append with price
		userCar.append("\nPrice: " + formatter.format(price));

		return userCar.toString();
	} //end toString

} //end Vehicle class
