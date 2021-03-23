package de.gothaer.personendemo.services;

import java.util.List;
import java.util.UUID;

import de.gothaer.personendemo.repository.PersonRepository;
import de.gothaer.personendemo.repository.models.Person;

public class PersonServiceImpl {
	
	private final PersonRepository repo;
	private final List<String> antipathen;

	public PersonServiceImpl(PersonRepository repo, final List<String> antipathen) {
		this.repo = repo;
		this.antipathen = antipathen;
	}
	
	
	
	/*
	 * 1. Person darf nicht null sein -> PSE +
	 * 2. Vorname darf nicht null sein und muss min 2. Zeichen -> PSE
	 * 3. Nachname darf nicht null sein und muss min 2. Zeichen -> PSE
	 * 4. Vorname darf nicht Attila sein -> PSE
	 * 5. Underlying Service löst RuntimeException aus -> wandeln nach PSE
	 * 6. Repo übernimmt Person
	 */
	public boolean speichern(Person person) throws PersonServiceException{
		
		try {
			return speichernImpl(person);
		} catch (RuntimeException e) {
			throw new PersonServiceException("Unerwarteter Fehler in der Datenbank.",e);
		}
	}

	
	public boolean speichern(String vorname, String nachname) throws PersonServiceException{
		
		try {
			return speichernImpl(new Person(vorname, nachname));
		} catch (RuntimeException e) {
			throw new PersonServiceException("Unerwarteter Fehler in der Datenbank.",e);
		}
	}



	private boolean speichernImpl(Person person) throws PersonServiceException {
		checkPerson(person);
		person.setId(UUID.randomUUID().toString());
		repo.saveOrUpdate(person);
		return true;
	}



	private void checkPerson(Person person) throws PersonServiceException {
		validate(person);
		businessCheck(person);
	}



	private void businessCheck(Person person) throws PersonServiceException {
		if(antipathen.contains(person.getVorname())) 
			throw new PersonServiceException("Antipath.");
	}



	private void validate(Person person) throws PersonServiceException {
		if(person == null) 
			throw new PersonServiceException("Person darf nicht null sein.");
		
		if(person.getVorname() == null || person.getVorname().length() < 2) 
			throw new PersonServiceException("Vorname muss min. 2 Zeichen enthalten.");
		 
		
		
		if(person.getNachname() == null || person.getNachname().length() < 2) 
			throw new PersonServiceException("Nachname muss min. 2 Zeichen enthalten.");
	}

}
