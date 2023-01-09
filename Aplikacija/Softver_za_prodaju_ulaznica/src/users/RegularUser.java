package users;

public class RegularUser extends User {
	private double credits = 1000;
	
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
}
