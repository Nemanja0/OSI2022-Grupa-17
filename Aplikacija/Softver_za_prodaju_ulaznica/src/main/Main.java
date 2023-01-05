package main;

import java.util.*;
import users.*;
import events.Event;
import java.io.*;

public class Main {
	
	public static String config = "config.txt";
	public static String users_ser = "users.ser";
	public static String events_ser = "events.ser";
	public static String users_filename;
	public static String events_filename;
	public static int max_login;
	public static ArrayList<User> users = new ArrayList<>();
	public static ArrayList<Event> events = new ArrayList<>();
	public static boolean first_run = false;
	public static Scanner scanner = new Scanner(System.in);
	
	public static void initialize() throws Exception {
		File input = new File(config);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		users_filename = reader.readLine();
		events_filename = reader.readLine();
		max_login = Integer.parseInt(reader.readLine());
		reader.close();
		input = new File(users_filename);
		File files[] = input.listFiles();
		if(files.length == 0) {
			first_run = true;
			return;
		}
		int index = -1;
		for(int i=0;i<files.length;i++) {
			if(files[i].toString().equals(users_ser)) {
				index = i;
				break;
			}
		}
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(files[index]));
		users = (ArrayList<User>)ois.readObject();
		ois.close();
		ois = new ObjectInputStream(new FileInputStream(events_filename + "/" + events_ser));
		events = (ArrayList<Event>)ois.readObject();
		ois.close();
	}
	
	public static boolean login() {
		return true;
	}
	
	public static boolean register() {
		return true;
	}
	
	public static void main(String[] args) {
		try {
			initialize();
		} catch (Exception e) {
			System.err.println("Error working with config file - exiting.");
			return;
		}
		if(first_run) {
			System.out.println("Welcome, this is your first time entering the system.");
			System.out.println("Please login with credentials admin/admin");
			if(login() && register()) {
				System.out.println("Successful first time registration.");
			}
			else {
				System.out.println("First initialization failed - exiting.");
				return;
			}
		}
		
	}

}
