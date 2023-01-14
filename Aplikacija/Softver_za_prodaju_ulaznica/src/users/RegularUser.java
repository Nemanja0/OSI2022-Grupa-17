package users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import events.Event;
import main.Main;
import tickets.Ticket;
import java.util.Random;

public class RegularUser extends User {
	private double credits = 1000;
	private ArrayList<Ticket> purchased_tickets = new ArrayList<>();
	
	public RegularUser(String name, String surname, String username, String password) {
		super(name,surname,username,password);
	}
	
	public void addCredits(double credits) {
		this.credits += credits;
	}
	
	public void takeCredits(double credits) {
		this.credits -= credits;
	}
	
	public void addPurchasedTicket(Ticket t) {
		this.purchased_tickets.add(t);
	}
	
	public void removePurchasedTicket(Ticket t) {
		this.purchased_tickets.remove(t);
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
	
	public void purchaseTicket() {
		System.out.println("~~~ Welcome to ticket purchase ~~~");
		System.out.println("Please enter name of the event which you want to buy ticket for"
							+ ", if you want to cancel type EXIT:");
		Event target = null;
		while(true) {
			String ev = Main.scanner.nextLine();
			if("EXIT".equals(ev)) {
				System.out.println("Ticket purchase cancelled !");
				return;
			}
			for(Event e : Main.events)
				if(e.getName().equals(ev)) {
					target = e;
					break;
				}
			if(target != null)
				break;
			else {
				System.out.println("Invalid input ! Try again:");
			}
		}
		if(target.getNumOfTickets() == 0) {
			System.out.println("There are no tickets left for this event ! Cancelling transaction.");
			return;
		}
		System.out.println("Ticket price is: " + target.getPrice() + "$");
		System.out.println("Do you want to pay with credits or at the event store ? "
				+ "(Type CREDITS for credits, EXIT to cancel)");
		String option = Main.scanner.nextLine();
		boolean elec_purchase = false;
		if("EXIT".equals(option)) {
			System.out.println("Ticket purchase cancelled !");
			return;
		}
		if("CREDITS".equals(option)) {
			elec_purchase = true;
			if(this.credits < target.getPrice()) {
				System.out.println("There are not enough credits on your account for this transaction ! "
									+ "Please top up your balance and try again later.");
				return;
			}
			else {
				this.takeCredits(target.getPrice());
			}
		}
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String date_and_time[] = dateFormat.format(Calendar.getInstance().getTime()).split(" ");
		String ticket_id = "TID" + (new Random().nextInt(10_000_000));
		Ticket ticket = new Ticket(ticket_id, target.getName(), date_and_time[0], date_and_time[1], this.username, target.getPrice(), elec_purchase);
		target.addBoughtTicket(ticket);
		this.purchased_tickets.add(ticket);
		System.out.println("Transaction completed successfully !");
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
				System.out.println("Are you sure? (YES, NO)");
				String yes_no = Main.scanner.nextLine();
				if("YES".equals(yes_no)) {
					this.purchased_tickets.remove(target);
					if(target.isElectronically())
						this.addCredits(target.getPrice());
					target.setCancelled(true);
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
	
	@Override
	public String toString() {
		return super.toString() + " - USER";
	}
}
