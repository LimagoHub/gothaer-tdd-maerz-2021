package de.gothaer.calculator;

import de.gothaer.calculator.client.CalcClient;
import de.gothaer.calculator.math.Calculator;
import de.gothaer.calculator.math.CalculatorImpl;

public class Application {

	public static void main(String[] args) {
		Calculator calculator = new CalculatorImpl();
		CalcClient client = new CalcClient(calculator);
		client.run();
	}

}
