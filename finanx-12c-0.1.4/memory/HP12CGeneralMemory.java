package memory;

public class HP12CGeneralMemory extends GeneralMemoryAbstraction {

	// IMPORTANT NOTE:
	// The registers from R0 to R6 are reserved
	// to store statistic data
	
	public HP12CGeneralMemory(){
		super(20);
	}

	public HP12CGeneralMemory(int size){
		super(size);
	}
	
	public HP12CGeneralMemory(double[][] mem){
		super(mem);
	}
	
	public void init() { /* Does nothing */ }
	
	
	//  Statistic Methods
	
	// Increase statistical registers
	public void sumStats(double x, double y){
		this.mem[1][0]+=1.0;  this.mem[1][1]=1.0;
		this.mem[2][0]+=  x;  this.mem[2][1]=1.0;
		this.mem[3][0]+=x*x;  this.mem[3][1]=1.0;
		this.mem[4][0]+=  y;  this.mem[4][1]=1.0;
		this.mem[5][0]+=y*y;  this.mem[5][1]=1.0;
		this.mem[6][0]+=x*y;  this.mem[6][1]=1.0;
	}
	
	// Decrease statistical registers
	public void subStats(double x, double y){
		this.mem[1][0]-=1.0;  this.mem[1][1]=1.0;
		this.mem[2][0]-=  x;  this.mem[2][1]=1.0;
		this.mem[3][0]-=x*x;  this.mem[3][1]=1.0;
		this.mem[4][0]-=  y;  this.mem[4][1]=1.0;
		this.mem[5][0]-=y*y;  this.mem[5][1]=1.0;
		this.mem[6][0]-=x*y;  this.mem[6][1]=1.0;
	}
	
	public void setR1(double R1){ this.mem[1][0]=R1; this.mem[1][1]=1.0; }
	public void setR2(double R2){ this.mem[2][0]=R2; this.mem[2][1]=1.0; }
	public void setR3(double R3){ this.mem[3][0]=R3; this.mem[3][1]=1.0; }
	public void setR4(double R4){ this.mem[4][0]=R4; this.mem[4][1]=1.0; }
	public void setR5(double R5){ this.mem[5][0]=R5; this.mem[5][1]=1.0; }
	public void setR6(double R6){ this.mem[6][0]=R6; this.mem[6][1]=1.0; }
	
	public double getR1(){ return this.mem[1][0]; }
	public double getR2(){ return this.mem[2][0]; }
	public double getR3(){ return this.mem[3][0]; }
	public double getR4(){ return this.mem[4][0]; }
	public double getR5(){ return this.mem[5][0]; }
	public double getR6(){ return this.mem[6][0]; }

	// Clear statistical registers
	public void clearStats(){
		this.mem[1][0]=0.0;  this.mem[1][1]=1.0;
		this.mem[2][0]=0.0;  this.mem[2][1]=1.0;
		this.mem[3][0]=0.0;  this.mem[3][1]=1.0;
		this.mem[4][0]=0.0;  this.mem[4][1]=1.0;
		this.mem[5][0]=0.0;  this.mem[5][1]=1.0;
		this.mem[6][0]=0.0;  this.mem[6][1]=1.0;
	}
	
	public void printStatisticData(){

		String str = "-----Statistic------\n";
		for(int i=1; i<7; i++){
			str += " - Mem"+i+": "+mem[i][0]+"  x  "+mem[i][1]+"\n";
		}
		
		System.out.println(str);

	}
}
