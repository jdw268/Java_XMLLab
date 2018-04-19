/*
* Name:  Jillian Irvin
* Class: CIT-244
* Date:  10.8.17
* Description: This class is the Car class that extends the Vehicle class
* It's used to create a Sedan object.
*/
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class Truck extends Vehicle {
	private int bedSize;
	private int engine;

	//construtor method
	public Truck(String makeInput, String modelInput, String[] optionsInput, String[] partsInput, double priceInput,
							int bedSizeInput, int engineInput){

		//set inherited data with super String makeInput, String modelInput, String[] optionsInput, double priceInput
		super(makeInput, modelInput, optionsInput, priceInput);

		//use setters to set instance variables
		setBedSize(bedSizeInput);
		setEngine(engineInput);
		setParts(partsInput);
	}  //end constructor

	//setter for HatchBack property
	public void setBedSize(int b){
		bedSize = b;
	}

	//getter for hatchback
	public int getBedSize(){
		return bedSize;
	}

	//setter for engine
	public void setEngine(int e){
		engine = e;
	}

	//getter for engine
	public int getEngine() {
		return engine;
	}

	//override abstract method from vehicle.java; p is the options array
	@Override
	public void setParts(String[] p){
		//reference https://stackoverflow.com/questions/7350173/problem-with-assigning-an-array-to-other-array-in-java
		parts = p.clone();
	}  //end setParts

} //end Sedan class
