package tickets;

import java.io.Serializable;

public class Ticket implements Serializable {
	private String id, event_name, date, time, user_name;
	private double price;
	private boolean cancelled, electronically;
	
	public Ticket(String id, String event_name, String date, String time, String user_name, double price, boolean electronically){
		this.id = id;
		this.event_name = event_name;
		this.date = date;
		this.time = time;
		this.user_name = user_name;
		this.price = price;
		this.electronically = electronically;
	}

	public boolean isElectronically() {
		return electronically;
	}

	public void setElectronically(boolean electronically) {
		this.electronically = electronically;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventName() {
		return event_name;
	}

	public void setEventName(String event_name) {
		this.event_name = event_name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserName() {
		return user_name;
	}

	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		String res = "========================== TICKET ======================\n";
		res += "Event name: " + this.event_name + "\n";
		res += "Ticket ID: " + this.id + "\n";
		res += "Date and time: " + this.date + " " + this.time + "\n";
		res += "Price: " + this.price + "$\n";
		res += "Bought by user: " + this.user_name + "\n";
		if(this.cancelled) {
			res += "------------ CANCELLED ------------";
		}
		res += "========================================================";
		return res;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Ticket) {
			return this.id.equals(((Ticket)o).id);
		}
		return false;
	}
}
