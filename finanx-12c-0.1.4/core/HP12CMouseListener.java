package core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import components.ImageButtonAbstraction;

public class HP12CMouseListener extends MouseAdapter {

	private HP12CController presenter;
	private ImageButtonAbstraction button;

	public HP12CMouseListener(HP12CController presenter) {
		this.presenter = presenter;
		this.button = null;
	}

	public void mousePressed(MouseEvent e) {
		this.presenter.keyPressed(getKey(e));
	}

	public void mouseReleased(MouseEvent e) {
		this.presenter.keyReleased(getKey(e));
	}

	public void mouseClicked(MouseEvent e) {
	}

	private KeysEnumInterface getKey(MouseEvent e) {
		button = (ImageButtonAbstraction) e.getSource();
		return button.getKey();
	}
}
