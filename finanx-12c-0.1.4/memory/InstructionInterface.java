package memory;

public interface InstructionInterface {

	public StepAbstraction getStep();
	public StackAbstraction getStack();
	public void setStep(StepAbstraction stp);
	public void setStack(StackAbstraction stk);
	
	public void print();
	public String toString();
	
	public void clear();
}
