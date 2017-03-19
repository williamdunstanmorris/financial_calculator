package memory;

public class HP12CInstruction extends InstructionAbstraction{	
	
	public HP12CInstruction() {
		super();
		this.stp = new HP12CStep();
		this.stk = new HP12CStack();
	}
	
	public HP12CInstruction(HP12CStep stp, HP12CStack stk) {
		this.stp = new HP12CStep(stp);
		this.stk = new HP12CStack(stk);
	}
	
	protected void init() {/* Does Nothing */}
	
	public void setStep(HP12CStep stp){
		this.stp = new HP12CStep(stp);
	}
	
	public void setStack(HP12CStack stk){
		this.stk =  new HP12CStack(stk);
	}
	
	public void setStep(StepAbstraction stp){
		this.stp = new HP12CStep((HP12CStep) stp);
	}
	
	public void setStack(StackAbstraction stk){
		this.stk =  new HP12CStack((HP12CStack) stk);
	}
	
}
