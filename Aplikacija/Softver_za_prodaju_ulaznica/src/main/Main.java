package main;

import java.util.*;
import users.*;
import events.Event;
import java.io.*;

public class Main {
	
	public static String config = "config.txt";
	public static String users_ser = "users.ser";
	public static String events_ser = "events.ser";
	public static String suspensions_ser = "suspensions.ser";
	public static String reasons_ser = "reasons.ser";
	public static String users_filename;
	public static String events_filename;
	public static String suspension_filename;
	public static int max_login;
	public static User current_user = null;
	public static ArrayList<User> users = new ArrayList<>();
	public static ArrayList<Event> events = new ArrayList<>();
	public static ArrayList<String> suspended_usernames = new ArrayList<>();
	public static ArrayList<String> suspend_reasons= new ArrayList<>();
	public static boolean first_run = false;
	public static Scanner scanner = new Scanner(System.in);
	
	public static void initialize() throws Exception {
		File input = new File(config);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		users_filename = reader.readLine();
		events_filename = reader.readLine();
		suspension_filename = reader.readLine();
		max_login = Integer.parseInt(reader.readLine());
		reader.close();
		input = new File(users_filename);
		File files[] = input.listFiles();
		if(files.length == 0) {
			first_run = true;
			return;
		}
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(users_filename + "/" + users_ser));
		users = (ArrayList<User>)ois.readObject();
		ois.close();
		ois = new ObjectInputStream(new FileInputStream(events_filename + "/" + events_ser));
		events = (ArrayList<Event>)ois.readObject();
		ois.close();
		ois = new ObjectInputStream(new FileInputStream(suspension_filename + "/" + suspensions_ser));
		suspended_usernames = (ArrayList<String>)ois.readObject();
		ois.close();
		ois = new ObjectInputStream(new FileInputStream(suspension_filename + "/" + reasons_ser));
		suspend_reasons = (ArrayList<String>)ois.readObject();
		ois.close();
	}
	
	public static boolean login() {
		String username, password;
		while(true) {
			System.out.println("Please enter your username:");
			username = scanner.nextLine();
			System.out.println("Please enter your password:");
			password = scanner.nextLine();
			if(first_run) {
				if("admin".equals(password) && "admin".equals(username)) 
					return true;
			}
			else {
				for(User usr : users) {
					if (usr.getUsername().equals(username))
						if(usr.getPassword().equals(password)) {
							if(suspended_usernames.contains(username)) {
								System.out.println("ALERT: Your account has been suspended by an administrator.");
								System.out.println("Reason for suspension: " + suspend_reasons.get(suspended_usernames.indexOf(username)));
								System.out.println("Logging in is not allowed.");
								return false;
							}
							current_user = usr;
							break;
						}
				}
				if(current_user != null)
					break;
			}
			System.out.println("Invalid username or password!");
			String yes_no;
			System.out.println("Try again?(yes, no)");
			yes_no = scanner.nextLine();
			if("yes".equals(yes_no) == false) {
				return false;
			}
		}
		if(current_user.isPasswordCancelled()) {
			System.out.println("ALERT: Your password has been cancelled by an administrator !");
			System.out.println("You have to change your password to continue.");
			current_user.changePassword();
			current_user.setPasswordCancelled(false);
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
				System.out.println("Password is too short! Must be 8 characters long or longer!");
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
						return true;
					case "CLIENT":
						users.add(new Client(name, surname, username, password));
						return true;
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
				first_run = false;
				users.add(new Administrator(name, surname, username, password));
			}
			else {
				users.add(new RegularUser(name, surname, username, password));
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
	
	public static void exit() throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(users_filename + "/" + users_ser));
		oos.writeObject(users);
		oos.close();
		oos = new ObjectOutputStream(new FileOutputStream(events_filename + "/" + events_ser));
		oos.writeObject(events);
		oos.close();
		oos = new ObjectOutputStream(new FileOutputStream(suspension_filename + "/" + suspensions_ser));
		oos.writeObject(suspended_usernames);
		oos.close();
		oos = new ObjectOutputStream(new FileOutputStream(suspension_filename + "/" + reasons_ser));
		oos.writeObject(suspend_reasons);
		oos.close();
		System.out.println("~~~ GOODBYE ~~~");
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
			System.out.println("Please login with credentials admin/admin, and register a new administrator account.");
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
					try {
						exit();
					} catch (Exception e) {
						System.out.println("ERROR: Error during serialization.");
					}
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
					try {
						exit();
					} catch (Exception e) {
						System.out.println("ERROR: Error during serialization.");
					}
					return;
				}
			}
			else if("3".equals(option)) {
				try {
					exit();
				} catch (Exception e) {
					System.err.println("ERROR: Error during serialization.");
				}
				return;
			}
			else {
				System.out.println("Wrong option! Choose again.");
			}
		}
		boolean end = false;
		while(!end) {
			if(current_user instanceof RegularUser) {
				RegularUser user = (RegularUser)current_user;
				System.out.println("1. Purchase ticket\n2. Browse events\n3. Cancelling purchased tickets\n4. Change password\n5. Logout");
				option = scanner.nextLine();
				switch(option) {
					case "1":
						user.purchaseTicket();
						break;
					case "2":
						user.browseEvents();
						break;
					case "3":
						user.cancelPurchasedTicket();
						break;
					case "4":
						current_user.changePassword();
						break;
					case "5":
						if(logout()) {
							end = true;
							System.out.println("Logout successful!");
						}
						else {
							System.out.println("Logout cancelled!");
						}
						break;
					default:
						System.out.println("Wrong option! Choose again.");
				}
			}
			else if(current_user instanceof Administrator) {
				Administrator admin = (Administrator)current_user;
				System.out.println("1. Accounts management\n2. Cancelling client events\n3. Create Administrator/Client account\n4. Change password\n5. Logout");
				option = scanner.nextLine();
				switch(option) {
					case "1":
						admin.accountManagement();
						break;
					case "2":
						admin.blockClientsEvents();
						break;
					case "3":
						admin.createAdministratorClientAccount();
						break;
					case "4":
						current_user.changePassword();
						break;
					case "5":
						if(logout()) {
							end = true;
							System.out.println("Logout successful!");
						}
						else {
							System.out.println("Logout cancelled!");
						}
						break;
					default:
						System.out.println("Wrong option! Choose again.");
				}
			}
			else {
				Client client = (Client)current_user;
				System.out.println("1. Create event\n2. Cancel event\n3. Browse sold tickets\n4. Change password\n5. Logout");
				option = scanner.nextLine();
				switch(option) {
					case "1":
						client.createEvent();
						break;
					case "2":
						client.cancelEvent();
						break;
					case "3":
						client.browseSoldTickets();
						break;
					case "4":
						current_user.changePassword();
						break;
					case "5":
						if(logout()) {
							end = true;
							System.out.println("Logout successful!");
						}
						else {
							System.out.println("Logout cancelled!");
						}
						break;
					default:
						System.out.println("Wrong option! Choose again.");
				}
			}
		}
		try {
			exit();
		}
		catch(Exception ex) {
			System.err.println("ERROR: Error during serialization !");
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
		String splitter[] = date.split("\\.");
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
