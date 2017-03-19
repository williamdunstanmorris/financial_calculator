package memory;

public class HP12CHistory extends HistoryAbstraction{

	public HP12CHistory(){
		super(100);
	}
	
	public HP12CHistory(int size){
		super(size);
	}
	
	public HP12CHistory(InstructionAbstraction hst[]){
		this.instr = new InstructionAbstraction[hst.length];
		for(int i=0; i<hst.length; i++)
			this.instr[i] = (InstructionAbstraction) hst[i];
	}

	public void init(){/* Does nothing */}
		
}