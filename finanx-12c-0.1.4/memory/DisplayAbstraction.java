package memory;

import core.HP12CInputException;

public abstract class DisplayAbstraction implements DisplayInterface {

	public static final int STATUS_READY = 0;
	public static final int STATUS_INPUT = 1;
	public static final int STATUS_OUTPUT = 2;

	public static final int MODE_NORMAL = 0;
	public static final int MODE_EXPONENTIAL = 1;
	public static final int MODE_PROGRAM = 2;

	protected String buf;
	protected String str;
	protected String msg; // String used to show error messages, mantissa etc.
	protected double val;

	protected int expo;
	protected int[] stp; // 0: index, 1: mod, 2: key, 3: cpm

	protected int status; // Indicates the status: input or output.
	protected int mode; // Indicates the input mode
	protected boolean dot; // Indicates if user inserted a decimal dot
	protected boolean neg; // Indicates if user changed the sign (positive to
							// negative)
	protected boolean full; // Indicates that the buffer is full
	protected boolean lock; // Indicates that the display is locked
	protected boolean pause; // Indicates that the display is paused for a while
	protected boolean comma; // Indicates if the decimal separator is a comma or
								// dot.
	protected int prec; // Precision (number of digits after the decimal dot)

	public DisplayAbstraction() {

		this.status = STATUS_READY;
		this.mode = MODE_NORMAL;

		this.clear();

		this.dot = false;
		this.full = false;

		this.prec = 9;

		this.init();
	}

	public abstract void init();

	public abstract void inputChar(char ch);

	public void setMessage(String msg) {
		this.msg = new String(msg);
	}

	public abstract String getString();

	public abstract void setValue(double val);

	public abstract double getValue() throws HP12CInputException;

	public abstract void updateValue();

	public abstract String getMantissa();

	public void setPrecision(int prec) {
		this.prec = prec;
	}

	public int getPrecision() {
		return this.prec;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void toggleComma() {
		if (this.getComma())
			this.setComma(false);
		else
			this.setComma(true);
	}

	public void setComma(boolean comma) {
		this.comma = comma;
	}

	public boolean getComma() {
		return this.comma;
	}

	public abstract void setReady();

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getMode() {
		return this.mode;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean getLock() {
		return this.lock;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean getPause() {
		return this.pause;
	}

	public void clear() {
		this.str = "";
		this.buf = "0";
		this.val = 0;
		this.expo = 0;
		this.stp = new int[4];
	}

	public void print() {
		System.out.println(this);
	}

	public String toString() {
		String rtn = "";
		rtn += "Dsp: " + str;
		return rtn;
	}

}
