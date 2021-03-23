package de.gothaer.personendemo.services;

import de.gothaer.personendemo.repository.PersonRepository;
import de.gothaer.personendemo.repository.models.Person;

public class PersonServiceImpl {
	
	private final PersonRepository repo;

	public PersonServiceImpl(PersonRepository repo) {
		this.repo = repo;
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
		
		if(person == null) 
			throw new PersonServiceException("Person darf nicht null sein.");
		
		if(person.getVorname() == null) 
			throw new PersonServiceException("Vorname muss min. 2 Zeichen enthalten.");
		
		
		return true;
	}

}
