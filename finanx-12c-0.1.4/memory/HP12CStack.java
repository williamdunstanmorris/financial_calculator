	package memory;

public class HP12CStack extends StackAbstraction{

	private double lstX;
	
	public HP12CStack() {
		super(4);
	}
	
	public HP12CStack(int size) {
		super(size);
	}
	
	public HP12CStack(double[] stk) {
		super(stk);
	}
	
	public HP12CStack(HP12CStack stk) {
		super(stk.getArray());
		this.lstX = stk.getLastX();
	}
	
	public void init() {
		this.lstX = 0.0;
	}
	
	public void swapXY()
	{	
		this.swp = this.stk[1];
		this.stk[1] = this.stk[0];
		this.stk[0] = this.swp;		
	}
	
	public void lowerXY()
	{
	    if(this.stk[0]>this.stk[1])
	    {
	        this.swp = this.stk[1];
	        this.stk[1] = this.stk[0];
	        this.stk[0] = this.swp;
	    }
	}

	public void setLastX(double x){
		this.lstX = x;
	}
	
	public void setLastX(){
		this.lstX = this.stk[0];
	}

	public double getLastX(){
		return this.lstX;
	}
	
	public void clearLastX(){
		this.lstX = 0.0;
	}
}
