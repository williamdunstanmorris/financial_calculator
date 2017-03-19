package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HP12CKeyListener implements KeyListener{

	private HP12CController controller;
	
	public HP12CKeyListener(HP12CController controller){
		this.controller = controller;
	}

    public void keyPressed(KeyEvent e) {
    	this.controller.keyPressed(e.getKeyChar());
    }
    
    public void keyReleased(KeyEvent e){
    	this.controller.keyReleased(e.getKeyChar());
    }
    
    public void keyTyped(KeyEvent e){}
}
