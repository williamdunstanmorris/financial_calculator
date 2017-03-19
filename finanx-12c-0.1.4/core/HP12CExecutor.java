package core;

import javax.swing.JOptionPane;

import utilities.Date;
import utilities.Timer;
import memory.DisplayAbstraction;
import memory.HP12CDisplay;
import memory.HP12CFinanceMemory;
import memory.HP12CFlags;
import memory.HP12CGeneralMemory;
import memory.HP12CHistory;
import memory.HP12CProgramMemory;
import memory.HP12CStep;
import memory.HP12CStack;

public class HP12CExecutor {

	private HP12CKeys k;
	private HP12CStep stp;
	private double tmp[];
	private Date dt[];

	private HP12CController controller;

	private HP12CFlags flg;
	private HP12CDisplay dsp;
	private HP12CProgramRunner runner;

	private HP12CStack stk;
	private HP12CFinanceMemory fin;
	private HP12CGeneralMemory mem;
	private HP12CProgramMemory prg;
	private HP12CHistory hst;

	private HP12CConfiguration cfg;

	public HP12CExecutor() {
		this.init();
	}

	protected void init() {

		// Not persistent data
		this.flg = new HP12CFlags();
		this.stp = new HP12CStep();
		this.hst = new HP12CHistory();
		this.dsp = new HP12CDisplay();

		// Persistent data
		this.stk = new HP12CStack();
		this.fin = new HP12CFinanceMemory();
		this.mem = new HP12CGeneralMemory();
		this.prg = new HP12CProgramMemory();

		// Persistent configurations
		this.cfg = new HP12CConfiguration();

		// Program runner. An independent thread
		// that executes steps sequentially.
		this.runner = new HP12CProgramRunner(this);
	}

	public HP12CController getController() {
		return this.controller;
	}

	public void setController(HP12CController presenter) {
		this.controller = presenter;
	}

	public HP12CConfiguration getConfigs() {
		return this.cfg;
	}

	public void setConfigs(HP12CConfiguration cfg) {
		this.cfg = cfg;

		this.setFlags();
		this.setDisplay();
	}

	public void setFlags() {

		this.getFlags().setBegin(cfg.getBeg());

		this.getFlags().setDmy(cfg.getDmy());
	}

	public void setDisplay() {

		if (cfg.getCom() == 1)
			this.getDisplay().setComma(true);
		else
			this.getDisplay().setComma(false);

		this.getDisplay().setPrecision(cfg.getFix());
	}

	public HP12CStack getStack() {
		return this.stk;
	}

	public HP12CFinanceMemory getFinanceMemory() {
		return this.fin;
	}

	public HP12CGeneralMemory getGeneralMemory() {
		return this.mem;
	}

	public HP12CProgramMemory getProgramMemory() {
		return this.prg;
	}

	public HP12CHistory getOperationHistory() {
		return this.hst;
	}

	public HP12CFlags getFlags() {
		return this.flg;
	}

	public HP12CDisplay getDisplay() {
		return this.dsp;
	}

	public void setStack(HP12CStack stk) {
		this.stk = stk;
	}

	public void setFinanceMemory(HP12CFinanceMemory fin) {
		this.fin = fin;
	}

	public void setGeneralMemory(HP12CGeneralMemory mem) {
		this.mem = mem;
	}

	public void setProgramMemory(HP12CProgramMemory prg) {
		this.prg = prg;
	}

	public void setOperationHistory(HP12CHistory hst) {
		this.hst = hst;
	}

	public void setFlags(HP12CFlags flg) {
		this.flg = flg;
	}

	public void setDisplay(HP12CDisplay dsp) {
		this.dsp = dsp;
	}

	// Sets the stacks x value.
	public void setX(double x) {
		// If the current status is 'STATUS_INPUT' or 'STATUS_OUTPUT',
		// the stack is lifted up with the new value.
		if (dsp.getStatus() != DisplayAbstraction.STATUS_READY)
			stk.put(x);
		else
			stk.set(0, x);
	}

	public double getX() {
		return this.stk.top();
	}

	public void shiftUpIfOutputStatus() {
		if ((dsp.getStatus() == HP12CDisplay.STATUS_OUTPUT)
				|| (dsp.getStatus() == HP12CDisplay.STATUS_OUTPUT2)) {
			stk.shiftUp();
		}
	}

	public void keyPressed(KeysEnumInterface key) {

		if (key == null)
			return;

		// Converting an KeyEnum interface to a KeyEnum Object
		HP12CKeys k = (HP12CKeys) key;

		// If the calculator is running a program and
		// a key is pressed, program execution stops.
		if (flg.getRun() == 1) {
			this.stopProgram();
			dsp.setLock(true);
		}

		switch (k) {
		case KEY_0: {
			break;
		}
		case KEY_1: {
			break;
		}
		case KEY_2: {
			break;
		}
		case KEY_3: {
			break;
		}
		case KEY_4: {
			break;
		}
		case KEY_5: {
			break;
		}
		case KEY_6: {
			break;
		}
		case KEY_7: {
			break;
		}
		case KEY_8: {
			break;
		}
		case KEY_9: {
			if (flg.getG() == 1) {
				String tmp = "";

				tmp += "P" + HP12CDisplay.zeroPad(prg.getUsedSteps(), 3);
				tmp += " ";
				tmp += "r"
						+ HP12CDisplay.zeroPad(mem.getAvailableRegisters(), 3);

				dsp.setMessage(tmp);
				dsp.setPause(true);
				controller.getWindow().updateDisplay();
			}
			break;
		}
		case KEY_DIV: {
			break;
		}
		case KEY_MUL: {
			break;
		}
		case KEY_SUB: {
			break;
		}
		case KEY_SUM: {
			break;
		}
		case KEY_N: {
			break;
		}
		case KEY_I: {
			break;
		}
		case KEY_PV: {
			break;
		}
		case KEY_PMT: {
			break;
		}
		case KEY_FV: {
			break;
		}
		case KEY_CHS: {
			break;
		}
		case KEY_POW: {
			break;
		}
		case KEY_RECIPROCAL: {
			break;
		}
		case KEY_PERC_TOT: {
			break;
		}
		case KEY_PERC_DELTA: {
			break;
		}
		case KEY_PERC: {
			break;
		}
		case KEY_EEX: {
			break;
		}
		case KEY_RS: {
			break;
		}
		case KEY_SST: {
			if (dsp.getMode() == HP12CDisplay.MODE_NORMAL) {

				if (flg.getG() == 1) {
					if (prg.getCurrentIndex() == 0)
						prg.setCurrentIndex(prg.getSize() - 1);
				} else {
					if (prg.getCurrentIndex() == prg.getSize() - 1)
						prg.setCurrentIndex(0);
					else if (prg.getCurrentIndex() == 0)
						prg.next();
				}

				dsp.inputProgramStep(prg.getCurrentIndex(),
						(HP12CStep) prg.get());
				dsp.setMode(HP12CDisplay.MODE_PROGRAM);
				controller.getWindow().updateDisplay();
				dsp.setMode(HP12CDisplay.MODE_NORMAL);
				dsp.setPause(true);
			}
			break;
		}
		case KEY_ROLL: {
			break;
		}
		case KEY_XY: {
			break;
		}
		case KEY_CLX: {
			break;
		}
		case KEY_ENTER: {
			if (flg.getF() == 1) {
				dsp.setMessage(dsp.getMantissa());
				dsp.setPause(true);
				controller.getWindow().updateDisplay();
			}
			break;
		}
		case KEY_ON: {
			break;
		}
		case KEY_F: {
			break;
		}
		case KEY_G: {
			break;
		}
		case KEY_STO: {
			break;
		}
		case KEY_RCL: {
			break;
		}
		case KEY_DOT: {
			break;
		}
		case KEY_TOT: {
			break;
		}
		default:
			break;
		}
	}

