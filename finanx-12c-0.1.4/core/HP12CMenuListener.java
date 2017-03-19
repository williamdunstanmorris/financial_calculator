package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class HP12CMenuListener implements MenuListener, ActionListener, ItemListener {
	
	private HP12CController controller;
	private JMenuItem src;
	private String cmd[];
	
	public HP12CMenuListener(HP12CController controller){
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		src =(JMenuItem)(e.getSource());
		cmd = src.getActionCommand().split("::");
		controller.doMenuCommand(cmd[0], (cmd.length>1?cmd[1]:null));
	}
	
	public void itemStateChanged(ItemEvent e) {
/*		src = (JMenuItem)(e.getSource());
		controller.doMenuCommand(src.getActionCommand(), null);*/

	}
	
	public void menuSelected(MenuEvent e) {
/*		src = (JMenuItem)(e.getSource());
		 controller.doMenuCommand(src.getActionCommand(), null);*/
		 
	}
	
	public void menuDeselected(MenuEvent e) {
		// TODO
	}
	
	public void menuCanceled(MenuEvent e) {
		// TODO
	}
}
