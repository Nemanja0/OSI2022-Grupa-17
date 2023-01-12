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
	public static User current_user = null;
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
		String username, password;
		while(true) {
			System.out.println("Please enter your username:");
			username = scanner.nextLine();
			System.out.println("Please enter your password:");
			password = scanner.nextLine();
			for(User usr : users) {
				if (usr.getUsername().equals(username))
					if(usr.getPassword().equals(password)) {
						current_user = usr;
						break;
					}
			}
			if(current_user != null)
				break;
			System.out.println("Invalid user or password!");
			String yes_no;
			System.out.println("Try again?(yes, no)");
			yes_no = scanner.nextLine();
			if("yes".equals(yes_no) == false) {
				return false;
			}
		}
		current_user.setNumberOfLogin(current_user.getNumberOfLogin()+1);
		if(current_user.getNumberOfLogin() == max_login) {
			System.out.println("You have logged in " + max_login + " times! You must change your password!");
			current_user.changePassword();
			current_user.setNumberOfLogin(0);
		}
		return true;
	}
	
	public static Event checkEvent(String event) {
		for(Event ev : events) {
			if(ev.getName().equals(event))
				return ev;
		}
		return null;
	}
	
	public static boolean checkUsername(String username) {
		for(User usr : users) {
			if (usr.getUsername().equals(username))
				return false;
		}
		return true;
	}
	public static boolean register() {
		String name, surname, username, password;
		System.out.println("Please enter your name:");
		name = scanner.nextLine();
		System.out.println("Please enter your surname:");
		surname = scanner.nextLine();
		while(true) {
			System.out.println("Please enter your username:");
			username = scanner.nextLine();
			if(checkUsername(username))
				break;
			else {
				String yes_no;
				System.out.println("Username already exists!");
				System.out.println("Try again?(yes, no)");
				yes_no = scanner.nextLine();
				if("yes".equals(yes_no) == false) {
					return false;
				}
			}
		}
		while(true) {
			System.out.println("Please enter your password:");
			password = scanner.nextLine();
			if(password.length() >= 8)
				break;
			else{
				String yes_no;
				System.out.println("Password is too short! Must be 8 characters long or higer!");
				System.out.println("Try again?(yes, no)");
				yes_no = scanner.nextLine();
				if("yes".equals(yes_no) == false) {
					return false;
				}
			}
		}
		if(current_user != null) {
			while(true) {
				System.out.println("If you want to create administrator account type ADMIN");
				System.out.println("If you want to create client account type CLIENT");
				System.out.println("If you want to exit type EXIT");
				String option = Main.scanner.nextLine();
				switch(option) {
					case "ADMIN":
						users.add(new Administrator(name, surname, username, password));
						break;
					case "CLIENT":
						users.add(new Client(name, surname, username, password));
						break;
					case "EXIT":
						return false;
					default:
						System.out.println("Invalid input ! Try again.");
						break;
				}
			}
		}
		else {
			if(first_run) {
				users.add(new Administrator(name, surname, username, password));
			}
			else {
				users.add(new RegularUser(name, surname, username, password));
				new File(users_filename + "/" + username).mkdir();
				new File(users_filename + "/" + username + "/ulaznice").mkdir();
			}
		}
		return true;
	}
	
	public static boolean logout() {
		String yes_no;
		System.out.println("Are you sure? (yes, no)");
		yes_no = scanner.nextLine();
		if("yes".equals(yes_no)) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		String option;
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
				System.out.println("First initialization failed - exiting...");
				return;
			}
		}
		System.out.println("Welcome user! Please choose one of the following options: ");
		while(true) {
			System.out.println("1. Login\n2. Register\n3. Exit");
			option = scanner.nextLine();
			if("1".equals(option)) {
				if(login()) {
					System.out.println("Welcome user " + current_user.getUsername());
					break;
				}
				else {
					System.out.println("Login failed! - exiting...");
					return;
				}
			}
			else if("2".equals(option)) {
				if(register()) {
					System.out.println("Registration successful!");
					System.out.println("You can login now!");
				}
				else {
					System.out.println("Registration failed! - exiting...");
					return;
				}
			}
			else if("3".equals(option)) {
				System.out.println("Goodbye!");
				return;
			}
			else {
				System.out.println("Wrong option! Choose again.");
			}
		}
		while(true) {
			if(current_user instanceof RegularUser) {
				System.out.println("1. Purchase ticket\n2. Browse events\n3. Cancelling purchased tickets\n4. Change password\n5. Logout");
				option = scanner.nextLine();
				switch(option) {
					case "1":
						break;
					case "2":
						break;
					case "3":
						break;
					case "4":
						current_user.changePassword();
						break;
					case "5":
						if(logout()) {
							System.out.println("Logout successful!");
							break;
						}
					default:
						System.out.println("Wrong option! Choose again.");
				}
			}
			else if(current_user instanceof Administrator) {
				System.out.println("1. Accounts management\n2. Cancelling client events\n3. Create Administrator/Client account\n4. Change password\n5. Logout");
				option = scanner.nextLine();
				switch(option) {
					case "1":
						break;
					case "2":
						break;
					case "3":
						break;
					case "4":
						current_user.changePassword();
						break;
					case "5":
						if(logout()) {
							System.out.println("Logout successful!");
							break;
						}
					default:
						System.out.println("Wrong option! Choose again.");
				}
			}
			else {
				System.out.println("1. Create event\n2. Cancel event\n3. Browse sold tickets\n4. Change password\n5. Logout");
				option = scanner.nextLine();
				switch(option) {
					case "1":
						break;
					case "2":
						break;
					case "3":
						break;
					case "4":
						current_user.changePassword();
						break;
					case "5":
						if(logout()) {
							System.out.println("Logout successful!");
							break;
						}
					default:
						System.out.println("Wrong option! Choose again.");
				}
			}
		}
	}
	
	public static boolean checkTimeValidity(String time) {
		String splitter[] = time.split(":");
		if(splitter.length != 2)
			return false;
		int hours, minutes;
		try {
			if(splitter[0].charAt(0) == '0')
				splitter[0] = splitter[0].substring(1);
			hours = Integer.parseInt(splitter[0]);
			if(hours < 0 || hours > 24)
				return false;
			if(splitter[1].charAt(0) == '0')
				splitter[1] = splitter[1].substring(1);
			minutes = Integer.parseInt(splitter[1]);
			if(minutes < 0 || minutes > 60)
				return false;
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
	public static boolean checkDateValidity(String date) {
		String splitter[] = date.split(".");
		if(splitter.length != 3)
			return false;
		int day,month,year;
		try {
			if(splitter[1].charAt(0) == '0')
				splitter[1] = splitter[1].substring(1);
			month = Integer.parseInt(splitter[1]);
			if(month < 0 || month > 12)
				return false;
			int allowed_day = getAllowedDay(month);
			if(splitter[0].charAt(0) == '0')
				splitter[0] = splitter[0].substring(1);
			day = Integer.parseInt(splitter[1]);
			if(day < 0 || day > allowed_day)
				return false;
			year = Integer.parseInt(splitter[2]);
			if(year < Calendar.getInstance().get(Calendar.YEAR))
				return false;
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
	public static int getAllowedDay(int month) {
		switch(month){
			case 1:
				return 31;
			case 2:
				return 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			default:
				return 31;
		}
	}
}
