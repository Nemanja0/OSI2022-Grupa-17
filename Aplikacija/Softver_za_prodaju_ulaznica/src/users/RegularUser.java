package users;

public class RegularUser extends User {
	private int credits = 1000;
	
	public RegularUser(String name, String surname, String username, String password) {
		super(name,surname,username,password);
	}
	
	public void addCredits(int credits) {
		this.credits += credits;
	}
	
	public boolean takeCredits(int credits) {
		if(this.credits < credits) {
			return false;
		}
		else {
			this.credits -= credits;
			return true;
		}
	}
}
