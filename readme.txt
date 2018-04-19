IMPORTANT:  READ THIS FILE BEFORE USING AnyCarManufactor

Project:
AnyCarManufactor

Description:
AnyCarManufactorproject takes in a user's input for a specific car to be made.
This PID number is matched against a a set of config files (ThisAuto.xml, ThatAuto.xml, and OtherAuto.xml)
of the available models, options, and prices.  The PID number is ran
against the xml file options to build a specified vehicle and output the user's
selections and associated costs.  The vehicle options are:  Sedan, Coupe, Truck, SUV, and Minivan.
Custom exceptions are built-in to let the user know specific formats for a PID number.

Prerequisites:
minimum Java version 8 update 91

Files List:
to run: 
Manifest.mf
IrvinLab4.jar
ThisAuto.xml
ThatAuto.xml
OtherAuto.xml
AnyCarManufactor.class
Vehicle.class
Sedan.class
Coupe.class
Truck.class
SUV.class
Minivan.class
AnyCarException.class
ConfigParserXML.class

to build:
AnyCarManufactor.java
Vehicle.java
Sedan.java
Coupe.java
Truck.java
SUV.java
Minivan.java
AnyCarException.java
ConfigParserXML.java
ThisAuto.config
ThatAuto.config
OtherAuto.config

Build Instructons:
Compile and run on the command line using the JDK compiler.  After extracting
the files to a folder, you can compile with javac *.java then run the program
using command java AnyCarMain

Run instructions:
If you have java runtime environment, use the java -jar IrvinLab4.jar to run the program

Authors:
Jillian Irvin
AnyCarManufactor v4
