package de.model;

public class Euro2DollarRechnerImpl implements IEuro2DollarRechner {
	
	
	/* (non-Javadoc)
	 * @see de.model.IEuro2DollarRechner#calculateEuro2Dollar(de.model.Euro2DollarForm)
	 */
	@Override
	public double calculateEuro2Dollar(double euro) {
		// zukünftig potentiell RuntimeException möglich
		return euro * 1.1;
	}

}
