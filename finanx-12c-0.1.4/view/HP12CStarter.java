package view;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import core.HP12CController;
import core.HP12CExecutor;

public class HP12CStarter {
	
	public static void main(String args[]){

		/*
		 try {
		        // Set System L&F
		        UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		    } 
		    catch (UnsupportedLookAndFeelException e) {
		       // handle exception
		    }
		    catch (ClassNotFoundException e) {
		       // handle exception
		    }
		    catch (InstantiationException e) {
		       // handle exception
		    }
		    catch (IllegalAccessException e) {
		       // handle exception
		    }
		*/
		HP12CController controller = new HP12CController();

	}
}

