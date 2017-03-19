package memory;

public class HP12CFinanceMemory extends FinanceMemoryAbstraction {
	
	public HP12CFinanceMemory(){
		super(5);		
	}

	public HP12CFinanceMemory(double[] fin){
		super(fin);		
	}
	
	public HP12CFinanceMemory(double n, double i, double pv, double pmt, double fv){
		super(5);

		this.fin[0] = n;
		this.fin[1] = i;
		this.fin[2] = pv;
		this.fin[3] = pmt;
		this.fin[4] = fv;
	}

	public void init(){ /* Do Nothing */ }
	
	public double getSize(){ return this.fin.length; }
	
	public void setN(double n){	this.fin[0] = n;}
	public void setI(double i){	this.fin[1] = i;}
	public void setPv(double pv){ this.fin[2] = pv;}
	public void setPmt(double pmt){	this.fin[3] = pmt;}
	public void setFv(double fv){ this.fin[4] = fv;}
	
	public double getN(){ return this.fin[0]; }
	public double getI(){ return this.fin[1]; }
	public double getPv(){ return this.fin[2]; }
	public double getPmt(){ return this.fin[3]; }
	public double getFv(){ return this.fin[4]; }
	
	public String toString() {
		
		String str = "==[FINANCE MEMORY]==\n";
		
		str += " - n  : "+this.fin[0] +"\n";
		str += " - i  : "+this.fin[1] +"\n";
		str += " - PV : "+this.fin[2] +"\n";
		str += " - PMT: "+this.fin[3] +"\n";
		str += " - FV : "+this.fin[4] +"\n";
		
		return str;
	}
	
}
