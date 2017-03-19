package memory;

public interface HistoryInterface {
	
	public InstructionAbstraction get(int index);
	public InstructionAbstraction pop();
	
	public void set(int index, InstructionInterface instr);
	public void put(InstructionInterface instr);
	
	public void shiftUp();
	public void shiftDown();
	public void rollUp();
	public void rollDown();
	
	public int getSize();

	public void print();
	public String toString();
	
	public void clear();
}
