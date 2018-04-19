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

public class SUV extends Vehicle {
	private int rows;
	private int seats;

	//construtor method
	public SUV(String makeInput, String modelInput, String[] optionsInput, String[] partsInput, double priceInput,
							int rowsInput, int seatsInput){

		//set inherited data with super String makeInput, String modelInput, String[] optionsInput, double priceInput
		super(makeInput, modelInput, optionsInput, priceInput);

		//use setters to set instance variables
		setRows(rowsInput);
		setSeats(seatsInput);
		setParts(partsInput);
	}  //end constructor

	//setter for HatchBack property
	public void setRows(int r){
		rows = r;
	}

	//getter for hatchback
	public int getRows(){
		return rows;
	}

	//setter for engine
	public void setSeats(int s){
		seats = s;
	}

	//getter for engine
	public int getSeats() {
		return seats;
	}

	//override abstract method from vehicle.java; p is the options array
	@Override
	public void setParts(String[] p){
		//reference https://stackoverflow.com/questions/7350173/problem-with-assigning-an-array-to-other-array-in-java
		parts = p.clone();
	}  //end setParts

} //end Sedan class
