package indeks;

import java.util.HashMap;
import predmet.Predmet;

public class Indeks {
	private HashMap<Predmet,Integer> indeks = new HashMap<>();
	
	public void dodajOcjenu(Predmet predmet, Integer ocjena) {
		indeks.put(predmet, ocjena);
	}
	
	public void ispisiOcjene() {
		indeks.forEach((key,value)->{System.out.println(key + " - Ocjena: " + value);});
	}
}
