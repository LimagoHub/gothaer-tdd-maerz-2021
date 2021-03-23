package de.gothaer.personendemo.repository.models;

public class Person {
	
	private String id;
	private String vorname;
	private String nachname;
	
	public Person() {
		this("John","Doe");
	}
	public Person(String vorname, String nachname) {
		this.id = null;
		this.vorname = vorname;
		this.nachname = nachname;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [id=");
		builder.append(id);
		builder.append(", vorname=");
		builder.append(vorname);
		builder.append(", nachname=");
		builder.append(nachname);
		builder.append("]");
		return builder.toString();
	}

}
