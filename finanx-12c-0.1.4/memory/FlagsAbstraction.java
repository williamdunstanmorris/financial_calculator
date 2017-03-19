package memory;

public abstract class FlagsAbstraction implements FlagsInterface{

	protected String flg[][];
	
	public FlagsAbstraction(int size){
		this.flg = new String[size][2];
		this.init();
	}
	
	public FlagsAbstraction(String[][] flg){
		this.flg = flg;
		this.init();
	}

	public abstract void init();
	
	public int getFlag(String key) {
		try {
			for(int i = 0; i < flg.length; i++) {
				if(flg[i][0].equals(key)) {
					return Integer.parseInt(this.flg[i][1]);
				}
			}
			return 0;
		}catch(NumberFormatException e){
			return 0;
		}
	}
	public void setFlag(String key, int value) {
		for(int i = 0; i < flg.length; i++) {
			if(flg[i][0].equals(key)) {
				this.flg[i][1] = ""+value;
			}
		}
	}
	
	public abstract void reset();
	
	public void clear() {
		for(int i=0; i<flg.length; i++){
			flg[i][1]="";
		}
	}
	
	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		
		String str = "-------Flags--------\n";
		for(int i=0; i<flg.length; i++){
			str += " - Flg: "+flg[i][0]+" = "+flg[i][1]+"\n";
		}
		return str;
	}
}
