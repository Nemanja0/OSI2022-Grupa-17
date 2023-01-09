package users;

import main.Main;
import events.Event;
import java.util.Calendar;

public class Client extends User {

	public Client(String name, String surname, String username, String password) {
		super(name,surname,username,password);
	}
	
	public void cancelEvent() {
		String event_name;
		Event event;
		System.out.println("Welcome to the event cancellation. Please give the following information or cancel event cancellation by typing EXIT.\n");
		System.out.println("Type a name of event that you want to cancel:");
		while(true) {
			event_name = Main.scanner.nextLine();
			if("EXIT".equals(event_name)) {
				System.out.println("Event cancellation cancelled!");
				return;
			}
			event = Main.checkEvent(event_name);
			if(event != null)
				break;
			System.out.println("Event does not exist!");
		}
		event.deleteAllTickets();
		Main.events.remove(event);
		System.out.println("Event successfuly cancelled!");
	}
	
	public void createEvent() {
		String tmp, name, time, date, description;
		double price;
		int num_of_tickets;
		boolean on_name;
		System.out.println("Welcome to the event creator. Please give the following information or cancel event creation by typing EXIT.\n");
		System.out.println("Enter name of the event:");
		name = Main.scanner.nextLine();
		if("EXIT".equals(name)) {
			System.out.println("Event creation cancelled !");
			return;
		}
		System.out.println("Enter date of the event in format dd.mm.yyyy:");
		while(true) {
			date = Main.scanner.nextLine();
			if("EXIT".equals(date)) {
				System.out.println("Event creation cancelled !");
				return;
			}
			if(this.checkDateValidity(date))
				break;
			else
				System.out.println("Invalid input. Try again.");
		}
		System.out.println("Enter time of the event in format hh:mm :");
		while(true) {
			time = Main.scanner.nextLine();
			if("EXIT".equals(time)) {
				System.out.println("Event creation cancelled !");
				return;
			}
			if(this.checkTimeValidity(time))
				break;
			else
				System.out.println("Invalid input. Try again.");
		}
		System.out.println("Enter description of the event:");
		description = Main.scanner.nextLine();
		if("EXIT".equals(description)) {
			System.out.println("Event creation cancelled !");
			return;
		}
		System.out.println("Enter number of tickets available for the event:");
		while(true) {
			try {
				tmp = Main.scanner.nextLine();
				if("EXIT".equals(tmp)) {
					System.out.println("Event creation cancelled !");
					return;
				}
				num_of_tickets = Integer.parseInt(tmp);
				if(num_of_tickets < 0) {
					throw new Exception();
				}
				break;
			}
			catch(Exception ex) {
				System.out.println("Invalid input. Try again.");
			}
		}
		System.out.println("Enter price of the ticket for this event:");
		while(true) {
			try {
				tmp = Main.scanner.nextLine();
				if("EXIT".equals(tmp)) {
					System.out.println("Event creation cancelled !");
					return;
				}
				price = Double.parseDouble(tmp);
				if(price < 0) {
					throw new Exception();
				}
				break;
			}
			catch(Exception ex) {
				System.out.println("Invalid input. Try again  ");
			}
		}
		System.out.println("Are tickets being bought on name ? (Type YES if they are):");
		tmp = Main.scanner.nextLine();
		on_name = "YES".equals(tmp);
		Main.events.add(new Event(name, time, date, description, price, on_name, num_of_tickets, this));
		System.out.println("Successfully created new event !");
		return;
	}
	
	private boolean checkTimeValidity(String time) {
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
	
	private boolean checkDateValidity(String date) {
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
			int allowed_day = this.getAllowedDay(month);
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
	
	private int getAllowedDay(int month) {
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
