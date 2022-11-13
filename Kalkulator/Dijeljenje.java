class Dijeljenje{
	private double rezultat, a, b;
	public Dijeljenje(a, b){
		this.a = a;
		this.b = b;
		if(this.b != 0)
			this.rez = a/b;
		else
			this.rez = 0;
	}
	public void printRez(){
		System.out.println("Rezultat je: " + this.rez);
	}
}