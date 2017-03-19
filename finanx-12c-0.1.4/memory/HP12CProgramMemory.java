package memory;

public class HP12CProgramMemory extends ProgramMemoryAbstraction{

	public HP12CProgramMemory(){
		super(1000);
	}
	
	public HP12CProgramMemory(int size){
		super(size);
	}
	
	public HP12CProgramMemory(HP12CStep[] prg) {
		super(prg);
	}

	public void init(){
	
		for(int i=0;i<this.prg.length;i++)
			this.prg[i] = new HP12CStep();
	}
	
}