package student;

public class Student {
	private String ime, prezime, br_indexa, studijski_program;	
	public Student(String ime, String prezime, String br_indexa, String studijski_program) {
		this.ime = ime;
		this.prezime = prezime;
		this.br_indexa = br_indexa;
		this.studijski_program = studijski_program;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getBrIndexa() {
		return br_indexa;
	}
	public void setBrIndexa(String br_indexa) {
		this.br_indexa = br_indexa;
	}
	public String getStudijskiProgram() {
		return studijski_program;
	}
	public void setStudijskiProgram(String studijski_program) {
		this.studijski_program = studijski_program;
	}
	
}
