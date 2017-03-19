package memory;

public interface StackInterface {
	
	public double get(int index);
	public double pop();
	public double top();
	
	public void set(int index, double value);
	public void put(double val);
	
	public void shiftUp();
	public void shiftDown();
	public void rollUp();
	public void rollDown();
	
	public int getSize();
	public double[] getArray();
	public void setArray(double stk[]);

	public void print();
	public String toString();
	
	public void clear();

}
