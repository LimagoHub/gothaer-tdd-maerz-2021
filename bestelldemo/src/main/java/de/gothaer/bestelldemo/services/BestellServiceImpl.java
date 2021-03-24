package de.gothaer.bestelldemo.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.gothaer.bestelldemo.repositories.BestellRepository;
import de.gothaer.bestelldemo.repositories.model.Bestellung;

public class BestellServiceImpl {
	
	private final SolvenzService solvenzService;
	private final BestellRepository repo;
	private final Pattern pattern = Pattern.compile("^(M|V)(\\d{10})$");
	
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
		
		try {
			if(bestellung == null) throw new BestellServiceException("Bestellung darf nicht null sein");
			if(creditcard == null) throw new BestellServiceException("Kreditkarte darf nicht null sein.");
			if(saldo <= 0.0) throw new BestellServiceException("Saldo darf nicht negativ sein.");
			
			
			Matcher matcher = pattern.matcher(creditcard);
			if(! matcher.matches()) throw new BestellServiceException("Falsches Kartenformat.");
			
					
			String type = matcher.group(1).equals("M") ? "MASTER" : "VISA";
			String number = matcher.group(2);
			
			if (! solvenzService.checkSolvenz(type, number, saldo)) 
				throw new KundeIstPleiteException("Kunde ist nicht solvent.");
			
			repo.save(bestellung);
			
		} catch (SolvenzServiceException e) {
			throw new BestellServiceException("Solvenzservice antwortet nicht.",e);
			
		} catch (RuntimeException e) {
			throw new BestellServiceException("Fehler im Service.",e);
		}
		
			

	}
}
