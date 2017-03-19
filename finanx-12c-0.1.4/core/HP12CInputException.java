package core;

public class HP12CInputException extends Exception implements InputExceptionInterface{

	private static final long serialVersionUID = 5767867117308016246L;

	private HP12CErrors error;
	private String detail;
	
	public HP12CInputException(HP12CErrors error){
		super(error.getMessage());
		this.error = error;
		this.detail = "";
	}
	
	public HP12CInputException(HP12CErrors error, String detail){
		super(error.getMessage());
		this.error = error;
		this.detail = detail;
	}

	public int getCode() {
		return this.error.getCode();
	}
	
	public String getName(){
		return this.error.getName();
	}
	
	public String getMessage(){
		return this.error.getMessage();
	}
	
	public String getDetail(){
		return this.detail;
	}
	
	public void print() {
		System.out.println(this);
	}
	
	public String toString() {
		String r = "";
		
		r += error.getName()+": ";
		r += error.getCode()+": ";
		
		return r;
	}
}
