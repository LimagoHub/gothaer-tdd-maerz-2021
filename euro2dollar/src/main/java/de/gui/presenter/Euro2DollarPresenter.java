package de.gui.presenter;

import de.gui.IEuro2DollarRechnerView;
import de.model.IEuro2DollarRechner;

public class Euro2DollarPresenter implements IEuro2DollarPresenter {
	
	private IEuro2DollarRechnerView view;
	private IEuro2DollarRechner model;
	
	
	
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#getView()
	 */
	@Override
	public IEuro2DollarRechnerView getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#setView(de.gui.IEuro2DollarRechnerView)
	 */
	@Override
	public void setView(IEuro2DollarRechnerView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#getModel()
	 */
	@Override
	public IEuro2DollarRechner getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#setModel(de.model.IEuro2DollarRechner)
	 */
	@Override
	public void setModel(IEuro2DollarRechner model) {
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#rechnen()
	 */
	@Override
	public void rechnen() {
		// Eurowert aus der Maske holen
		// Eurowert wenn möglich in double wandeln
		// Wenn nicht möglich Fehlermeldung in dollarfeld schreiben
		// Eurowert an Model übergeben
		// Ergebnis in String wandeln
		// ergebnis in Maske schreiben
		// Fehler immer in Dollarfeld schreiben
	
	}
	
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#beenden()
	 */
	@Override
	public void beenden() {  
		view.close();
	}
	
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#populateItems()
	 */
	@Override
	public void populateItems() {
		// "0" in Dollar und Eurofeld schreiben
		// Rechnen aktivieren
	}

	@Override
	public void updateRechnenActionState() {
		
		// Noch nicht
	}

}
