package users;

import java.io.Serializable;
import events.Event;

import main.Main;

public abstract class User implements Serializable {
	protected String name; 
	protected String surname;
	protected String username;
	protected String password;
	protected int number_of_login;
	
	public User(String name, String surname, String username, String password) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.number_of_login = 0;
	}
	
	public void changePassword() {
		while(true) {
			String pass;
			System.out.println("Please enter your current password:");
			pass = Main.scanner.nextLine();
			if(this.password.equals(pass) == false) {
				System.out.println("Wrong password! Try again.");
			}
			else {
				System.out.println("Please enter new password:");
				pass = Main.scanner.nextLine();
				if(pass.length() >= 8) {
					System.out.println("Password successfully changed!");
					this.password = pass;
					break;
				}
				else{
					System.out.println("Password is too short! Must be 8 characters long or longer! Try again!");
				}
			}
		}
	}
	
	public int getNumberOfLogin() {
		return number_of_login;
	}

	public void setNumberOfLogin(int number_of_login) {
		this.number_of_login = number_of_login;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof User) {
			if(((User)obj).username.equals(this.username))
				return true;
		}
		return false;
	}
	
}
