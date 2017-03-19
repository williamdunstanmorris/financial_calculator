package memory;

import core.HP12CInputException;

public interface DisplayInterface {

	public void init();
	
	public void inputChar(char ch);
	
	public String getString();
	
	public void setMessage(String str);
	
	public void setValue(double val);
	public double getValue() throws HP12CInputException;
	public void updateValue();

	public String getMantissa();
	
	public void setPrecision(int mode);
	public int getPrecision();
		
	public void setMode(int mode);
	public int getMode();
	
	public void setStatus(int status);
	public int getStatus();
	
	public void setLock(boolean lock);
	public boolean getLock();
	
	public void setPause(boolean pause);
	public boolean getPause();
	
	public void clear();
	
	public void print();
}
