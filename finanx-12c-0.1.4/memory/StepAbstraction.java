package memory;

public abstract class StepAbstraction implements StepInterface {

	// Step considered null or invalid
	public static final HP12CStep STP_NULL = new HP12CStep(-1, -1, -1);

	private int mod, key, cpm;

	public StepAbstraction() {
		this.mod = -1;
		this.key = -1;
		this.cpm = -1;

		this.init();
	}

	public StepAbstraction(int mod, int key, int cpm) {
		this.mod = mod;
		this.key = key;
		this.cpm = cpm;

		this.init();
	}

	public StepAbstraction(StepAbstraction stp) {
		this.mod = stp.getModifier();
		this.key = stp.getKey();
		this.cpm = stp.getComplement();

		this.init();
	}

	public void setStep(int mod, int key, int cpm) {
		this.mod = mod;
		this.key = key;
		this.cpm = cpm;

		this.init();
	}

	// This method should be used to initialize the object.
	// It is called when the object's constructor is called.
	protected abstract void init();

	public int getModifier() {
		return this.mod;
	}

	public int getKey() {
		return this.key;
	}

	public int getComplement() {
		return this.cpm;
	}

	public void setModifier(int mod) {
		this.mod = mod;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public void setComplement(int cpm) {
		this.cpm = cpm;
	}

	public int[] getArray() {
		int tmp[] = { mod, key, cpm };
		return tmp;
	}

	public void clear() {
		this.mod = -1;
		this.key = -1;
		this.cpm = -1;
	}

	public void print() {
		System.out.println(this);
	}

	public String toString() {

		String rtn = "==[STEP]============\n";
		rtn += " - Stp: ";
		rtn += this.mod + ", ";
		rtn += this.key + ", ";
		rtn += this.cpm + "\n";

		return rtn;
	}

	public boolean equals(StepAbstraction stp) {

		return (this.getModifier() == stp.getModifier())
				&& (this.getKey() == stp.getKey())
				&& (this.getComplement() == stp.getComplement());

	}
}
