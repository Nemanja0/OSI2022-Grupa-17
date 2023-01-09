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
			if(Main.checkDateValidity(date))
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
			if(Main.checkTimeValidity(time))
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
	
	public void browseSoldTickets() {
		Event target = null;
		for(Event e : Main.events)
			if(e.getEventCreator().equals(this)) {
				target = e;
				break;
			}
		if(target != null) {
			target.getBoughtTickets().stream().filter(x -> !x.isCancelled()).forEach(System.out::println);
		}
		boolean end = false;
		while(!end) {
			String option;
			System.out.println("\nIf you want to search for sellings for certain date type: SEARCH");
			System.out.println("If you want to search for cancelled tickets type: CANCEL");
			System.out.println("To exit type: EXIT");
			option = Main.scanner.nextLine();
			switch(option) {
				case "SEARCH":
					System.out.println("Enter the desired date:");
					String date = Main.scanner.nextLine();
					if(Main.checkDateValidity(date)) {
						target.getBoughtTickets().stream().filter(x -> x.getDate().equals(date)).forEach(System.out::println);
					}
					else
						System.out.println("Invalid input ! Try again.");
					break;
				case "CANCEL":
					target.getBoughtTickets().stream().filter(x -> x.isCancelled()).forEach(System.out::println);
					break;
				case "EXIT":
					end = true;
					break;
				default:
					System.out.println("Invalid input ! Try again.");
			}
		}
		
	}
	
}
