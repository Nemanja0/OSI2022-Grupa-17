package events;

import java.io.Serializable;
import users.Client;
import java.util.ArrayList;
import tickets.Ticket;

public class Event implements Serializable {
	
	private Client event_creator;
	private String name, time, date;
	private String description;
	private double price;
	private int num_of_tickets;
	private boolean on_name;
	private ArrayList<Ticket> bought_tickets = new ArrayList<>();
	
	public Event(String name, String time, String date, String description, double price, boolean on_name, int num_of_tickets, Client event_creator) {
		this.name = name;
		this.time = time;
		this.date = date;
		this.description = description;
		this.price = price;
		this.on_name = on_name;
		this.num_of_tickets = num_of_tickets;
		this.event_creator = event_creator;
	}

	public Client getEventCreator() {
		return event_creator;
	}

	public void setEventCreator(Client event_creator) {
		this.event_creator = event_creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isOnName() {
		return on_name;
	}

	public void setOnName(boolean on_name) {
		this.on_name = on_name;
	}

	public int getNumOfTickets() {
		return num_of_tickets;
	}

	public void setNumOfTickets(int num_of_tickets) {
		this.num_of_tickets = num_of_tickets;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
