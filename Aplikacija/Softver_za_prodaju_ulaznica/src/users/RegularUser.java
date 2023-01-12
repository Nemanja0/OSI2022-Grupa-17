package users;

import java.util.ArrayList;

import events.Event;
import main.Main;
import tickets.Ticket;

public class RegularUser extends User {
	private double credits = 1000;
	private ArrayList<Ticket> purchased_tickets = new ArrayList<>();
	
	public RegularUser(String name, String surname, String username, String password) {
		super(name,surname,username,password);
	}
	
	public void addCredits(double credits) {
		this.credits += credits;
	}
	
	public boolean takeCredits(double credits) {
		if(this.credits < credits) {
			return false;
		}
		else {
			this.credits -= credits;
			return true;
		}
	}
	
	public void browseEvents() {
		System.out.println("~~~ Upcoming events ~~~\n");
		Main.events.stream().forEach(System.out::println);
		boolean end = false;
		while(!end) {
			String option = null;
			System.out.println("\nIf you want detailed info for any event type INFO");
			System.out.println("If you want description for any event type DESC");
			System.out.println("If you want to exit type EXIT");
			option = Main.scanner.nextLine();
			boolean info = "INFO".equals(option);
			boolean desc = "DESC".equals(option);
			if(info || desc) {
				Event target = null;
				System.out.println("Enter name of the desired event:");
				option = Main.scanner.nextLine();
				for(Event ev : Main.events) {
					if(ev.getName().equals(option)) {
						target = ev;
						break;
					}
				}
				if(target == null)
					System.out.println("Invalid input ! Try again.");
				else {
					if(info)
						target.printEventInfo();
					else
						System.out.println(target.getDescription());
				}
			}		
			else if("EXIT".equals(option))
				end = true;
			else 
				System.out.println("Invalid input ! Try again.");
		}
	}
	
	public void cancelPurchasedTicket() {
		System.out.println("~~~ Purchased tickets ~~~\n");
		this.purchased_tickets.stream().forEach(System.out::println);
		while(true) {
			Ticket target = null;
			System.out.println("Type ID of ticket that you want to cancel or type EXIT if you want to exit:");
			String option = Main.scanner.nextLine();
			if("EXIT".equals(option))
				break;
			for(Ticket ti : this.purchased_tickets) {
				if(ti.getId().equals(option)) {
					target = ti;
					break;
				}
			}
			if(target == null)
				System.out.println("Invalid input ! Try again.");
			else {
				System.out.println("Are you sure?");
				String yes_no = Main.scanner.nextLine();
				if("YES".equals(yes_no)) {
					this.purchased_tickets.remove(target);
					target.setCancelled(true);
					if(target.isElectronically())
						this.addCredits(target.getPrice());
					System.out.println("Ticket successfuly cancelled!");
					break;
				}
				else {
					System.out.println("The ticket has not been cancelled!");
					break;
				}
			}	
		}
	}
}
