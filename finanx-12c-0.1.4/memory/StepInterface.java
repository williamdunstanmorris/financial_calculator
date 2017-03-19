package memory;

public interface StepInterface {
	
	public void setModifier(int m);
	public void setKey(int k);
	public void setComplement(int c);
	
	public int getModifier();
	public int getKey();
	public int getComplement();
	
	public int[] getArray();
	public void clear();
	public void print();
	
}
