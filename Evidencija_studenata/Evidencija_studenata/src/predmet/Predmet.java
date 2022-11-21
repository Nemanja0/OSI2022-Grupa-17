package predmet;


public class Predmet {
	
	private String ime;
	private int ECTS, sifra;
	
	public Predmet(String ime, int ECTS, int sifra){
		this.ime=ime;
		this.ECTS=ECTS;
		this.sifra=sifra;
	}
	@Override
	public String toString() {
		return "Predmet: "+ime+"\nBroj ECTS bodova: "+ ECTS+ "\nSifra predmeta: "+sifra; 
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public int getECTS() {
		return ECTS;
	}
	public void setECTS(int eCTS) {
		ECTS = eCTS;
	}
	public int getSifra() {
		return sifra;
	}
	public void setSifra(int sifra) {
		this.sifra = sifra;
	}
	
}

