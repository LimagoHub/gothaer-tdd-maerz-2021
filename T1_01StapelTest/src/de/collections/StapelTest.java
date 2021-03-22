package de.collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StapelTest {
	
	private Stapel objectUnderTest;
	
	
	@BeforeEach
	public void setup() {
		objectUnderTest = new Stapel();
	}

	@Test
	public void isEmpty_emptyStack_returnsTrue() {
		
		// Arrange
		
		
		// Action
		boolean ergebnis = objectUnderTest.isEmpty();
		
		// Assertion
		assertTrue(ergebnis);
		
		
	}

	@Test
	public void isEmpty_notEmptyStack_returnsFalse() throws Exception{
		
		// Arrange
	
		objectUnderTest.push(new Object());
		
		// Action
		boolean ergebnis = objectUnderTest.isEmpty();
		
		// Assertion
		assertFalse(ergebnis);
		
		
	}

	@Test
	public void isEmpty_EmptyAgainStack_returnsTrue() throws Exception{
		
		// Arrange
	
		objectUnderTest.push(new Object());
		objectUnderTest.pop();
		
		// Action
		boolean ergebnis = objectUnderTest.isEmpty();
		
		// Assertion
		assertTrue(ergebnis);
		
		
	}
	
	@Test
	public void push_happyDay_popReturnsInsertedValue() throws Exception{
		final Object object = new Object();
		
		// Action
		objectUnderTest.push(object);
		
		
		// Assertion
		assertEquals(object, objectUnderTest.pop());
		
	}
	
	@Test
	public void push_UpperLimit_NoExceptionIsThrown() throws Exception{
		final Object object = new Object();
		
		for (int i = 0; i < 9; i++) {
			objectUnderTest.push(object);
			 
		}
		
		assertDoesNotThrow(()->objectUnderTest.push(object));
		
		
		
	}

	@Test
	public void push_Overflow_throwsStapelException() throws Exception{
		final Object object = new Object();
		
		for (int i = 0; i < 10; i++) {
			objectUnderTest.push(object);
			 
		}
		
		StapelException ex = assertThrows(StapelException.class, ()->objectUnderTest.push(object) );
		
		assertEquals("Overflow", ex.getMessage());
		
	}

}
