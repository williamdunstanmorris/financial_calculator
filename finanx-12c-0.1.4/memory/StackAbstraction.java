package memory;

public abstract class StackAbstraction implements StackInterface{

	protected double stk[]; 
	protected double swp;
	
	public StackAbstraction(int size) {
		this.stk = new double[size];
		this.init();
	}
	
	public StackAbstraction(double[] stk) {
		
		this.stk = new double[stk.length];
		
		for (int i=0; i<stk.length; i++)
			this.stk[i] = stk[i];
		
		this.init();
	}
	
	// This method should be used to initialize the object. 
	// It is called when the object's constructor is called.
	protected abstract void init();
	
	public double get(int idx)
	{
		return this.stk[idx];	
	}
	
	public void set(int idx, double val)
	{
		this.stk[idx] = val;
	}
	
	public double[] getArray()
	{
		return this.stk;
	}
	
	public void setArray(double[] stk)
	{
		for(int i=0; i<stk.length; i++)
			this.stk[i] = stk[i];
	}

	public void shiftUp()
	{
		for (int i=stk.length-1; i>0; i--)
		{
			this.stk[i]=this.stk[i-1];
		}
	}	
	
	public void shiftDown()
	{
		for (int i=0; i<stk.length-1; i++)
		{
			this.stk[i]=this.stk[i+1];
		}
	}
	
	public void put(double val)
	{		
		this.shiftUp();
		this.stk[0] = val;
	}
	
	public double pop(){
		
		this.swp = stk[0];
		this.shiftDown();
		return this.swp;
	}

	public double top(){
		return this.stk[0];
	}
	
	public void rollUp()
	{
		this.swp = this.stk[stk.length-1];
		this.shiftUp();
		this.stk[0] = this.swp;
	}
	
	public void rollDown()
	{
		this.swp = this.stk[0];
		this.shiftDown();
		this.stk[stk.length-1] = this.swp;
	}
	
	public int getSize(){
		return stk.length;
	}
	
	public void clear()
	{
		for(int i=0; i<this.stk.length; i++)
		{
			this.stk[i] = 0.0;
		}
	}
	
	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		String str = "==[STACK]===========\n";
		for (int i=0; i<this.stk.length; i++){
			str += " - S"+i+": "+this.stk[i]+"\n";
		}
		return str;
	}
}
