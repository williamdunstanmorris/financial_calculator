package memory;

public abstract class FinanceMemoryAbstraction {

	protected double[] fin;
	
	public FinanceMemoryAbstraction(int size) {
		this.fin = new double[size];
		this.init();
	}
	
	public FinanceMemoryAbstraction(double[] fin) {
		this.fin = fin;
		this.init();
	}
	
	public abstract void init();
	
	public double get(int idx){ return this.fin[idx]; }
	public void set(int idx, double val){ this.fin[idx] = val;}
	
	public void setArray(double fin[]){
		this.fin = fin;
	}
	
	public double [] getArray(){		
		return this.fin;
	}
	
	public void clear(){
		for(int i = 0; i < fin.length; i++){
			fin[i] = 0.0;
		}
	}
	
	public void print() {
		System.out.println(this);
	}
	
	public String toString(){
		
		String str = "==[FINANCE MEMORY]==\n";
		for(int i=0; i<fin.length; i++){
			str += " - F"+i+": "+fin[i]+"\n";
		}
		return str;
	}
}
