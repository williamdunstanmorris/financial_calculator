package memory;

public interface FinanceMemoryInterface {
	
	public void init();
	
	public double get(int idx);
	
	public void set(int idx, double val);
	
	public void setArray(double fin[]);
	
	public double [] getArray();
	
	public void clear();
	
	public void print();

}
