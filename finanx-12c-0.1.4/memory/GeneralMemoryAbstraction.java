package memory;

public abstract class GeneralMemoryAbstraction implements GeneralMemoryInterface{

	protected double mem[][];
	protected int cur;
	
	public GeneralMemoryAbstraction(int size){
		mem = new double[size][2];

		for(int i=0; i<mem.length; i++){
			this.mem[i][0]=0;
			this.mem[i][1]=1;
		}
		
		this.cur=0;
		
		this.init();
	}
	
	public GeneralMemoryAbstraction(double[][] mem) {
		this.mem = mem;
		this.init();
	}

	// This method should be used to initialize the object.
	// It is called when the object's constructor is called.
	protected abstract void init();
	
	public int getSize(){
		return mem.length;
	}
	
	public int getUsedRegisters(){
		int cnt = 0;
		for(int i=0; i<mem.length; i++){
			if(mem[i][0]!=0.0){
				cnt++;
			}
		}
		return cnt;
	}
	
	public int getAvailableRegisters(){
		return getSize() - getUsedRegisters();
	}
	
	public void set(int idx, double value){
		if(idx<mem.length){ this.mem[idx][0] = value; }
	}
	
	public double get(int idx){
		double rtn=0;
		if(idx<mem.length){
		rtn = this.mem[idx][0];
		}
		return rtn;
	}
	
	public double[] getWithTimes(int idx){
		double rtn[]={0,0};
		if(idx<mem.length){
			rtn[0] = this.mem[idx][0];
			rtn[1] = this.mem[idx][1];
			}
			return rtn;
	}
	
	public void setTimes(int idx, double times){
		if(idx<mem.length){
		this.mem[idx][1] = times;
		}
	}	
	
	public double getTimes(int idx){
		if(idx<mem.length){	return this.mem[idx][1]; }
		else{ return 1.0; }
	}
	
	public void setWithTimes(int idx, double value, double times){
		if((idx<mem.length)&&(times>0)&&(times<100)){
		this.mem[idx][0] = value;
		this.mem[idx][1] = times;
		}
	}
	
	public void setWithTimes(int idx, double[] a){
		this.setWithTimes(idx, a[0], a[1]);
	}
	
	public int getCurrentIndex(){ return this.cur; }	
	
	public double[][] getArray(){ return mem; }	
	public void setArray(double[][] mem){ this.mem=mem; }
	
	// Inserts a new value in the memory
	public void put(double value, double times){
		this.setWithTimes(++this.cur, value, times);
	}
	
	// Inserts a new value in the memory
	// a[0]: value
	// a[1]: times
	public void put(double[] a){
		this.setWithTimes(++this.cur, a);
	}
	
	public void clear(){
		for(int i=0; i<mem.length; i++){
			mem[i][0]=0;
			mem[i][1]=1;
		}
		this.cur=0;
	}
	
	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		
		String str = "==[GENERAL MEMORY]==\n";
		for(int i=0; i<mem.length; i++){
			str += " - M"+i+": "+mem[i][0]+" x "+mem[i][1]+"\n";
		}
		return str;
	}

}
