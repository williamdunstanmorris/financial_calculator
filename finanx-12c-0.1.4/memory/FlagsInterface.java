package memory;

public interface FlagsInterface {

	public int getFlag(String key);
	public void setFlag(String key, int value);
	
	public void reset();	
	public void clear();
	public void print();
	
}
