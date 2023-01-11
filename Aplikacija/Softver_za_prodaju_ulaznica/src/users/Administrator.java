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
}
