package de.gothaer.calculator.client;

import de.gothaer.calculator.math.Calculator;


public class CalcClient {
	
	private final Calculator calculator;
	
	

	public CalcClient(final Calculator calculator) {
		this.calculator = calculator;
	}



	public void run() {
		final double komplizierteFormel1 = 3;
		final double komplizierteFormel2 = 4;
		System.out.println(calculator.add(komplizierteFormel1,komplizierteFormel2));
		//System.out.println(calculator.add(komplizierteFormel1+1,komplizierteFormel2));
	}

}
