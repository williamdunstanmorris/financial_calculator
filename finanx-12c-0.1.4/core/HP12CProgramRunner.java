package core;

public class HP12CProgramRunner extends Thread {

	private HP12CExecutor exe;
	
	public HP12CProgramRunner(HP12CExecutor exe){
		super();
		this.exe = exe;
	}
	
	public void run(){
		while(exe.getFlags().getRun() == 1){
			exe.executeSingleStep();
			try{
				sleep(100);
			}
			catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
	
}
