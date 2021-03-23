package de.gothaer.personendemo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.gothaer.personendemo.repository.PersonRepository;
import de.gothaer.personendemo.repository.models.Person;


@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

	@Mock private PersonRepository repoMock;
	@InjectMocks private PersonServiceImpl objectUnderTest;
	
	@Test
	void speichern_PersonParameterIsNull_throwsPersonenServiceException() throws Exception{
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(null));
		assertEquals("Person darf nicht null sein.", ex.getMessage());
	}

	@Test
	void speichern_PersonVornameIsNull_throwsPersonenServiceException() throws Exception{
		final Person person = new Person(null, "Doe");
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Vorname muss min. 2 Zeichen enthalten.", ex.getMessage());
	}

}