	public void keyReleased(KeysEnumInterface key) {

		if (key == null)
			return;

		// Converting an KeyEnum interface to a KeyEnum implementation
		k = (HP12CKeys) key;

		try {

			if (dsp.getPause()) {

				Timer t = new Timer(1);
				t.run();

				dsp.setPause(false);
			} else if (dsp.getLock()) {
				dsp.setLock(false);
				return;
			}

			// If the calculator is in program mode,
			// sends the key command to the appropriate method
			if (flg.getPrgm() == 1) {
				programInput(k);
				return;
			}

			switch (k) {
			case KEY_0: {
				this.doKey00();
				break;
			}
			case KEY_1: {
				this.doKey01();
				break;
			}
			case KEY_2: {
				this.doKey02();
				break;
			}
			case KEY_3: {
				this.doKey03();
				break;
			}
			case KEY_4: {
				this.doKey04();
				break;
			}
			case KEY_5: {
				this.doKey05();
				break;
			}
			case KEY_6: {
				this.doKey06();
				break;
			}
			case KEY_7: {
				this.doKey07();
				break;
			}
			case KEY_8: {
				this.doKey08();
				break;
			}
			case KEY_9: {
				this.doKey09();
				break;
			}
			case KEY_DIV: {
				this.doKey10();
				break;
			}
			case KEY_MUL: {
				this.doKey20();
				break;
			}
			case KEY_SUB: {
				this.doKey30();
				break;
			}
			case KEY_SUM: {
				this.doKey40();
				break;
			}
			case KEY_N: {
				this.doKey11();
				break;
			}
			case KEY_I: {
				this.doKey12();
				break;
			}
			case KEY_PV: {
				this.doKey13();
				break;
			}
			case KEY_PMT: {
				this.doKey14();
				break;
			}
			case KEY_FV: {
				this.doKey15();
				break;
			}
			case KEY_CHS: {
				this.doKey16();
				break;
			}
			case KEY_POW: {
				this.doKey21();
				break;
			}
			case KEY_RECIPROCAL: {
				this.doKey22();
				break;
			}
			case KEY_PERC_TOT: {
				this.doKey23();
				break;
			}
			case KEY_PERC_DELTA: {
				this.doKey24();
				break;
			}
			case KEY_PERC: {
				this.doKey25();
				break;
			}
			case KEY_EEX: {
				this.doKey26();
				break;
			}
			case KEY_RS: {
				this.doKey31();
				break;
			}
			case KEY_SST: {
				this.doKey32();
				break;
			}
			case KEY_ROLL: {
				this.doKey33();
				break;
			}
			case KEY_XY: {
				this.doKey34();
				break;
			}
			case KEY_CLX: {
				this.doKey35();
				break;
			}
			case KEY_ENTER: {
				this.doKey36();
				break;
			}
			case KEY_ON: {

				int r;
				String msg = controller.getWindow().getLanguageStringList()
						.getValue("DIALOG_QUIT");

				r = JOptionPane.showConfirmDialog(null, msg);

				if (r == JOptionPane.YES_OPTION) {
					controller.quit();
				} else if (r == JOptionPane.NO_OPTION) {
					this.doKey41();
				}

				break;
			}
			case KEY_F: {
				this.doKey42();
				break;
			}
			case KEY_G: {
				this.doKey43();
				break;
			}
			case KEY_STO: {
				this.doKey44();
				break;
			}
			case KEY_RCL: {
				this.doKey45();
				break;
			}
			case KEY_DOT: {
				this.doKey48();
				break;
			}
			case KEY_TOT: {
				this.doKey49();
				break;
			}
			default: {
				break;
			}
			} // End Switch

			this.updateDisplay();

			// Debug
			
			System.out.println("--------------------\n");
			System.out.println(stp);
			System.out.println(fin);
			System.out.println(stk);

		} // End Try
		catch (HP12CInputException e) {
			this.dsp.setMessage(" Error " + e.getCode());
			this.clearFgsr();
			this.dsp.setLock(true);

			// Debug
			System.out.println("[Error " + e.getCode() + "] " + e.getDetail()
					+ "\n");
			// e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Updates the display and the X values so that
	// both have the same value.
	public void updateDisplay() {
		try {
			if ((dsp.getStatus() != HP12CDisplay.STATUS_INPUT)) {
				dsp.setValue(this.getX());
			} else {
				stk.set(0, dsp.getValue());
			}
		} catch (HP12CInputException e) {
			System.out.println("[Error " + e.getCode() + "] " + e.getDetail());
			e.printStackTrace();

			this.dsp.setLock(true);
			this.dsp.setMessage(" Error " + e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Processes STO operations. It Receives integers to form the memory index.
	 * It uses the memory index to store the X value into a memory register.
	 * Note 1: If the calculator has only 20 registers the calculator will work
	 * like the HP12C Gold, on witch a point represents 10. Note 2: It store
	 * from 000 to 999.
	 */
	protected void stoInput(int i) throws HP12CInputException {

		if (flg.getSto() == 1) {
			tmp = new double[3];

			if (mem.getSize() <= 20) {
				if (i == -1) {
					tmp[0] = 0;
					tmp[1] = 1;
					flg.setSto(3);
				} else {
					tmp[0] = 0;
					tmp[1] = 0;
					tmp[2] = i;
					flg.setSto(4);
				}
			} else if (mem.getSize() <= 100) {
				if (i == -1)
					return;
				tmp[0] = 0;
				tmp[1] = i;
				flg.setSto(3);
			} else {
				if (i == -1)
					return;
				tmp[0] = i;
				flg.setSto(2);
			}
		} else if (flg.getSto() == 2) {
			if (i == -1)
				return;
			tmp[1] = i;
			flg.setSto(3);
		} else if (flg.getSto() == 3) {
			if (i == -1)
				return;
			tmp[2] = i;
			flg.setSto(4);
		}

		if (flg.getSto() == 4) {
			flg.setSto(0);
			stp.setComplement((int) (tmp[0] * 100 + tmp[1] * 10 + tmp[2]));
		}

		if (flg.getSto() == 0) {
			if (dsp.getMode() == HP12CDisplay.MODE_NORMAL) {
				if (stp.getModifier() == HP12CKeys.KEY_STO.getCode()) {
					if (stp.getKey() == HP12CKeys.KEY_SUM.getCode()) {
						HP12CFunctions.sum(stk.top(),
								mem.get(stp.getComplement()));
						mem.set(stp.getComplement(),
								HP12CFunctions.sum(stk.top(),
										mem.get(stp.getComplement())));
					} else if (stp.getKey() == HP12CKeys.KEY_SUB.getCode()) {
						HP12CFunctions.sub(stk.top(),
								mem.get(stp.getComplement()));
						mem.set(stp.getComplement(),
								HP12CFunctions.sub(stk.top(),
										mem.get(stp.getComplement())));
					} else if (stp.getKey() == HP12CKeys.KEY_MUL.getCode()) {
						HP12CFunctions.mul(stk.top(),
								mem.get(stp.getComplement()));
						mem.set(stp.getComplement(),
								HP12CFunctions.mul(stk.top(),
										mem.get(stp.getComplement())));
					} else if (stp.getKey() == HP12CKeys.KEY_DIV.getCode()) {
						HP12CFunctions.div(stk.top(),
								mem.get(stp.getComplement()));
						mem.set(stp.getComplement(),
								HP12CFunctions.div(stk.top(),
										mem.get(stp.getComplement())));
					}
				} else {
					mem.set((int) (tmp[0] * 100 + tmp[1] * 10 + tmp[2]),
							stk.top());
				}
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			}
		}
	}

	/*
	 * Processes RCL operations. It Receives integers to form the memory index.
	 * It uses the memory index to store the X value into a memory register.
	 * Note 1: If the calculateor has only 20 registers the calculator will work
	 * like the HP12C Gold, on witch a point represents 10. Note 2: It recall
	 * from 000 to 999.
	 */
	protected void rclInput(int i) {

		if (flg.getRcl() == 1) {
			tmp = new double[3];

			if (mem.getSize() <= 20) {
				if (i == -1) {
					tmp[0] = 0;
					tmp[1] = 1;
					flg.setRcl(3);
				} else {
					tmp[0] = 0;
					tmp[1] = 0;
					tmp[2] = i;
					flg.setRcl(4);
				}
			} else if (mem.getSize() <= 100) {
				if (i == -1)
					return;
				tmp[0] = 0;
				tmp[1] = i;
				flg.setRcl(3);
			} else {
				if (i == -1)
					return;
				tmp[0] = i;
				flg.setRcl(2);
			}
		} else if (flg.getRcl() == 2) {
			if (i == -1)
				return;
			tmp[1] = i;
			flg.setRcl(3);
		} else if (flg.getRcl() == 3) {
			if (i == -1)
				return;
			tmp[2] = i;
			flg.setRcl(4);
		}

		if (flg.getRcl() == 4) {
			flg.setRcl(0);
			stp.setComplement((int) (tmp[0] * 100 + tmp[1] * 10 + tmp[2]));
		}

		if (flg.getRcl() == 0) {
			if (dsp.getMode() == HP12CDisplay.MODE_NORMAL) {
				setX(mem.get((int) (tmp[0] * 100 + tmp[1] * 10 + tmp[2])));
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			}
		}
	}

	/*
	 * Processes GTO operations.
	 */
	protected void gtoInput(int i) {

		if (flg.getGto() == 1) {
			tmp = new double[3];

			if (prg.getSize() <= 20) {
				if (i == -1) {
					tmp[0] = 0;
					tmp[1] = 1;
					flg.setGto(3);
				} else {
					tmp[0] = 0;
					tmp[1] = 0;
					tmp[2] = i;
					flg.setGto(4);
				}
			} else if (prg.getSize() <= 100) {
				if (i == -1)
					return;
				tmp[0] = 0;
				tmp[1] = i;
				flg.setGto(3);
			} else {
				if (i == -1)
					return;
				tmp[0] = i;
				flg.setGto(2);
			}
		} else if (flg.getGto() == 2) {
			if (i == -1)
				return;
			tmp[1] = i;
			flg.setGto(3);
		} else if (flg.getGto() == 3) {
			if (i == -1)
				return;
			tmp[2] = i;
			flg.setGto(4);
		}

		if (flg.getGto() == 4) {
			if (flg.getPrgm() == 1) {
				flg.setGto(0);
				stp.setStep(HP12CStep.STP_G_ROLL);
				stp.setComplement((int) (tmp[0] * 100 + tmp[1] * 10 + tmp[2]));
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			} else {
				flg.setGto(0);
				prg.setCurrentIndex((int) (tmp[0] * 100 + tmp[1] * 10 + tmp[2]));
				stp.setStep(HP12CStep.STP_G_ROLL);
				stp.setComplement((int) (tmp[0] * 100 + tmp[1] * 10 + tmp[2]));
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			}
		}
	}

	private boolean checkOddPeriod() {
		try {

			if (HP12CFunctions.fracPart(fin.getN()) != 0.0)
				return true;
			else
				return false;

		} // End Try
		catch (HP12CInputException e) {
			this.dsp.setLock(true);
			this.dsp.setMessage(" Error " + e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	protected void executeOddPeriodTasks() {
		try {
			// With odd period using simple interest
			if (flg.getC() == 0) {
				HP12CFunctions.simpleFutureValue(
						HP12CFunctions.fracPart(fin.getN()), (fin.getI()),
						fin.getPv());
				fin.setPv(HP12CFunctions.simpleFutureValue(
						HP12CFunctions.fracPart(fin.getN()), (fin.getI()),
						fin.getPv()));
			}
			// With odd period using compound interest
			else {
				HP12CFunctions.futureValue(HP12CFunctions.fracPart(fin.getN()),
						(fin.getI()), fin.getPv());
				fin.setPv(HP12CFunctions.futureValue(
						HP12CFunctions.fracPart(fin.getN()), (fin.getI()),
						fin.getPv()));
			}

			// Fixing PV sign
			fin.setPv(-fin.getPv());

			// Truncating the odd period
			fin.setN(HP12CFunctions.intPart(fin.getN()));

		} // End Try
		catch (HP12CInputException e) {
			System.out.println("[Error " + e.getCode() + "] " + e.getDetail());
			e.printStackTrace();

			this.dsp.setLock(true);
			this.dsp.setMessage(" Error " + e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void showDisplayMessage(String msg) {
		dsp.setMessage(msg);
		dsp.setLock(true);
		controller.getWindow().updateDisplay();
	}

	public void programInput(KeysEnumInterface key) {

		if (key == null)
			return;

		if (dsp.getLock()) {
			dsp.setLock(false);
			return;
		}

		// Converting a KeyEnum interface to a KeyEnum implementation
		k = (HP12CKeys) key;

		try {
			switch (k) {
			case KEY_F: {
				this.doKey42();
				if (flg.getF() == 1) {
					stp.setStep(HP12CStep.STP_F);
					dsp.setStatus(HP12CDisplay.STATUS_INPUT);
				} else {
					stp.setStep(HP12CStep.STP_NULL);
					dsp.setStatus(HP12CDisplay.STATUS_READY);
				}
				break;
			}
			case KEY_G: {
				this.doKey43();
				if (flg.getG() == 1) {
					stp.setStep(HP12CStep.STP_G);
					dsp.setStatus(HP12CDisplay.STATUS_INPUT);
				} else {
					stp.setStep(HP12CStep.STP_NULL);
					dsp.setStatus(HP12CDisplay.STATUS_READY);
				}
				break;
			}
			case KEY_STO: {
				this.doKey44();
				if (flg.getSto() > 0) {
					stp.setStep(HP12CStep.STP_STO);
					dsp.setStatus(HP12CDisplay.STATUS_INPUT);
				} else {
					stp.setStep(HP12CStep.STP_NULL);
					dsp.setStatus(HP12CDisplay.STATUS_READY);
				}
				break;
			}
			case KEY_RCL: {
				this.doKey45();
				if (flg.getRcl() > 0) {
					stp.setStep(HP12CStep.STP_RCL);
					dsp.setStatus(HP12CDisplay.STATUS_INPUT);
				} else {
					stp.setStep(HP12CStep.STP_NULL);
					dsp.setStatus(HP12CDisplay.STATUS_READY);
				}
				break;
			}
			case KEY_ROLL: {
				this.doKey33();

				if (flg.getGto() > 0) {
					stp.setStep(HP12CStep.STP_G_ROLL);
					dsp.setStatus(HP12CDisplay.STATUS_INPUT);
				} else {
					dsp.setStatus(HP12CDisplay.STATUS_READY);
				}

				break;
			}
			case KEY_SST: {
				if (flg.getF() == 1) {
					stp = new HP12CStep(HP12CStep.STP_F_SST);
					putStep(stp);
					flg.toggleF();
					dsp.setStatus(HP12CDisplay.STATUS_READY);
				} else if (flg.getG() == 1) {
					if (!prg.back())
						prg.setCurrentIndex(prg.getSize() - 1);
					dsp.setStatus(HP12CDisplay.STATUS_READY);
					flg.toggleG();
				} else {
					if (!prg.next())
						prg.setCurrentIndex(0);
					dsp.setStatus(HP12CDisplay.STATUS_READY);
				}

				break;
			}
			default: {
				if (flg.getF() == 1) {
					if (k.getCode() == HP12CKeys.KEY_ENTER.getCode()) {
						doKey42();
						stp.setStep(HP12CStep.STP_NULL);
						dsp.setStatus(HP12CDisplay.STATUS_READY);
					} else if (k.getCode() == HP12CKeys.KEY_RS.getCode()) {
						this.doKey31();
					} else {
						stp.setModifier(HP12CKeys.KEY_F.getCode());
						stp.setKey(k.getCode());
						putStep(stp);
						flg.toggleF();
						dsp.setStatus(HP12CDisplay.STATUS_READY);
					}
				} else if (flg.getG() == 1) {
					if (k.getCode() == HP12CKeys.KEY_9.getCode()) {
						doKey43();
						stp.setStep(HP12CStep.STP_NULL);
						dsp.setStatus(HP12CDisplay.STATUS_READY);
					} else {
						stp.setModifier(HP12CKeys.KEY_G.getCode());
						stp.setKey(k.getCode());
						putStep(stp);
						flg.toggleG();
						dsp.setStatus(HP12CDisplay.STATUS_READY);
					}
				} else if (flg.getSto() > 0) {
					if (k.getCode() <= HP12CKeys.KEY_9.getCode()) {
						this.stoInput(k.getCode());
					} else if (k.getCode() == HP12CKeys.KEY_DOT.getCode()) {
						this.stoInput(HP12CKeys.KEY_NULL.getCode());
					} else if (k.getCode() == HP12CKeys.KEY_N.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_N.getCode());
						flg.setSto(0);
					} else if (k.getCode() == HP12CKeys.KEY_I.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_I.getCode());
						flg.setSto(0);
					} else if (k.getCode() == HP12CKeys.KEY_PV.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_PV.getCode());
						flg.setSto(0);
					} else if (k.getCode() == HP12CKeys.KEY_PMT.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_PMT.getCode());
						flg.setSto(0);
					} else if (k.getCode() == HP12CKeys.KEY_FV.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_FV.getCode());
						flg.setSto(0);
					} else if (k.getCode() == HP12CKeys.KEY_SUM.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_SUM.getCode());
					} else if (k.getCode() == HP12CKeys.KEY_SUB.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_SUB.getCode());
					} else if (k.getCode() == HP12CKeys.KEY_MUL.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_MUL.getCode());
					} else if (k.getCode() == HP12CKeys.KEY_DIV.getCode()) {
						stp.setModifier(HP12CKeys.KEY_STO.getCode());
						stp.setKey(HP12CKeys.KEY_DIV.getCode());
					}

					if (flg.getSto() == 0) {
						putStep(stp);
						dsp.setStatus(HP12CDisplay.STATUS_READY);
					}
				} else if (flg.getRcl() > 0) {
					if (k.getCode() <= HP12CKeys.KEY_9.getCode()) {
						this.rclInput(k.getCode());
					} else if (k.getCode() == HP12CKeys.KEY_DOT.getCode()) {
						this.rclInput(HP12CKeys.KEY_NULL.getCode());
					} else if (k.getCode() == HP12CKeys.KEY_N.getCode()) {
						stp.setModifier(HP12CKeys.KEY_RCL.getCode());
						stp.setKey(HP12CKeys.KEY_N.getCode());
						flg.setRcl(0);
					} else if (k.getCode() == HP12CKeys.KEY_I.getCode()) {
						stp.setModifier(HP12CKeys.KEY_RCL.getCode());
						stp.setKey(HP12CKeys.KEY_I.getCode());
						flg.setRcl(0);
					} else if (k.getCode() == HP12CKeys.KEY_PV.getCode()) {
						stp.setModifier(HP12CKeys.KEY_RCL.getCode());
						stp.setKey(HP12CKeys.KEY_PV.getCode());
						flg.setRcl(0);
					} else if (k.getCode() == HP12CKeys.KEY_PMT.getCode()) {
						stp.setModifier(HP12CKeys.KEY_RCL.getCode());
						stp.setKey(HP12CKeys.KEY_PMT.getCode());
						flg.setRcl(0);
					} else if (k.getCode() == HP12CKeys.KEY_FV.getCode()) {
						stp.setModifier(HP12CKeys.KEY_RCL.getCode());
						stp.setKey(HP12CKeys.KEY_FV.getCode());
						flg.setRcl(0);
					}

					if (flg.getRcl() == 0) {
						putStep(stp);
						dsp.setStatus(HP12CDisplay.STATUS_READY);
					}
				} else if (flg.getGto() > 0) {
					if (k.getCode() <= HP12CKeys.KEY_9.getCode())
						this.gtoInput(k.getCode());
					else if (k.getCode() == HP12CKeys.KEY_DOT.getCode())
						flg.toggleWild();

					if (flg.getGto() == 0) {
						if (flg.getWild() == 1) {
							prg.setCurrentIndex(stp.getComplement());
							flg.toggleWild();
							dsp.setStatus(HP12CDisplay.STATUS_READY);
						} else {
							putStep(stp);
							dsp.setStatus(HP12CDisplay.STATUS_READY);
						}
					}
				} else {
					stp = new HP12CStep();
					stp.setKey(k.getCode());
					putStep(stp);
					dsp.setStatus(HP12CDisplay.STATUS_READY);
				}
			}
			}

			if (dsp.getStatus() == HP12CDisplay.STATUS_READY) {
				stp.setStep((HP12CStep) prg.get());
				dsp.inputProgramStep(prg.getCurrentIndex(), stp);
			}

		} catch (Exception e) {
			// TODO
		}

	}

	public void executeStep(HP12CStep stp) {

		// if the current instruction has a null key,
		// goes to line zero and stop executing
		// It acts the same way as [g][gto]000.
		// So it is not necessary to store that instruction
		// in each single line when [f][prg] is keyed.
		if (stp.getKey() == HP12CKeys.KEY_NULL.getCode()) {
			this.stopProgram();
		}
		// goes to line number ZERO and stops execution.
		else if (stp.getModifier() == HP12CKeys.KEY_G.getCode()
				&& stp.getKey() == HP12CKeys.KEY_ROLL.getCode()
				&& stp.getComplement() == HP12CKeys.KEY_0.getCode()) {

			this.stopProgram();
		}
		// goes to line number specified by the instruction [g][gto]xxx.
		else if (stp.getModifier() == HP12CKeys.KEY_G.getCode()
				&& stp.getKey() == HP12CKeys.KEY_ROLL.getCode()) {

			prg.setCurrentIndex(stp.getComplement());
		} else if (stp.getModifier() == HP12CKeys.KEY_G.getCode()
				&& stp.getKey() == HP12CKeys.KEY_XY.getCode()) {

			if (stk.get(0) <= stk.get(1)) {
				this.prg.next();
			} else {
				this.prg.next();
				this.prg.next();
			}
		} else if (stp.getModifier() == HP12CKeys.KEY_G.getCode()
				&& stp.getKey() == HP12CKeys.KEY_CLX.getCode()) {

			if (stk.get(0) == 0.0) {
				this.prg.next();
			} else {
				this.prg.next();
				this.prg.next();
			}
		} else if (stp.getKey() == HP12CKeys.KEY_STO.getCode()) {
			mem.set(stp.getComplement(), stk.top());
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			this.prg.next();
		} else if (stp.getKey() == HP12CKeys.KEY_RCL.getCode()) {
			this.setX(mem.get(stp.getComplement()));
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			this.prg.next();
		} else {
			if (stp.getModifier() > (-1)) {
				keyReleased(HP12CKeys.getKey(stp.getModifier()));
			}
			if (stp.getKey() > (-1)) {
				keyReleased(HP12CKeys.getKey(stp.getKey()));
			}
			if (stp.getComplement() > (-1)) {
				keyReleased(HP12CKeys.getKey(stp.getComplement()));
			}
			this.prg.next();
		}

	}

	public void executeSingleStep() {

		// Erases old content in display
		// and shows the message "running"
		showDisplayMessage("");
		showDisplayMessage("running");
		getDisplay().setLock(false);

		if (prg.getCurrentIndex() == 0)
			this.prg.next();

		this.executeStep((HP12CStep) this.prg.get());

		if (prg.getCurrentIndex() == prg.getSize() - 1) {
			this.stopProgram();
		}
	}

	public void executeProgram() {

		this.runner = null;
		this.runner = new HP12CProgramRunner(this);
		this.runner.start();

	}

	public void stopProgram() {
		prg.setCurrentIndex(0);
		flg.setRun(0);
		dsp.setLock(false);
		controller.getWindow().updateDisplay();
	}

	public HP12CStep getStep() {
		return this.stp;
	}

	public HP12CStep getCurrentProgramStep() {
		return (HP12CStep) this.prg.get();
	}

	public void setStep(HP12CStep stp) {
		this.stp = new HP12CStep(stp);
	}

	public void setCurrentProgramStep(HP12CStep stp) {
		this.prg.set(new HP12CStep(stp));
	}

	public void putStep(HP12CStep stp) {
		System.out.println("putStep: " + stp);
		this.prg.put(new HP12CStep(stp));
	}

	private void clearFgsr() {
		if (flg.getF() == 1) {
			flg.toggleF();
		}
		if (flg.getG() == 1) {
			flg.toggleG();
		}
		if (flg.getSto() > 0) {
			flg.toggleSto();
		}
		if (flg.getRcl() > 0) {
			flg.toggleRcl();
		}
	}

	protected void doKey00() throws HP12CInputException {
		if (flg.getF() == 1) {
			dsp.setPrecision(0);

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_0);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {

			// Temp variable
			tmp = new double[2];

			// Tests for errors
			HP12CFunctions.mean(mem.getR2(), mem.getR1());
			HP12CFunctions.mean(mem.getR4(), mem.getR1());

			// Calculates mean values
			tmp[0] = HP12CFunctions.mean(mem.getR2(), mem.getR1());
			tmp[1] = HP12CFunctions.mean(mem.getR4(), mem.getR1());

			// Inserts y and x results
			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_0);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getSto() > 0) {
			this.stoInput(0);
		} else if (flg.getRcl() > 0) {
			this.rclInput(0);
		} else if (flg.getGto() > 0) {
			this.gtoInput(0);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('0');
			stp.setStep(HP12CStep.STP_0);
		}
	}

	protected void doKey01() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(1);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_1);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {

			// Temporary variables
			tmp = new double[2];

			tmp[0] = HP12CFunctions.xLinearEstimation(mem.getR2(), mem.getR4(),
					mem.getR6(), mem.getR3(), mem.getR1(), stk.top());
			tmp[1] = HP12CFunctions.rLinearEstimation(mem.getR2(), mem.getR4(),
					mem.getR6(), mem.getR3(), mem.getR5(), mem.getR1());

			stk.setLastX();

			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_1);
			dsp.setStatus(HP12CDisplay.STATUS_READY);

		} else if (flg.getSto() > 0) {
			this.stoInput(1);
		} else if (flg.getRcl() > 0) {
			this.rclInput(1);
		} else if (flg.getGto() > 0) {
			this.gtoInput(1);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('1');
			stp.setStep(HP12CStep.STP_1);
		}
	}

	protected void doKey02() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(2);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_2);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			// Temporary variables
			tmp = new double[2];

			tmp[0] = HP12CFunctions.yLinearEstimation(mem.getR2(), mem.getR4(),
					mem.getR6(), mem.getR3(), mem.getR1(), stk.top());
			tmp[1] = HP12CFunctions.rLinearEstimation(mem.getR2(), mem.getR4(),
					mem.getR6(), mem.getR3(), mem.getR5(), mem.getR1());

			stk.setLastX();

			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_2);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getSto() > 0) {
			this.stoInput(2);
		} else if (flg.getRcl() > 0) {
			this.rclInput(2);
		} else if (flg.getGto() > 0) {
			this.gtoInput(2);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('2');
			stp.setStep(HP12CStep.STP_2);
		}
	}

	protected void doKey03() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(3);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_3);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			HP12CFunctions.fat(getX());
			stk.setLastX();
			setX(HP12CFunctions.fat(stk.pop()));

			flg.toggleG();
			stp.setStep(HP12CStep.STP_F_3);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			this.stoInput(3);
		} else if (flg.getRcl() > 0) {
			this.rclInput(3);
		} else if (flg.getGto() > 0) {
			this.gtoInput(3);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('3');
			stp.setStep(HP12CStep.STP_3);
		}
	}

	protected void doKey04() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(4);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_4);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setDmy(1);
			this.flg.toggleG();
			stp.setStep(HP12CStep.STP_G_4);
		} else if (flg.getSto() > 0) {
			this.stoInput(4);
		} else if (flg.getRcl() > 0) {
			this.rclInput(4);
		} else if (flg.getGto() > 0) {
			this.gtoInput(4);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('4');
			stp.setStep(HP12CStep.STP_4);
		}
	}

	protected void doKey05() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(5);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_5);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setDmy(0);
			this.flg.toggleG();
			stp.setStep(HP12CStep.STP_G_5);
		} else if (flg.getSto() > 0) {
			this.stoInput(5);
		} else if (flg.getRcl() > 0) {
			this.rclInput(5);
		} else if (flg.getGto() > 0) {
			this.gtoInput(5);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('5');
			stp.setStep(HP12CStep.STP_5);
		}
	}

	protected void doKey06() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(6);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_6);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			// Temporary variables
			tmp = new double[2];

			// Calculates weighted averages for y and x.
			tmp[0] = HP12CFunctions.weightedAverage(mem.getR6(), mem.getR2());
			tmp[1] = HP12CFunctions.weightedAverage(mem.getR6(), mem.getR4());

			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_6);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getSto() > 0) {
			this.stoInput(6);
		} else if (flg.getRcl() > 0) {
			this.rclInput(6);
		} else if (flg.getGto() > 0) {
			this.gtoInput(6);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('6');
			stp.setStep(HP12CStep.STP_6);
		}
	}

	protected void doKey07() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(7);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_7);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setBegin(1);
			this.flg.toggleG();
			stp.setStep(HP12CStep.STP_G_7);
		} else if (flg.getSto() > 0) {
			this.stoInput(7);
		} else if (flg.getRcl() > 0) {
			this.rclInput(7);
		} else if (flg.getGto() > 0) {
			this.gtoInput(7);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('7');

			stp.setStep(HP12CStep.STP_7);
		}
	}

	protected void doKey08() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(8);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_8);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setBegin(0);
			this.flg.toggleG();
			stp.setStep(HP12CStep.STP_G_8);
		} else if (flg.getSto() > 0) {
			this.stoInput(8);
		} else if (flg.getRcl() > 0) {
			this.rclInput(8);
		} else if (flg.getGto() > 0) {
			this.gtoInput(8);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('8');

			stp.setStep(HP12CStep.STP_8);
		}
	}

	protected void doKey09() throws HP12CInputException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(9);
			this.flg.toggleF();
			stp.setStep(HP12CStep.STP_F_9);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_9);
		} else if (flg.getSto() > 0) {
			this.stoInput(9);
		} else if (flg.getRcl() > 0) {
			this.rclInput(9);
		} else if (flg.getGto() > 0) {
			this.gtoInput(9);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('9');
			stp.setStep(HP12CStep.STP_9);
		}
	}

	protected void doKey10() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setStep(HP12CStep.STP_STO_DIV);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.div(stk.get(0), stk.get(1));
			stk.setLastX();
			stk.put(HP12CFunctions.div(stk.pop(), stk.pop()));
			stp.setStep(HP12CStep.STP_DIV);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey11() throws HP12CInputException {
		if (flg.getF() == 1) {
			HP12CFunctions.amortization(stk.top(), fin.getN(), fin.getI(),
					fin.getPv(), fin.getPmt(), flg.getBegin(),
					dsp.getPrecision());
			tmp = HP12CFunctions.amortization(stk.top(), fin.getN(),
					fin.getI(), fin.getPv(), fin.getPmt(), flg.getBegin(),
					dsp.getPrecision());

			// For details, see HP12CFunctions.amortization(...)
			stk.put(tmp[0]);
			stk.put(tmp[1]);
			stk.put(tmp[2]);
			fin.setPv(tmp[3]);
			fin.setN(tmp[4]);

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_N);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			HP12CFunctions.mul(stk.top(), 12.0);
			stk.put(HP12CFunctions.mul(stk.pop(), 12.0));
			fin.setN(stk.top());
			stp.setStep(HP12CStep.STP_G_N);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			fin.setN(stk.top());
			flg.setSto(0);
			stp.setStep(HP12CStep.STP_STO_N);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getN());
			flg.setRcl(0);
			stp.setStep(HP12CStep.STP_RCL_N);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else {
			if ((dsp.getStatus() == HP12CDisplay.STATUS_READY)
					|| (dsp.getStatus() == HP12CDisplay.STATUS_OUTPUT2)) {

				HP12CFunctions.period((fin.getI()), fin.getPv(), fin.getPmt(),
						fin.getFv(), flg.getBegin(), flg.getC());
				stk.set(0,
						HP12CFunctions.period((fin.getI()), fin.getPv(),
								fin.getPmt(), fin.getFv(), flg.getBegin(),
								flg.getC()));

				// Here odd period check have to be done AFTER the calculus
				// if(checkOddPeriod()) executeOddPeriodTasks();

				fin.setN(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT2);

			} else {
				fin.setN(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_READY);
			}
			stp.setStep(HP12CStep.STP_N);
		}
	}

	protected void doKey12() throws HP12CInputException {
		if (flg.getF() == 1) {

			tmp = new double[2];

			HP12CFunctions.simpleInterest(fin.getN() / 360.0, fin.getI(),
					fin.getPv());
			tmp[0] = HP12CFunctions.simpleInterest(fin.getN() / 360.0,
					fin.getI(), fin.getPv());

			HP12CFunctions.simpleInterest(fin.getN() / 365.0, fin.getI(),
					fin.getPv());
			tmp[1] = HP12CFunctions.simpleInterest(fin.getN() / 365.0,
					fin.getI(), fin.getPv());

			stk.set(0, tmp[0]);
			stk.set(1, (-fin.getPv()));
			stk.set(2, tmp[1]);

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_I);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);

		} else if (flg.getG() == 1) {
			HP12CFunctions.div(12.0, stk.top());
			stk.put(HP12CFunctions.div(12.0, stk.pop()));
			fin.setI(stk.top());
			stp.setStep(HP12CStep.STP_G_I);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			fin.setI(stk.top());
			flg.setSto(0);
			stp.setStep(HP12CStep.STP_STO_I);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getI());
			flg.setRcl(0);
			stp.setStep(HP12CStep.STP_RCL_I);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else {
			if ((dsp.getStatus() == HP12CDisplay.STATUS_READY)
					|| (dsp.getStatus() == HP12CDisplay.STATUS_OUTPUT2)) {

				HP12CFunctions.rate(fin.getN(), fin.getPv(), fin.getPmt(),
						fin.getFv(), flg.getBegin(), flg.getC());
				stk.set(0,
						HP12CFunctions.rate(fin.getN(), fin.getPv(),
								fin.getPmt(), fin.getFv(), flg.getBegin(),
								flg.getC()));

				// Here odd period check have to be done AFTER the calculus
				if (checkOddPeriod())
					executeOddPeriodTasks();

				fin.setI(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT2);
			} else {
				fin.setI(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_READY);
			}
			stp.setStep(HP12CStep.STP_I);
		}
	}

	protected void doKey13() throws HP12CInputException {
		if (flg.getF() == 1) {

			HP12CFunctions.npv(fin.getN(), fin.getI(), mem.getArray());
			stk.set(0,
					HP12CFunctions.npv(fin.getN(), fin.getI(), mem.getArray()));

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_PV);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			mem.set(0, stk.top());

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_PV);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setPv(stk.top());
			flg.setSto(0);
			stp.setStep(HP12CStep.STP_STO_PV);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getPv());
			flg.setRcl(0);
			stp.setStep(HP12CStep.STP_RCL_PV);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else {
			if ((dsp.getStatus() == HP12CDisplay.STATUS_READY)
					|| (dsp.getStatus() == HP12CDisplay.STATUS_OUTPUT2)) {

				// Here odd period check should not be done
				// if(checkOddPeriod()) executeOddPeriodTasks();

				HP12CFunctions.presentValue(fin.getN(), (fin.getI()),
						fin.getPmt(), fin.getFv(), flg.getBegin(), flg.getC());
				stk.set(0, HP12CFunctions.presentValue(fin.getN(),
						(fin.getI()), fin.getPmt(), fin.getFv(),
						flg.getBegin(), flg.getC()));

				fin.setPv(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT2);
			} else {
				fin.setPv(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_READY);
			}
			stp.setStep(HP12CStep.STP_PV);
		}
	}

	protected void doKey14() throws HP12CInputException {
		if (flg.getF() == 1) {
			HP12CFunctions.round(stk.top(), dsp.getPrecision());
			stk.setLastX();
			stk.put(HP12CFunctions.round(stk.pop(), dsp.getPrecision()));

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_PMT);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			mem.put(stk.top(), 1.0);
			fin.setN(fin.getN() + 1);
			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_PMT);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setPmt(stk.top());
			flg.setSto(0);
			stp.setStep(HP12CStep.STP_STO_PMT);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getPmt());
			flg.setRcl(0);
			stp.setStep(HP12CStep.STP_RCL_PMT);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else {
			if ((dsp.getStatus() == HP12CDisplay.STATUS_READY)
					|| (dsp.getStatus() == HP12CDisplay.STATUS_OUTPUT2)) {

				// Here odd period check should be done BEFORE the calculus
				if (checkOddPeriod())
					executeOddPeriodTasks();

				HP12CFunctions.pricePayment(fin.getN(), (fin.getI()),
						fin.getPv(), fin.getFv(), flg.getBegin(), flg.getC());
				stk.set(0, HP12CFunctions.pricePayment(fin.getN(),
						(fin.getI()), fin.getPv(), fin.getFv(), flg.getBegin(),
						flg.getC()));

				fin.setPmt(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT2);
			} else {
				fin.setPmt(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_READY);
			}
			stp.setStep(HP12CStep.STP_PMT);
		}
	}

	protected void doKey15() throws HP12CInputException {
		if (flg.getF() == 1) {
			HP12CFunctions.irr(fin.getN(), mem.getArray());
			stk.set(0, HP12CFunctions.irr(fin.getN(), mem.getArray()));

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_FV);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			mem.setTimes(mem.getCurrentIndex(), stk.top());
			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_FV);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setFv(stk.top());
			flg.setSto(0);
			stp.setStep(HP12CStep.STP_STO_FV);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getFv());
			flg.setRcl(0);
			stp.setStep(HP12CStep.STP_RCL_FV);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else {
			if ((dsp.getStatus() == HP12CDisplay.STATUS_READY)
					|| (dsp.getStatus() == HP12CDisplay.STATUS_OUTPUT2)) {

				// Here odd period check should be done BEFORE the calculus
				if (checkOddPeriod())
					executeOddPeriodTasks();

				HP12CFunctions.futureValue(fin.getN(), (fin.getI()),
						fin.getPv(), fin.getPmt(), flg.getBegin(), flg.getC());
				stk.set(0, HP12CFunctions.futureValue(fin.getN(), (fin.getI()),
						fin.getPv(), fin.getPmt(), flg.getBegin(), flg.getC()));

				fin.setFv(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT2);
			} else {
				fin.setFv(stk.top());
				dsp.setStatus(HP12CDisplay.STATUS_READY);
			}
			stp.setStep(HP12CStep.STP_FV);
		}
	}

	protected void doKey16() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {

			// Temporary arrays
			tmp = new double[2];
			dt = new Date[1];

			// Test for errors
			tmp[0] = stk.top();
			tmp[1] = stk.get(1);
			dt[0] = HP12CFunctions.getDate(tmp[1], (flg.getDmy()));
			HP12CFunctions.addDays(dt[0], (int) tmp[0], (flg.getDmy()));

			stk.setLastX();

			// Calculate future date
			tmp[0] = stk.pop();
			tmp[1] = stk.pop();
			dt[0] = HP12CFunctions.getDate(tmp[1], (flg.getDmy()));
			stk.put(HP12CFunctions.addDays(dt[0], (int) tmp[0], (flg.getDmy())));

			// Calculate week day and show it.
			dt[0] = HP12CFunctions.getDate(stk.top(), (flg.getDmy()));
			dsp.setValue(stk.top());
			stp.setStep(HP12CStep.STP_G_CHS);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
			dsp.setMessage((dsp.getString() + "          ").substring(1, 11)
					+ (int) HP12CFunctions.weekDay(dt[0]));

			flg.toggleG();
			dsp.setLock(true);

		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			if ((dsp.getStatus() != HP12CDisplay.STATUS_INPUT)) {
				stk.put(-(stk.pop()));
			} else {
				this.dsp.inputChar('-');
			}
			stp.setStep(HP12CStep.STP_CHS);
		}
	}

	protected void doKey20() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			HP12CFunctions.pow(2.0, stk.top());
			stk.setLastX();
			stk.put(HP12CFunctions.pow(2.0, stk.pop()));

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_MUL);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			stp.setStep(HP12CStep.STP_STO_MUL);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.mul(stk.top(), stk.get(1));
			stk.setLastX();
			stk.put(HP12CFunctions.mul(stk.pop(), stk.pop()));
			stp.setStep(HP12CStep.STP_MUL);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);

		}
	}

	protected void doKey21() throws HP12CInputException {
		if (flg.getF() == 1) {

			tmp = new double[2];
			dt = new Date[2];

			HP12CFunctions.bondPrice(fin.getI(), fin.getPmt(), dt[1], dt[0]);
			tmp = HP12CFunctions.bondPrice(fin.getI(), fin.getPmt(), dt[1],
					dt[0]);

			stk.put(tmp[1]);
			stk.put(tmp[0]);

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_POW);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			HP12CFunctions.sqrt(stk.top());
			stk.setLastX();
			stk.put(HP12CFunctions.sqrt(stk.pop()));

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_POW);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.pow(stk.top(), stk.get(1));
			stk.setLastX();
			stk.put(HP12CFunctions.pow(stk.pop(), stk.pop()));
			stp.setStep(HP12CStep.STP_POW);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey22() throws HP12CInputException {
		if (flg.getF() == 1) {

			// Shows a message: "Not Available Sorry"
			this.showDisplayMessage(" N.A. Sorry ");
			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_RECIPROCAL);
			dsp.setStatus(HP12CDisplay.STATUS_READY);

			// TODO: This piece of code is ready to work
			// but the function bondYield is not yet.
			// A message like "N/A Sorry" will be shown in the
			// display until I can rewrite that function again.
			/*
			 * dt = new Date[2];
			 * 
			 * HP12CFunctions.bondYield(fin.getPv(), fin.getPmt(), dt[1],
			 * dt[0]); stk.put(HP12CFunctions.bondYield(fin.getPv(),
			 * fin.getPmt(), dt[1], dt[0]));
			 * 
			 * flg.toggleF(); stp.setStep(HP12CStep.STP_F_RECIPROCAL);
			 * dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			 */

		} else if (flg.getG() == 1) {
			HP12CFunctions.ePowerX(stk.top());
			stk.setLastX();
			stk.put(HP12CFunctions.ePowerX(stk.pop()));

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_RECIPROCAL);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.reciprocal(stk.top());
			stk.setLastX();
			stk.put(HP12CFunctions.reciprocal(stk.pop()));
			stp.setStep(HP12CStep.STP_RECIPROCAL);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey23() throws HP12CInputException {
		if (flg.getF() == 1) {

			tmp = new double[2];

			HP12CFunctions.slDepreciation(fin.getN(), fin.getI(), fin.getPv(),
					fin.getFv(), stk.top());
			tmp = HP12CFunctions.slDepreciation(fin.getN(), fin.getI(),
					fin.getPv(), fin.getFv(), stk.top());

			stk.put(tmp[0]);
			stk.put(tmp[1]);

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_PERC_TOT);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			HP12CFunctions.logN(stk.top());
			stk.setLastX();
			stk.put(HP12CFunctions.logN(stk.pop()));

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_PERC_TOT);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.percentOfTotal(stk.top(), stk.get(1));
			stk.setLastX();
			stk.set(0, HP12CFunctions.percentOfTotal(stk.top(), stk.get(1)));
			stp.setStep(HP12CStep.STP_PERC_TOT);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey24() throws HP12CInputException {
		if (flg.getF() == 1) {
			tmp = new double[2];

			HP12CFunctions.soydDepreciation(fin.getN(), fin.getI(),
					fin.getPv(), fin.getFv(), stk.top());
			tmp = HP12CFunctions.soydDepreciation(fin.getN(), fin.getI(),
					fin.getPv(), fin.getFv(), stk.top());

			stk.put(tmp[0]);
			stk.put(tmp[1]);

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_PERC_DELTA);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.setLastX();
			stk.set(0, HP12CFunctions.fracPart(this.getX()));
			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_PERC_DELTA);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.percentDifference(stk.top(), stk.get(1));
			stk.setLastX();
			stk.set(0, HP12CFunctions.percentDifference(stk.top(), stk.get(1)));
			stp.setStep(HP12CStep.STP_PERC_DELTA);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey25() throws HP12CInputException {
		if (flg.getF() == 1) {
			tmp = new double[2];

			HP12CFunctions.dbDepreciacion(fin.getN(), fin.getI(), fin.getPv(),
					fin.getFv(), stk.top());
			tmp = HP12CFunctions.dbDepreciacion(fin.getN(), fin.getI(),
					fin.getPv(), fin.getFv(), stk.top());

			stk.put(tmp[0]);
			stk.put(tmp[1]);

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_PERC);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.setLastX();
			stk.set(0, HP12CFunctions.intPart(this.getX()));
			stp.setStep(HP12CStep.STP_G_PERC);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.percent(stk.top(), stk.get(1));
			stk.setLastX();
			stk.set(0, HP12CFunctions.percent(stk.top(), stk.get(1)));
			stp.setStep(HP12CStep.STP_PERC);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey26() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {

			// Temporary arrays
			tmp = new double[4];
			dt = new Date[2];

			// Calculates difference between dates on the basis of a 30-days
			// month
			tmp[0] = stk.top();
			tmp[1] = stk.get(1);
			dt[0] = HP12CFunctions.getDate(tmp[0], (flg.getDmy()));
			dt[1] = HP12CFunctions.getDate(tmp[1], (flg.getDmy()));
			tmp[2] = HP12CFunctions.diffCommercialDates(dt[1], dt[0]);

			stk.setLastX();

			// Calculates difference between dates in actual days
			tmp[0] = stk.pop();
			tmp[1] = stk.pop();
			dt[0] = HP12CFunctions.getDate(tmp[0], (flg.getDmy()));
			dt[1] = HP12CFunctions.getDate(tmp[1], (flg.getDmy()));
			tmp[3] = HP12CFunctions.diffDates(dt[1], dt[0]);

			stk.put(tmp[2]);
			stk.put(tmp[3]);

			flg.toggleG();
			stp.setStep(HP12CStep.STP_F_EEX);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);

		} else if (flg.getSto() > 0) {
			flg.toggleC();
			flg.setSto(0);
			stp.setStep(HP12CStep.STP_G_EEX);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			if (dsp.getValue() != 0.0) {
				if ((dsp.getStatus() != HP12CDisplay.STATUS_INPUT)) {
					dsp.inputChar('1');
				}
				this.dsp.setMode(HP12CDisplay.MODE_EXPONENTIAL);
			}
			stp.setStep(HP12CStep.STP_EEX);
		}
	}

	protected void doKey30() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setStep(HP12CStep.STP_STO_SUB);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.sub(stk.top(), stk.get(1));
			stk.setLastX();
			stk.put(HP12CFunctions.sub(stk.pop(), stk.pop()));
			stp.setStep(HP12CStep.STP_SUB);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey31() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.togglePrgm();

			if (flg.getPrgm() == 1) {
				dsp.inputProgramStep(prg.getCurrentIndex(),
						(HP12CStep) prg.get());
				dsp.setMode(HP12CDisplay.MODE_PROGRAM);
			} else {
				prg.setCurrentIndex(0);
				dsp.setMode(HP12CDisplay.MODE_NORMAL);
			}

			dsp.setStatus(HP12CDisplay.STATUS_READY);
			flg.toggleF();
		} else if (flg.getG() == 1) {

			// TODO: show partial result in display.
			if (flg.getRun() == 1) {
				dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
				this.updateDisplay();
				controller.getWindow().updateDisplay();
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			if (dsp.getMode() == HP12CDisplay.MODE_NORMAL) {
				flg.toggleRun();
				if (flg.getRun() == 1) {
					executeProgram();
				} else {
					flg.setRun(0);
					dsp.setLock(false);
					controller.getWindow().updateDisplay();
				}
			}
		}
	}

	protected void doKey32() throws HP12CInputException {
		if (flg.getF() == 1) {
			mem.clearStats();
			stk.clear();
			flg.toggleF();
		} else if (flg.getG() == 1) {
			// Goes back until get to index 0,
			// then goes to the last index.
			if (!prg.back())
				prg.setCurrentIndex(0);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			this.executeSingleStep();
		}

	}

	protected void doKey33() throws HP12CInputException {
		if (flg.getF() == 1) {
			if (dsp.getMode() == HP12CDisplay.MODE_NORMAL)
				prg.setCurrentIndex(0);
			else if (dsp.getMode() == HP12CDisplay.MODE_PROGRAM)
				prg.clear();
			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_ROLL);
		} else if (flg.getG() == 1) {
			flg.toggleGto();
			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_ROLL);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.rollDown();
			stp.setStep(HP12CStep.STP_ROLL);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey34() throws HP12CInputException {
		if (flg.getF() == 1) {
			fin.clear();
			stp.setStep(HP12CStep.STP_F_XY);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
			flg.toggleF();
		} else if (flg.getG() == 1) {
			stk.lowerXY();
			stp.setStep(HP12CStep.STP_G_XY);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.swapXY();
			stp.setStep(HP12CStep.STP_XY);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey35() throws HP12CInputException {
		if (flg.getF() == 1) {
			mem.clear();
			fin.clear();
			stk.clear();
			stk.clearLastX();

			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_CLX);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.set(0, 0.0);
			stp.setStep(HP12CStep.STP_CLX);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		}
	}

	protected void doKey36() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
			stp.setStep(HP12CStep.STP_F_ENTER);
		} else if (flg.getG() == 1) {

			this.setX(stk.getLastX());

			stp.setStep(HP12CStep.STP_G_ENTER);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.put(dsp.getValue());
			stp.setStep(HP12CStep.STP_ENTER);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		}
	}

	protected void doKey40() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setStep(HP12CStep.STP_STO_SUM);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			HP12CFunctions.sum(stk.top(), stk.get(1));
			stk.setLastX();
			stk.put(HP12CFunctions.sum(stk.pop(), stk.pop()));
			stp.setStep(HP12CStep.STP_SUM);
			dsp.setStatus(HP12CDisplay.STATUS_OUTPUT);
		}
	}

	protected void doKey41() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else if (flg.getOn() == 1) {
			flg.toggleOn();
		} else {
			flg.toggleOn();
		}
	}

	protected void doKey42() throws HP12CInputException {

		if (flg.getF() > 0) {
			this.clearFgsr();
		} else {
			this.clearFgsr();
			flg.toggleF();
		}
	}

	protected void doKey43() throws HP12CInputException {

		if (flg.getG() > 0) {
			this.clearFgsr();
		} else {
			this.clearFgsr();
			flg.toggleG();
		}

	}

	protected void doKey44() throws HP12CInputException {

		if (flg.getSto() > 0) {
			this.clearFgsr();
		} else {
			this.clearFgsr();
			flg.toggleSto();
		}
	}

	protected void doKey45() throws HP12CInputException {

		if (flg.getRcl() > 0) {
			this.clearFgsr();
		} else {
			this.clearFgsr();
			flg.toggleRcl();
		}
	}

	protected void doKey48() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			// Temp variable
			tmp = new double[2];

			// Tests for errors
			HP12CFunctions.standardDeviation(mem.getR2(), mem.getR3(),
					mem.getR1());
			HP12CFunctions.standardDeviation(mem.getR4(), mem.getR5(),
					mem.getR1());

			// Calculates mean values
			tmp[0] = HP12CFunctions.standardDeviation(mem.getR2(), mem.getR3(),
					mem.getR1());
			tmp[1] = HP12CFunctions.standardDeviation(mem.getR4(), mem.getR5(),
					mem.getR1());

			// Inserts y and x results
			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_DOT);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getSto() > 0) {
			this.stoInput(-1);
		} else if (flg.getRcl() > 0) {
			this.rclInput(-1);
		} else if (flg.getGto() > 0) {
			this.gtoInput(-1);
		} else if (flg.getOn() == 1) {
			dsp.toggleComma();
			flg.toggleOn();
		} else {
			this.shiftUpIfOutputStatus();

			this.dsp.inputChar('.');
			stp.setStep(HP12CStep.STP_DOT);
		}
	}

	protected void doKey49() throws HP12CInputException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			mem.subStats(stk.top(), stk.get(1));
			stk.setLastX();
			stk.set(0, mem.getR1());
			flg.toggleG();
			stp.setStep(HP12CStep.STP_G_TOT);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			mem.sumStats(stk.top(), stk.get(1));
			stk.setLastX();
			stk.set(0, mem.getR1());
			stp.setStep(HP12CStep.STP_TOT);
			dsp.setStatus(HP12CDisplay.STATUS_READY);
		}
	}

}
