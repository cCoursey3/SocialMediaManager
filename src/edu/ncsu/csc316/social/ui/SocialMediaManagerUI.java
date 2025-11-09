package edu.ncsu.csc316.social.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.ncsu.csc316.social.manager.ReportManager;

/**
 * This file creates a gui interface that prompts a user for files and prints
 * reports
 * 
 * @author Chloe Coursey
 *
 */
public class SocialMediaManagerUI {

	/** this field creates a report manager */
	private static ReportManager rmanager;

	/**
	 * calls a user interface
	 * 
	 * @param args the arguments inputed from a user
	 */
	public static void main(String[] args) {
		userInterface();

	}

	/**
	 * This method prompts the user for the files and calls for the user to select
	 * one of three options
	 */
	private static void userInterface() {
		String personFile;
		String connectionFile;
		String outputFile;

		Scanner in = new Scanner(System.in);
		System.out.print("Please enter an input file of people: ");

		personFile = in.nextLine();

		System.out.print("\nPlease enter an input file of connections: ");
		connectionFile = in.nextLine();

		System.out.print("\nPlease enter an output file: ");
		outputFile = in.nextLine();

		// capture START time first
		long start = System.currentTimeMillis();

		// Now construct the manager and call the appropriate method
		ReportManager manager;
		try {
			manager = new ReportManager(personFile, connectionFile);
			manager.getConnectionsByPerson();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	
		// capture END time now
		long end = System.currentTimeMillis();
		

		// calculate ELASPED TIME
		long duration = end - start;
		
		System.out.println(duration);

		try {
			rmanager = new ReportManager(personFile, connectionFile);
			selectReport(outputFile);

		} catch (FileNotFoundException e) {
			System.out.print("\nMust enter valid filenames\n");
			userInterface();
		}
		in.close();
	}

	/**
	 * This method prompts the user to chose three options, either to create a
	 * report by person, a report by platform, or to quit the program
	 */
	private static void selectReport(String outputFile) {

		Scanner in = new Scanner(System.in);
		System.out.print("Please chose an option A, B, or C from the choices below: \n\n"
				+ "A) Generate a report of the friendships/connections for each person\n"
				+ "B) Generate a report of the friendships/connections for each social media platform"
				+ "\n\nC) Quit\n");

		String input = in.next();
		char option = input.toUpperCase().charAt(0);

		switch (option) {
		case 'A':
			try {
				PrintWriter out = new PrintWriter(new File(outputFile));
				out.println(rmanager.getConnectionsByPerson());
				out.close();
			} catch (FileNotFoundException e) {
				System.out.print(e.getMessage());
			}

			break;
		case 'B':
			try {
				PrintWriter out = new PrintWriter(new File(outputFile));
				out.println(rmanager.getConnectionsByPlatform());
				out.close();
			} catch (FileNotFoundException e) {
				System.out.print(e.getMessage());
			}
			break;
		case 'C':
			System.exit(1);
			break;
		default:
			System.out.println("Invalid choice");
			selectReport(outputFile);
		}
		in.close();
	}
}
