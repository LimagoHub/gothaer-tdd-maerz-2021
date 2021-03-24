package de.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import de.gui.presenter.IEuro2DollarPresenter;

public class Euro2DollarRechnerView extends Frame implements IEuro2DollarRechnerView {
	
	
	private static final long serialVersionUID = 8836985830947515511L;
	private Panel centerPanel = null;
	private Panel southPanel = null;
	private TextField txtEuro = null;
	private TextField txtDollar = null;
	private Button btnRechnen = null;
	private Button btnEnde = null;
	
	private IEuro2DollarPresenter presenter;
	
	public Euro2DollarRechnerView() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				presenter.beenden();
			}
		});
		add(getCenterPanel(),BorderLayout.CENTER);
		add(getSouthPanel(),BorderLayout.SOUTH);
		pack();
	}
	
	
	
	public IEuro2DollarPresenter getPresenter() {
		return presenter;
	}



	public void setPresenter(IEuro2DollarPresenter presenter) {
		this.presenter = presenter;
	}



	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#close()
	 */
	@Override
	public void close() {
		this.dispose();
	}
	
	@Override
	public boolean isRechnenEnabled() {
		
		return getBtnRechnen().isEnabled();
	}



	@Override
	public void setRechnenEnabled(boolean enabled) {
		getBtnRechnen().setEnabled(enabled);
		
	}
	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#getEuro()
	 */
	@Override
	public String getEuro() {
		return getTxtEuro().getText();
	}




	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#setEuro(java.lang.String)
	 */
	@Override
	public void setEuro(String euro) {
		getTxtEuro().setText(euro);
	}




	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#getDollar()
	 */
	@Override
	public String getDollar() {
		return getTxtDollar().getText();
	}




	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#setDollar(java.lang.String)
	 */
	@Override
	public void setDollar(String dollar) {
		getTxtDollar().setText(dollar);
	}


	public void setEnabled(boolean value){
		getBtnRechnen().setEnabled(value);
	}

	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel(new GridLayout(2,2));
			centerPanel.add(new Label("Euro:"));
			centerPanel.add(getTxtEuro());
			centerPanel.add(new Label("Dollar:"));
			centerPanel.add(getTxtDollar());
		}
		return centerPanel;
	}
	private Panel getSouthPanel() {
		if(southPanel == null) {
			southPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
			southPanel.add(getBtnRechnen());
			southPanel.add(getBtnEnde());
			
		}
		return southPanel;
	}
	private TextField getTxtEuro() {
		if(txtEuro == null) {
			txtEuro = new TextField(20);
			txtEuro.addTextListener(e->presenter.updateRechnenActionState());
		}
		return txtEuro;
	}
	private TextField getTxtDollar() {
		if(txtDollar == null) {
			txtDollar = new TextField(20);
			txtDollar.setEditable(false);
		}
		return txtDollar;
	}
	private Button getBtnRechnen() {
		if(btnRechnen == null) {
			btnRechnen = new Button("Rechnen");
			btnRechnen.setEnabled(false);
			btnRechnen.addActionListener(e->presenter.rechnen());
		}
		return btnRechnen;
	}
	private Button getBtnEnde() {
		if(btnEnde == null) {
			btnEnde = new Button("Ende");
			btnEnde.addActionListener(e->presenter.beenden());
		}
		return btnEnde;
	}




	
	
}
