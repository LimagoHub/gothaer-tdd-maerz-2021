package de.gui.presenter;

import de.gui.IEuro2DollarRechnerView;
import de.model.IEuro2DollarRechner;

public interface IEuro2DollarPresenter {

	public abstract IEuro2DollarRechnerView getView();

	public abstract void setView(IEuro2DollarRechnerView view);

	public abstract IEuro2DollarRechner getModel();

	public abstract void setModel(IEuro2DollarRechner model);

	public abstract void rechnen();

	public abstract void beenden();

	public abstract void populateItems();
	
	public abstract void updateRechnenActionState(); // Nicht beachten

}