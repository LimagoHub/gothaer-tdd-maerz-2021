package de.gothaer.bestelldemo.services;

public interface SolvenzService {
	
	boolean checkSolvenz(/* MASTER oder VISA */ String cardtype, /*10 stellig numerisch*/ String cardnumber, double credit) throws SolvenzServiceException;

}
