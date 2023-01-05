package events;

import java.io.Serializable;

public class Event implements Serializable {
	
	private String name, time, date, client_name;
	private double price;
	public int ticket_number;
	private boolean on_name;
	
	public Event(String name, String time, String date, double price, boolean on_name, int ticket_number, String client_name) {
		this.name = name;
		this.time = time;
		this.date = date;
		this.price = price;
		this.on_name = on_name;
		this.ticket_number = ticket_number;
		this.client_name = client_name;
	}

	public String getClientName() {
		return client_name;
	}

	public void setClientName(String client_name) {
		this.client_name = client_name;
	}

	public int getTicketNumber() {
		return ticket_number;
	}

	public void setTicketNumber(int ticket_number) {
		this.ticket_number = ticket_number;
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
	
	
}
