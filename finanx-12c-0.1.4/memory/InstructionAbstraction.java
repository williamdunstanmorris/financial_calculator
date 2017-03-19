package memory;

public abstract class InstructionAbstraction implements InstructionInterface{

	protected StepAbstraction stp;
	protected StackAbstraction stk;

	public StepAbstraction getStep(){
		return this.stp;
	}
	
	public StackAbstraction getStack(){
		return this.stk;
	}
	
	public void setStep(StepAbstraction stp){
		this.stp.setModifier(stp.getModifier());
		this.stp.setKey(stp.getKey());
		this.stp.setComplement(stp.getComplement());
	}
	
	public void setStack(StackAbstraction stk){
		this.stk.setArray(stk.getArray());
	}
	
	public void clear() {
		this.stp.clear();
		this.stk.clear();
	}
	
	public void print() {
		System.out.println(this);
	}

	public String toString() {
		String rtn = "==[INSTRUCTION]=====\n";
		
		rtn += " - Instr: ";
		rtn += this.stp.getModifier()+", ";
		rtn += this.stp.getKey()+", ";
		rtn += this.stp.getModifier()+", ";
		rtn += this.stk.get(0)+"\n";
		
		// - Instr: -1, 36, -1 (Enter)
		return rtn;
	}
}
