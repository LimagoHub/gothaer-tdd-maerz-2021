package de.gothaer.personendemo.repository;

import java.util.List;
import java.util.Optional;

import de.gothaer.personendemo.repository.models.Person;

public interface PersonRepository {
	
	void saveOrUpdate(Person person);
	void remove(Person person);
	Optional<Person> findById(String id);
	List<Person> findAll();
	boolean existsById(String id);
}
