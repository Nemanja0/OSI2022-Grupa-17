package users;

import events.Event;
import main.Main;
import tickets.Ticket;

public class Administrator extends User {
	
	public Administrator(String name, String surname, String username, String password) {
		super(name,surname,username,password);
	}
	
	public void createAdministratorClientAccount() {
		if(Main.register())
			System.out.println("Account successfuly created!");
		else
			System.out.println("Account has not been created!");
	}
	
	public void accountManagement() {
		boolean end = false;
		System.out.println("Choose one of the following options.");
		System.out.println("1. Suspension\n2. Activate account\n3. Delete account\n4. Password cancellation\n5. Exit");
		while(!end) {
			String option = Main.scanner.nextLine();
			switch(option) {
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				case "5":
					end = true;
					System.out.println("Exiting ...");
					break;
				default:
					System.out.println("Invalid input ! Try again.");
					break;
			}
		}
	}
	
	public void blockClientsEvents() {
		System.out.println("~~~ Events ~~~\n");
		Main.events.stream().forEach(System.out::println);
		while(true) {
			Event target = null;
			System.out.println("Type name of event that you want to block or type EXIT if you want to exit:");
			String option = Main.scanner.nextLine();
			if("EXIT".equals(option)) {
				System.out.println("Exiting ...");
				break;
			}
			for(Event ev : Main.events) {
				if(ev.getName().equals(option)) {
					target = ev;
					break;
				}
			}
			if(target == null) {
				System.out.println("Invalid input ! Try again.");
			}
			else {
				System.out.println("Are you sure? (YES, NO)");
				String yes_no = Main.scanner.nextLine();
				if("YES".equals(yes_no)) {
					target.deleteAllTickets();
					Main.events.remove(target);
					System.out.println("Event successfuly blocked!");
					break;
				}
				else {
					System.out.println("The event has not been blocked!");
					break;
				}
			}
		}
	}
}
