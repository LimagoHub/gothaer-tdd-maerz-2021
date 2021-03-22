package de.gothaer.calculator.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.annotation.Annotation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import static org.mockito.Mockito.*;

import de.gothaer.calculator.math.Calculator;

public class CalcClientTest {

	private CalcClient objectUnderTest;
	private Calculator calculatorMock;
	
	@BeforeEach
	public void setup() {
		calculatorMock = mock(Calculator.class);
		
		objectUnderTest = new CalcClient(calculatorMock);
	}
	
	@Test
	void run_blabla() {
		
		// Arrange
		// Mock im Recordmode
//		when(calculatorMock.add(3.0, 5.0)).thenReturn(4711.0);
//		when(calculatorMock.add(3.0, 4.0)).thenReturn(42.0);
//		when(calculatorMock.add(anyDouble(), anyDouble())).thenReturn(42.0);
		
		// Act
		objectUnderTest.run();
		
		
		// Assert
		verify(calculatorMock).add(3.0, 4.0);
	}

	
}
