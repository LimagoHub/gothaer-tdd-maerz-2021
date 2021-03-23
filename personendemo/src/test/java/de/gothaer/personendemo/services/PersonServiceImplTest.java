package de.gothaer.personendemo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.gothaer.personendemo.repository.PersonRepository;
import de.gothaer.personendemo.repository.models.Person;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

	@Mock private PersonRepository repoMock;
	@Mock private List<String> antipathenMock;
	@InjectMocks private PersonServiceImpl objectUnderTest;
	
	
	private final Person validPerson = new Person("John", "Doe");
	
	
	@Captor
	private ArgumentCaptor<Person> personCaptor;
	
	
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
	@Test
	void speichern_PersonVornameIsTooShort_throwsPersonenServiceException() throws Exception{
		final Person person = new Person("J", "Doe");
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Vorname muss min. 2 Zeichen enthalten.", ex.getMessage());
	}

	@Test
	void speichern_PersonNachnameIsNull_throwsPersonenServiceException() throws Exception{
		final Person person = new Person("John", null);
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Nachname muss min. 2 Zeichen enthalten.", ex.getMessage());
	}
	@Test
	void speichern_PersonNachnameIsTooShort_throwsPersonenServiceException() throws Exception{
		final Person person = new Person("John", "D");
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Nachname muss min. 2 Zeichen enthalten.", ex.getMessage());
	}

	@Test
	void speichern_Antipath_throwsPersonenServiceException() throws Exception{
		
		when(antipathenMock.contains(anyString())).thenReturn(true);
		
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(validPerson));
		assertEquals("Antipath.", ex.getMessage());
	}

	@Test
	void speichern_UnexpectedRuntimeExceptionInUnderlyingService_throwsPersonenServiceException() throws Exception{
		when(antipathenMock.contains(anyString())).thenReturn(false);
		doThrow(new ArrayIndexOutOfBoundsException()).when(repoMock).saveOrUpdate(any(Person.class));
		
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(validPerson));
		assertEquals("Unerwarteter Fehler in der Datenbank.", ex.getMessage());
	}

	@Test
	void speichern_HappyDay_PersonIsPassedToRepo() throws Exception{
		when(antipathenMock.contains(anyString())).thenReturn(false);
		//doThrow(new ArrayIndexOutOfBoundsException()).when(repoMock).saveOrUpdate(any(Person.class));
		doNothing().when(repoMock).saveOrUpdate(any(Person.class));
		
		objectUnderTest.speichern(validPerson);
		
		verify(repoMock).saveOrUpdate(validPerson);
	}

	@Test
	void speichern_HappyDayOverload_PersonIsPassedToRepo() throws Exception{
		when(antipathenMock.contains(anyString())).thenReturn(false);
		
		
		objectUnderTest.speichern("Max","Mustermann");
		
		verify(repoMock).saveOrUpdate(personCaptor.capture());
		assertNotNull( personCaptor.getValue().getId());
		assertEquals(36,  personCaptor.getValue().getId().length());
		assertEquals("Max", personCaptor.getValue().getVorname());
		assertEquals("Mustermann", personCaptor.getValue().getNachname());
	}

}
