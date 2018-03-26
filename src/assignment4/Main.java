package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

import org.omg.CORBA.DynAnyPackage.Invalid;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Method;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {

//        try {
//            Critter.makeCritter("assignment4.Craig");
//            Critter.makeCritter("assignment4.Craig");
//        }catch (InvalidCritterException e){
//            System.out.println(e);
//        }
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // System.out.println("GLHF");
        while (controller())
        /* Write your code above */
        System.out.flush();

    }

    /**
     * a method that controls the world with inputs from the user, based on the given commands in the instructions
     * @return true if method should be called again, false otherwise
     */
    private static boolean controller() {
        String commandString;
        commandString = kb.nextLine();
        commandString = commandString.replaceAll("\t", " ");
        String command[] = commandString.split(" ");
        try {
            switch (command[0]) {
                case "quit":
                    return false;

                case "show":
                    if (command.length == 1) {
                        Critter.displayWorld();
                    } else {
                        System.out.println("error processing: " + commandString);
                    }
                    return true;
                case "step":
                    int numSteps;
                    if (command.length == 1) {
                        Critter.worldTimeStep();
                    }
                    if (command.length > 1) {
                        numSteps = Integer.parseInt(command[1]);
                        for (int i = 0; i < numSteps; i++)
                            Critter.worldTimeStep();
                    }
                    return true;
                case "seed":
                    int seedValue;
                    if (command.length == 2) {
                        seedValue = Integer.parseInt(command[2]);
                        Critter.setSeed(seedValue);
                    } else {
                        System.out.println("error processing: " + commandString);
                    }
                    return true;

                case "make":
                    if (command.length == 2) {
                        Critter.makeCritter("assignment4." + command[1]);
                    } else if (command.length == 3) {
                        int numCritters;
                        numCritters = Integer.parseInt(command[2]);
                        for (int i = 0; i < numCritters; i++)
                            Critter.makeCritter("assignment4." + command[1]);
                    } else {
                        System.out.println("error processing: " + commandString);
                    }
                    return true;
                case "stats":
                    if (command.length == 2) {
                        List<Critter> classList;
                        Class c = Class.forName("assignment4." + command[1]);
                        Critter crt = (Critter)c.newInstance();
                        classList = Critter.getInstances("assignment4." + command[1]);
                        Method m;
                        m=crt.getClass().getMethod("runStats", List.class);
                        m.invoke(crt,classList);


                        return true;
                    } else {
                        System.out.println("invalid command: " + commandString);
                        return true;
                    }
                default:
                    System.out.println("invalid command: " + commandString);
                    return true;
            }
        } catch (NumberFormatException n) {
            System.out.println("error processing: " + commandString);
            return true;
        } catch (InvalidCritterException e) {
            System.out.println("error processing: " + commandString);
            return true;
        }catch (ClassNotFoundException e){
            System.out.println("error processing: " + commandString);
            return true;
        }catch (InstantiationException e){
            System.out.println("error processing: " + commandString);
            return true;
        }catch (IllegalAccessException e){
            System.out.println("error processing: " + commandString);
            return true;
        }catch (NoSuchMethodException e){
            System.out.println("error processing: " + commandString);
            return true;
        }catch (InvocationTargetException e){
            System.out.println("error processing: " + commandString);
            return true;
        }
    }
}
