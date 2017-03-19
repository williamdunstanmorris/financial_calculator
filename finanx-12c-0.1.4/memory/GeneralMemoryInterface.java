package memory;

public interface GeneralMemoryInterface {
	
	public int getSize();
	public int getUsedRegisters();
	public int getAvailableRegisters();
	
	public void set(int idx, double value);
	
	public double get(int idx);
	
	public double[] getWithTimes(int idx);
	
	public void setTimes(int idx, double times);
	
	public double getTimes(int idx);
	
	public void setWithTimes(int idx, double value, double times);
	
	public void setWithTimes(int idx, double[] a);
	
	public int getCurrentIndex();
	
	public double[][] getArray();

	public void put(double value, double times);
	
	public void put(double[] a);
	
	public void clear();
	
	public void print();
}
