package memory;

public abstract class ProgramMemoryAbstraction implements ProgramMemoryInterface {
	protected StepAbstraction prg[];
	protected int idx; 
	
	public ProgramMemoryAbstraction(int size){

		// This line only reserves an array 
		// for the Step implementations
		// It's recommended to use the init() method
		// to initialize every single step in this array.
		this.prg = new StepAbstraction[size];
		
		this.idx=0;
		
		this.init();
	}
	
	public ProgramMemoryAbstraction(StepAbstraction[] prg) {
		
		this.prg = prg;
		
		this.idx=0;
		
		this.init();
	}
	
	// This method should be used to initialize the object. 
	// It is called when the object's constructor is called.
	protected abstract void init();
	
	public int getSize(){
		return prg.length;
	}
	
	public int getUsedSteps(){
		int i = 0;
		for (i=1; i<prg.length; i++){
			if(prg[i].equals(StepAbstraction.STP_NULL))
				break;
		}
		return i-1;
	}
	
	public int getAvailableSteps(){
		return getSize() - getUsedSteps() - 1;
	}
	
	public void set(int idx, StepAbstraction stp){
		if(idx<prg.length){ 
			this.prg[idx] = stp; 
		}
	}
	
	public void set(StepAbstraction stp){
		if(idx<prg.length){ 
			this.prg[idx] = stp;
		}
	}
	
	public StepAbstraction get(int idx){
		if(idx<prg.length){
			return prg[idx];
		}
		return null;		
	}
	
	public StepAbstraction get(){
		if(idx<prg.length){
			return prg[idx];
		}
		return null;
	}
	
	public boolean next(){
		if (this.idx < this.prg.length-1)
			this.idx++;
		else 
			return false;
		
		return true;
	}
	
	public boolean back(){
		if (this.idx > 0)
			this.idx--;
		else 
			return false;
		
		return true;
	}
	
	public void setModifier(int mod){
		if(idx<prg.length){
			this.prg[idx].setModifier(mod);
		}
	}
	
	public void setModifier(int idx, int mod){
		if(idx<prg.length){
			this.prg[idx].setModifier(mod);
		}
	}
	
	public void setKey(int key){
		if(idx<prg.length){
			this.prg[idx].setKey(key);
		}
	}	
	
	public void setKey(int idx, int key){
		if(idx<prg.length){
			this.prg[idx].setKey(key);
		}
	}	
	
	public void setComplement(int cpm){
		if(idx<prg.length){
			this.prg[idx].setComplement(cpm);
		}
	}	
	
	public void setComplement(int idx, int cpm){
		if(idx<prg.length){
			this.prg[idx].setComplement(cpm);
		}
	}	
	
	public int getModifier(){
		if(idx<prg.length){ return this.prg[idx].getModifier(); }
		else{ return -1; }
	}
	
	public int getModifier(int idx){
		if(idx<prg.length){ return this.prg[idx].getModifier(); }
		else{ return -1; }
	}
	
	public int getKey(){
		if(idx<prg.length){ return this.prg[idx].getKey(); }
		else{ return -1; }
	}
	
	public int getKey(int idx){
		if(idx<prg.length){ return this.prg[idx].getKey(); }
		else{ return -1; }
	}
	
	public int getComplement(){
		if(idx<prg.length){ return this.prg[idx].getComplement(); }
		else{ return -1; }
	}
	
	public int getComplement(int idx){
		if(idx<prg.length){ return this.prg[idx].getComplement(); }
		else{ return -1; }
	}
	
	public int getCurrentIndex(){ return this.idx; }	
	public void setCurrentIndex(int idx){	this.idx=idx; }
	
	public void put(StepAbstraction stp){
		this.idx++;
		if(idx<prg.length){ 
			this.prg[idx] = stp; 
		}
	}
	
	public StepAbstraction[] getArray(){ return prg; }	
	public void setArray(StepAbstraction[] prg){ this.prg=prg; }
	
	public void clear(){
		for(int i=0; i<prg.length; i++){
			this.prg[i].clear();
		}
		this.idx=0;
	}
	
	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		String str = "==[PROGRAM MEMORY]==\n";
		for(int i=0; i<prg.length; i++){
			str += " - P"+i+": "+prg[i].getModifier()+", "+prg[i].getKey()+", "+prg[i].getComplement()+"\n";
		}
		return str;
	}

}
