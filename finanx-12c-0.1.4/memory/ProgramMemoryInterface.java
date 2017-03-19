package memory;

public interface ProgramMemoryInterface {
	
	public int getSize();
	public int getUsedSteps();
	public int getAvailableSteps();

	// Sets the current step
	public void set(StepAbstraction stp);
	// Sets the step that is located in a given index
	public void set(int idx, StepAbstraction stp);
	
	// Returns a step that is located in a given index
	public StepAbstraction get(int idx);
	// Returns the current step
	public StepAbstraction get();
	
	// Increment index (idx++)
	// It returns false if failed
	public boolean next();
	// Decrements index (idx--)
	// It returns false if failed
	public boolean back();
	
	public void setModifier(int mod);
	public void setModifier(int idx, int mod);
	
	public void setKey(int key);
	public void setKey(int idx, int key);
	
	public void setComplement(int cpm);
	public void setComplement(int idx, int cpm);
	
	public int getModifier();
	public int getModifier(int idx);
	
	public int getKey();
	public int getKey(int idx);
	
	public int getComplement();
	public int getComplement(int idx);
	
	public int getCurrentIndex();
	public void setCurrentIndex(int cur);
	
	// Inserts a new step in the program memory
	public void put(StepAbstraction stp);
	
	public StepAbstraction[] getArray();	
	public void setArray(StepAbstraction[] stp);
	
	public void clear();
	
	public void print();
}
