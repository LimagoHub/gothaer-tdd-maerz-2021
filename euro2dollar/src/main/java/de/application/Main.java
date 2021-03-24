package de.application;

import de.gui.Euro2DollarRechnerView;
import de.gui.IEuro2DollarRechnerView;
import de.gui.presenter.Euro2DollarPresenter;
import de.gui.presenter.IEuro2DollarPresenter;
import de.model.Euro2DollarRechnerImpl;
import de.model.IEuro2DollarRechner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IEuro2DollarRechner model = new Euro2DollarRechnerImpl();
		
		IEuro2DollarPresenter presenter = new Euro2DollarPresenter();
		
		IEuro2DollarRechnerView view = new Euro2DollarRechnerView();
		
		
		presenter.setView(view);
		presenter.setModel(model);
		
		view.setPresenter(presenter);
		
		presenter.populateItems();
		
		view.show();

	}

}
