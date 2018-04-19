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

public class Coupe extends Vehicle {
	private boolean turbo;
	private double engine;

	//construtor method
	public Coupe(String makeInput, String modelInput, String[] optionsInput, String[] partsInput, double priceInput,
							Boolean turboInput, double engineInput){

		//set inherited data with super String makeInput, String modelInput, String[] optionsInput, double priceInput
		super(makeInput, modelInput, optionsInput, priceInput);

		//use setters to set instance variables
		setTurbo(turboInput);
		setEngine(engineInput);
		setParts(partsInput);

	}  //end constructor

	//setter for HatchBack property
	public void setTurbo(boolean t){
		turbo = t;
	}

	//getter for hatchback
	public boolean getTurbo(){
		return turbo;
	}

	//setter for engine
	public void setEngine(double e){
		engine = e;
	}

	//getter for engine
	public double getEngine() {
		return engine;
	}

	//override abstract method from vehicle.java; p is the options array
	@Override
	public void setParts(String[] p){
		//reference https://stackoverflow.com/questions/7350173/problem-with-assigning-an-array-to-other-array-in-java
		parts = p.clone();
	}  //end setParts

} //end Coupe class
