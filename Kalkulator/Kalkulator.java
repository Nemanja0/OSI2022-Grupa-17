public class Kalkulator{
	public static void main(String[] args){
		System.out.println("5 + 5 = " + Sabiranje.saberi(5,5));
		System.out.println("10 - 5 = ", Oduzimanje.oduzmi(10,5));
		System.out.println("5 * 5 = ", Mnozenje<Integer>.pomnozi(5,5));
		Dijeljenje d = new Dijeljenje(10,5);
		System.out.println("Dijeljenje: ");
		d.printRez();
	}
}