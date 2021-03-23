package de.gothaer.bestelldemo.services;

import de.gothaer.bestelldemo.repositories.BestellRepository;
import de.gothaer.bestelldemo.repositories.model.Bestellung;

public class BestellServiceImpl {
	
	private final SolvenzService solvenzService;
	private final BestellRepository repo;
	
	
	public BestellServiceImpl(final SolvenzService solvenzService, final BestellRepository repo) {
		this.solvenzService = solvenzService;
		this.repo = repo;
	}
	
	

	/*
	 * 1. Bestellung darf nicht null sein -> BSE
	 * 2. Creditcard darf nicht null sein -> BSE
	 * 3. Fachliche prüfung der Credikartennummer -> BSE
	 * 4. saldo darf nicht negativ -> BSE
	 * 5. Solvenzprüfung scheitert -> KIPE
	 * 6. Problem beim einem abhängigen Service -> BSE
	 * 7. SolvenzPrüfung muss vor dem speichern erfolgen.
	 * 8. Jeder Service darf max 1 mal berührt werden
	 */
	public void erfasseBestellung(Bestellung bestellung, /* M oder V danach genau 10 Ziffern */ String creditcard, double saldo) throws BestellServiceException, KundeIstPleiteException {
		
	}
}
