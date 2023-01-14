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
		while(!end) {
			System.out.println("1. Suspension\n2. Activate account\n3. Delete account\n4. Password cancellation\n5. Exit");
			String option = Main.scanner.nextLine();
			switch(option) {
				case "1":
					this.suspendUserAccount();
					break;
				case "2":
					this.activateUserAccount();
					break;
				case "3":
					this.deleteUserAccount();
					break;
				case "4":
					this.cancelAccountPassword();
					break;
				case "5":
					end = true;
					System.out.println("Exiting ...");
					break;
				default:
					System.out.println("Invalid input ! Try again.");
			}
		}
	}
	
	public void blockClientsEvents() {
		if(Main.events.isEmpty()) {
			System.out.println("Currently there are no events available.");
			return;
		}
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
	
	public void cancelAccountPassword() {
		while(true) {
			String option = null;
			User target = null;
			System.out.println("Enter username of desired account to cancel its password, or EXIT to quit:");
			option = Main.scanner.nextLine();
			if("EXIT".equals(option)) {
				System.out.println("Password cancellation cancelled.");
				return;
			}
			else {
				for(User u : Main.users)
					if(u.getUsername().equals(option)) {
						target = u;
						break;
					}
				if(target == null)
					System.out.println("Entered username doesen't exist. Try again.");
				else {
					System.out.println("Are you sure you want to cancel this users password ? (YES, NO)");
					String yes_no = Main.scanner.nextLine();
					if("YES".equals(yes_no)) {
						target.setPasswordCancelled(true);
						System.out.println("Password cancellation successfull.");
						break;
					}
					else {
						System.out.println("Password cancellation cancelled.");
						return;
					}
				}
			}
		}
	}
	
	public void deleteUserAccount() {
		while(true) {
			String option = null;
			User target = null;
			System.out.println("Enter username of desired account to delete, or EXIT to quit:");
			option = Main.scanner.nextLine();
			if("EXIT".equals(option)) {
				System.out.println("Account deletion cancelled.");
				return;
			}
			else {
				for(User u : Main.users)
					if(u.getUsername().equals(option)) {
						target = u;
						break;
					}
				if(target == null)
					System.out.println("Entered username doesen't exist. Try again.");
				else {
					if(target instanceof Administrator) {
						System.out.println("Unable to delete an administrator account. Try again.");
					}
					else {
						System.out.println("Are you sure you want to delete this users account ? (YES, NO)");
						String yes_no = Main.scanner.nextLine();
						if("YES".equals(yes_no)) {
							Main.users.remove(target);
							if(target instanceof Client) {
								//If the targeted user is Client -> remove all of his events 
								for(Event ev : Main.events)
									if(ev.getEventCreator().equals(target)) {
										ev.deleteAllTickets();
										Main.events.remove(ev);
									}
							}
							System.out.println("Account deletion successfull.");
							break;
						}
						else {
							System.out.println("Account deletion cancelled.");
							return;
						}
					}
				}
			}
		}
	}
	
	public void suspendUserAccount() {
		System.out.println("~~~ ACTIVE USERS ~~~\n");
		Main.users.stream().forEach(System.out::println);
		while(true) {
			String option = null;
			User target = null;
			System.out.println("\nEnter username of desired account to suspend, or EXIT to quit:");
			option = Main.scanner.nextLine();
			if("EXIT".equals(option)) {
				System.out.println("Account suspension cancelled.");
				return;
			}
			else {
				if(Main.suspended_usernames.contains(option)) {
					System.out.println("This user has already been suspended. Try again.");
				}
				else {
					for(User u : Main.users)
						if(u.getUsername().equals(option)) {
							target = u;
							break;
						}
					if(target == null)
						System.out.println("Entered username doesen't exist. Try again.");
					else {
						if(target instanceof Administrator) {
							System.out.println("Unable to block an administrator account. Try again.");
						}
						else {
							System.out.println("Are you sure you want to suspend this account ? (YES, NO)");
							String yes_no = Main.scanner.nextLine();
							if("YES".equals(yes_no)) {
								System.out.println("Please enter the reason for suspension:");
								String reason = Main.scanner.nextLine();
								Main.suspend_reasons.add(reason);
								Main.suspended_usernames.add(target.getUsername());
								System.out.println("Account suspension successfull.");
								break;
							}
							else {
								System.out.println("Account suspension cancelled.");
								return;
							}
						}
					}
				}
			}
		}
	}
	
	public void activateUserAccount() {
		if(Main.suspended_usernames.isEmpty()) {
			System.out.println("There are no suspended users.");
			return;
		}
		System.out.println("~~~ SUSPENDED ACCOUNTS ~~~\n");
		for(int i=0;i<Main.suspend_reasons.size();i++)
			System.out.println(Main.suspended_usernames.get(i) + " - " + Main.suspend_reasons.get(i));
		while(true) {
			String option = null;
			System.out.println("Enter username of desired account to activate, or EXIT to quit:");
			option = Main.scanner.nextLine();
			if("EXIT".equals(option)) {
				System.out.println("Account activation cancelled.");
				return;
			}
			else {
				if(!Main.suspended_usernames.contains(option))
					System.out.println("Entered username is not in the suspended list. Try again.");
				else {
					System.out.println("Are you sure you want to activate this account ? (YES, NO)");
					String yes_no = Main.scanner.nextLine();
					if("YES".equals(yes_no)) {
						int index = Main.suspended_usernames.indexOf(option);
						Main.suspend_reasons.remove(index);
						Main.suspended_usernames.remove(index);
						System.out.println("Account activation successfull.");
						break;
					}
					else {
						System.out.println("Account activation cancelled.");
						return;
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + " - ADMIN";
	}
}
