package core;

public interface InputExceptionInterface {

	public int getCode();
	public String getName();
	public String getMessage();
	public String getDetail();
	
	public void print();
}
