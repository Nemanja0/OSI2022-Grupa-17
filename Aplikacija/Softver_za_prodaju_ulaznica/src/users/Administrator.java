package users;

import main.Main;

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
}
