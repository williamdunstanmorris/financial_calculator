package memory;

public abstract class HistoryAbstraction {
	
	protected InstructionAbstraction instr[];
	protected InstructionAbstraction swp;
	
	public HistoryAbstraction(){
		this.instr = new InstructionAbstraction[100];
	}
	
	public HistoryAbstraction(int size){
		this.instr = new InstructionAbstraction[size];
	}
	
	public InstructionAbstraction get(int index){
		return this.instr[index];
	}
	
	public void set(int index, InstructionAbstraction instr){
		this.instr[index] = instr;
	}
	
	public void shiftUp()
	{
		for (int i=instr.length-1; i>0; i--)
		{
			this.instr[i]=this.instr[i-1];
		}
	}	
	
	public void shiftDown()
	{
		for (int i=0; i<instr.length-1; i++)
		{
			this.instr[i]=this.instr[i+1];
		}
	}
	
	public void put(InstructionAbstraction instr)
	{		
		this.shiftUp();
		this.instr[0] = instr;
	}
	
	public InstructionAbstraction pop(){
		
		this.swp = instr[0];
		this.shiftDown();
		return this.swp;
	}

	public InstructionAbstraction top(){
		return this.instr[0];
	}
	
	public int getSize(){
		return this.instr.length;
	}

	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		String str = "---Program Memory---\n";
		for(int i=0; i<instr.length; i++){
			str += " - H"+i+": "+instr[i].getStep().getModifier()+", "+instr[i].getStep().getKey()+", "+instr[i].getStep().getComplement()+", "+instr[i].getStack().get(0)+"\n";
		}
		return str;
	}
	
	public void clear(){
		for(int i=0; i<instr.length; i++){
			instr[0].clear();
		}
	}
}
