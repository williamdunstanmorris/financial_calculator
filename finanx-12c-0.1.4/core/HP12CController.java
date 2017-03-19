package core;

import java.awt.Point;
import java.util.Hashtable;

import javax.swing.JTextField;

import persistence.HP12CConfigurationDAO;
import persistence.Item;
import persistence.ItemList;
import persistence.LanguageDAO;
import persistence.MemoryDAO;
import persistence.Skin;
import persistence.SkinDAO;
import persistence.StringList;
import memory.HP12CFinanceMemory;
import memory.HP12CGeneralMemory;
import memory.HP12CProgramMemory;
import memory.HP12CStack;
import tools.HP12CTool;
import view.HP12CWindow;

public class HP12CController {

	protected HP12CExecutor executor;
	protected HP12CWindow window;

	protected MemoryDAO memd;

	protected HP12CConfigurationDAO cfgd;
	protected HP12CConfiguration cfg;

	protected SkinDAO sknd;
	protected ItemList sknl;
	protected Skin skin;
	protected Item skinItems[];

	protected LanguageDAO lngd;
	protected StringList strl;

	protected Hashtable<String, HP12CTool> ToolsHash;

	protected String tmp[];
	protected String cmd, prm;

	public HP12CController() {
		this.init();
	}

	public void init() {

		this.loadConfigs();
		this.loadMemory();

		this.initWindow();
		this.initExecutor();

		this.window.updateDisplay();

		this.welcomeMessage();
	}

	public void initWindow() {
		this.window = new HP12CWindow(cfg);
		this.window.setController(this);
		this.setWindowConfigs();

		this.setUpMouseListeners();
		this.setUpMenuListeners();
		this.setUpKeyListeners();
		this.setUpWindowListeners();
	}

	public void initExecutor() {
		this.executor = new HP12CExecutor();
		this.executor.setController(this);
		this.setExecutorConfigs();
		this.setExecutorMemory();
	}

	public void updateWindow() {
		this.window.destruct();
		this.initWindow();
		this.window.updateDisplay();
	}

	private void loadConfigs() {
		// Loading configurations
		cfgd = new HP12CConfigurationDAO();
		cfg = cfgd.getConfiguration();
	}

	public void saveConfigs() {

		// Window position
		Point point = window.getWindowLocation();
		this.cfg.setXPos(point.x);
		this.cfg.setYPos(point.y);
		
		// Flags and display
		this.cfg.setAlg(executor.getFlags().getAlg());
		this.cfg.setBeg(executor.getFlags().getBegin());
		this.cfg.setC(executor.getFlags().getC());
		this.cfg.setCom(executor.getDisplay().getComma() ? 1 : 0);
		this.cfg.setDmy(executor.getFlags().getDmy());
		this.cfg.setFix(executor.getDisplay().getPrecision());
		this.cfg.setMode(executor.getDisplay().getMode());
		
		this.cfgd.save(cfg);
	}

	public void setWindowConfigs() {
		this.window.setConfigs(cfg);
	}

	public void setExecutorConfigs() {
		this.executor.setConfigs(cfg);
	}

	public void loadMemory() {

		this.memd = new MemoryDAO(this.cfg);

	}

	public void saveMemory() {

		this.memd.setStack((HP12CStack) this.executor.getStack());
		this.memd.setFinanceMemory((HP12CFinanceMemory) this.executor
				.getFinanceMemory());
		this.memd.setGegeralMemory((HP12CGeneralMemory) this.executor
				.getGeneralMemory());
		this.memd.setProgramMemory((HP12CProgramMemory) this.executor
				.getProgramMemory());

		this.memd.save();

	}

	public void setExecutorMemory() {
		this.executor.setStack(memd.getStack());
		this.executor.setFinanceMemory(memd.getFinanceMemory());
		this.executor.setGeneralMemory(memd.getGeneralMemory());
		this.executor.setProgramMemory(memd.getProgramMemory());

		this.executor.updateDisplay();
	}

	public void keyPressed(char key) {
		this.keyPressed(getKey(key));
	}

	public void keyReleased(char key) {
		this.keyReleased(this.getKey(key));
	}

	private KeysEnumInterface getKey(char chr) {
		
		if (this.cfg.getCode(chr) == HP12CKeys.KEY_0.getCode())
			return HP12CKeys.KEY_0;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_1.getCode())
			return HP12CKeys.KEY_1;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_2.getCode())
			return HP12CKeys.KEY_2;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_3.getCode())
			return HP12CKeys.KEY_3;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_4.getCode())
			return HP12CKeys.KEY_4;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_5.getCode())
			return HP12CKeys.KEY_5;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_6.getCode())
			return HP12CKeys.KEY_6;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_7.getCode())
			return HP12CKeys.KEY_7;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_8.getCode())
			return HP12CKeys.KEY_8;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_9.getCode())
			return HP12CKeys.KEY_9;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_DIV.getCode())
			return HP12CKeys.KEY_DIV;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_N.getCode())
			return HP12CKeys.KEY_N;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_I.getCode())
			return HP12CKeys.KEY_I;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_PV.getCode())
			return HP12CKeys.KEY_PV;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_PMT.getCode())
			return HP12CKeys.KEY_PMT;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_FV.getCode())
			return HP12CKeys.KEY_FV;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_CHS.getCode())
			return HP12CKeys.KEY_CHS;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_POW.getCode())
			return HP12CKeys.KEY_POW;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_RECIPROCAL.getCode())
			return HP12CKeys.KEY_RECIPROCAL;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_PERC_TOT.getCode())
			return HP12CKeys.KEY_PERC_TOT;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_PERC_DELTA.getCode())
			return HP12CKeys.KEY_PERC_DELTA;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_PERC.getCode())
			return HP12CKeys.KEY_PERC;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_EEX.getCode())
			return HP12CKeys.KEY_EEX;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_RS.getCode())
			return HP12CKeys.KEY_RS;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_SST.getCode())
			return HP12CKeys.KEY_SST;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_ROLL.getCode())
			return HP12CKeys.KEY_ROLL;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_XY.getCode())
			return HP12CKeys.KEY_XY;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_CLX.getCode())
			return HP12CKeys.KEY_CLX;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_ENTER.getCode())
			return HP12CKeys.KEY_ENTER;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_ON.getCode())
			return HP12CKeys.KEY_ON;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_F.getCode())
			return HP12CKeys.KEY_F;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_G.getCode())
			return HP12CKeys.KEY_G;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_STO.getCode())
			return HP12CKeys.KEY_STO;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_RCL.getCode())
			return HP12CKeys.KEY_RCL;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_DOT.getCode())
			return HP12CKeys.KEY_DOT;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_TOT.getCode())
			return HP12CKeys.KEY_TOT;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_MUL.getCode())
			return HP12CKeys.KEY_MUL;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_SUB.getCode())
			return HP12CKeys.KEY_SUB;
		else if (this.cfg.getCode(chr) == HP12CKeys.KEY_SUM.getCode())
			return HP12CKeys.KEY_SUM;
		else
			return null;
	}

	public void setUpMouseListeners() {

		HP12CMouseListener listener = new HP12CMouseListener(this);

		window.setMouseListener(HP12CKeys.KEY_0, listener);
		window.setMouseListener(HP12CKeys.KEY_1, listener);
		window.setMouseListener(HP12CKeys.KEY_2, listener);
		window.setMouseListener(HP12CKeys.KEY_3, listener);
		window.setMouseListener(HP12CKeys.KEY_4, listener);
		window.setMouseListener(HP12CKeys.KEY_5, listener);
		window.setMouseListener(HP12CKeys.KEY_6, listener);
		window.setMouseListener(HP12CKeys.KEY_7, listener);
		window.setMouseListener(HP12CKeys.KEY_8, listener);
		window.setMouseListener(HP12CKeys.KEY_9, listener);
		window.setMouseListener(HP12CKeys.KEY_DIV, listener);
		window.setMouseListener(HP12CKeys.KEY_N, listener);
		window.setMouseListener(HP12CKeys.KEY_I, listener);
		window.setMouseListener(HP12CKeys.KEY_PV, listener);
		window.setMouseListener(HP12CKeys.KEY_PMT, listener);
		window.setMouseListener(HP12CKeys.KEY_FV, listener);
		window.setMouseListener(HP12CKeys.KEY_CHS, listener);
		window.setMouseListener(HP12CKeys.KEY_POW, listener);
		window.setMouseListener(HP12CKeys.KEY_RECIPROCAL, listener);
		window.setMouseListener(HP12CKeys.KEY_PERC_TOT, listener);
		window.setMouseListener(HP12CKeys.KEY_PERC_DELTA, listener);
		window.setMouseListener(HP12CKeys.KEY_PERC, listener);
		window.setMouseListener(HP12CKeys.KEY_EEX, listener);
		window.setMouseListener(HP12CKeys.KEY_RS, listener);
		window.setMouseListener(HP12CKeys.KEY_SST, listener);
		window.setMouseListener(HP12CKeys.KEY_ROLL, listener);
		window.setMouseListener(HP12CKeys.KEY_XY, listener);
		window.setMouseListener(HP12CKeys.KEY_CLX, listener);
		window.setMouseListener(HP12CKeys.KEY_ENTER, listener);
		window.setMouseListener(HP12CKeys.KEY_ON, listener);
		window.setMouseListener(HP12CKeys.KEY_F, listener);
		window.setMouseListener(HP12CKeys.KEY_G, listener);
		window.setMouseListener(HP12CKeys.KEY_STO, listener);
		window.setMouseListener(HP12CKeys.KEY_RCL, listener);
		window.setMouseListener(HP12CKeys.KEY_DOT, listener);
		window.setMouseListener(HP12CKeys.KEY_TOT, listener);
		window.setMouseListener(HP12CKeys.KEY_MUL, listener);
		window.setMouseListener(HP12CKeys.KEY_SUB, listener);
		window.setMouseListener(HP12CKeys.KEY_SUM, listener);

	}

	public void setUpKeyListeners() {
		this.window.setKeyListener(new HP12CKeyListener(this));
	}

	public void setUpMenuListeners() {
		this.window.setMenuListener(new HP12CMenuListener(this));
	}

	public void setUpWindowListeners() {
		this.window.setWindowListener(new HP12CWindowListener(this));
	}
	
	public void doMenuCommand(String command, String param) {

		if (command == null)
			return;

		tmp = command.split("::");

		if (tmp.length > 1) {
			cmd = tmp[0];
			prm = tmp[1];
		} else {
			cmd = tmp[0];
		}

		if (cmd.equals(HP12CWindow.CMD_FILE_IMPORT)) {

		} else if (cmd.equals(HP12CWindow.CMD_FILE_EXPORT)) {

		} else if (cmd.equals(HP12CWindow.CMD_FILE_QUIT)) {

		} else if (cmd.equals(HP12CWindow.CMD_EDIT_MENU)) {

		} else if (cmd.equals(HP12CWindow.CMD_EDIT_COPY)) {
			copyFromDisplayValue();
		} else if (cmd.equals(HP12CWindow.CMD_EDIT_PASTE)) {
			pasteToDisplayValue();
		} else if (cmd.equals(HP12CWindow.CMD_EDIT_ERASE_DSP)) {
			this.executor.getDisplay().setValue(0);
			this.executor.getStack().set(0, 0);
			this.executor.updateDisplay();
			this.window.updateDisplay();
		} else if (cmd.equals(HP12CWindow.CMD_EDIT_ERASE_STK)) {
			this.executor.getDisplay().setValue(0);
			this.executor.getStack().clear();
			this.executor.updateDisplay();
			this.window.updateDisplay();
		} else if (cmd.equals(HP12CWindow.CMD_EDIT_ERASE_FIN)) {
			this.executor.getFinanceMemory().clear();
		} else if (cmd.equals(HP12CWindow.CMD_EDIT_ERASE_STA)) {
			this.executor.getGeneralMemory().clearStats();
		} else if (cmd.equals(HP12CWindow.CMD_EDIT_ERASE_REG)) {
			this.executor.getGeneralMemory().clear();
		} else if (cmd.equals(HP12CWindow.CMD_EDIT_ERASE_PRG)) {
			this.executor.getProgramMemory().clear();
		} else if (cmd.equals(HP12CWindow.CMD_VIEW_MENU)) {

		} else if (cmd.equals(HP12CWindow.CMD_VIEW_SIZE)) {

		} else if (cmd.equals(HP12CWindow.CMD_VIEW_SIZE_VERY_SMALL)) {
			cfg.setSize(HP12CWindow.SIZE_VERY_SMALL);
			this.updateWindow();
		} else if (cmd.equals(HP12CWindow.CMD_VIEW_SIZE_SMALL)) {
			cfg.setSize(HP12CWindow.SIZE_SMALL);
			this.updateWindow();
		} else if (cmd.equals(HP12CWindow.CMD_VIEW_SIZE_MEDIUM)) {
			cfg.setSize(HP12CWindow.SIZE_MEDIUM);
			this.updateWindow();
		} else if (cmd.equals(HP12CWindow.CMD_VIEW_SIZE_LARGE)) {
			cfg.setSize(HP12CWindow.SIZE_LARGE);
			this.updateWindow();
		} else if (cmd.equals(HP12CWindow.CMD_VIEW_SIZE_HUGE)) {
			cfg.setSize(HP12CWindow.SIZE_HUGE);
			this.updateWindow();
		} else if (cmd.equals(HP12CWindow.CMD_VIEW_SKIN)) {
			cfg.setSkin(param);
			this.updateWindow();
		} else if (cmd.equals(HP12CWindow.CMD_VIEW_LANGUAGE)) {
			cfg.setLanguage(param);
			this.updateWindow();
		} else if (cmd.equals(HP12CWindow.CMD_OPTIONS_NUMBER_FORMAT)) {

		} else if (cmd.equals(HP12CWindow.CMD_OPTIONS_NUMBER_FORMAT_DOT)) {
			executor.getDisplay().setComma(false);
			window.updateDisplay();
		} else if (cmd.equals(HP12CWindow.CMD_OPTIONS_NUMBER_FORMAT_COMMA)) {
			executor.getDisplay().setComma(true);
			window.updateDisplay();
		} else if (cmd.equals(HP12CWindow.CMD_OPTIONS_DATE_FORMAT_DAY)) {
			executor.getFlags().setDmy(1);
			window.updateDisplay();
		} else if (cmd.equals(HP12CWindow.CMD_OPTIONS_DATE_FORMAT_MONTH)) {
			executor.getFlags().setDmy(0);
			window.updateDisplay();
		} else if (cmd.equals(HP12CWindow.CMD_OPTIONS_PAYMENT_MODE_BEGIN)) {
			executor.getFlags().setBegin(1);
			window.updateDisplay();
		} else if (cmd.equals(HP12CWindow.CMD_OPTIONS_PAYMENT_MODE_END)) {
			executor.getFlags().setBegin(0);
			window.updateDisplay();
		} else if (cmd.equals(HP12CWindow.CMD_TOOLS_REGISTERS_VIEW)) {

		} else if (cmd.equals(HP12CWindow.CMD_TOOLS_HISTORY)) {

		} else if (cmd.equals(HP12CWindow.CMD_ABOUT_AUTHOR)) {

		} else if (cmd.equals(HP12CWindow.CMD_ABOUT_CONTRIBUTORS)) {

		} else if (cmd.equals(HP12CWindow.CMD_ABOUT_SOFTWARE)) {

		}
	}

	public void setExecutor(HP12CExecutor executor) {
		this.executor = executor;
	}

	public HP12CExecutor getExecutor() {
		return this.executor;
	}

	public HP12CWindow getWindow() {
		return this.window;
	}

	public void setWindow(HP12CWindow window) {
		this.window = window;
	}

	public HP12CTool getTool(String id) {
		if (this.ToolsHash.get(id) != null) {
			return this.ToolsHash.get(id);
		} else {
			return null;
		}
	}

	public void setTool(String id, HP12CTool tool) {
		if (this.ToolsHash.get(id) == null) {
			this.ToolsHash.put(id, tool);
		}
	}

	public void addTool(String id, HP12CTool tool) {
		if (this.ToolsHash.get(id) == null) {
			this.ToolsHash.put(id, tool);
		}
	}

	public void remTool(String id) {
		if (this.ToolsHash.get(id) != null) {
			this.ToolsHash.remove(id);
		}
	}

	public void updateTool(String id) {
		if (this.ToolsHash.get(id) != null) {
			this.ToolsHash.get(id).update();
		}
	}

	public void showTool(String id) {
		if (this.ToolsHash.get(id) != null) {
			this.ToolsHash.get(id).show();
		}
	}

	public void hideTool(String id) {
		if (this.ToolsHash.get(id) != null) {
			this.ToolsHash.get(id).hide();
		}
	}

	public void keyPressed(KeysEnumInterface key) {
		this.executor.keyPressed(key);
		this.window.keyPressed(key);
	}

	public void keyReleased(KeysEnumInterface key) {
		this.executor.keyReleased(key);
		this.window.keyReleased(key);
	}

	public void copyFromDisplayValue() {
		JTextField tmp = new JTextField();
		tmp.setText("" + executor.getStack().get(0));
		tmp.selectAll();
		tmp.copy();
	}

	public void pasteToDisplayValue() {
		JTextField tmp = new JTextField();
		String str = "";
		double val = 0.0;

		tmp.selectAll();
		tmp.paste();

		try {

			if (executor.getDisplay().getComma()) {
				str = tmp.getText().replace('.', ',');
			} else {
				str = tmp.getText();
			}

			val = Double.parseDouble(str);

			executor.getDisplay().setValue(val);
			executor.getStack().set(0, val);
			window.updateDisplay();
		} catch (Exception e) { /* TODO: handle exception */
		}
	}

	private void welcomeMessage() {
		System.out.println("");
		System.out.println(this.window.getLanguageStringList().getValue(
				"META_NAME") + " v" + HP12CConfiguration.VERSION);
		System.out.println(this.window.getLanguageStringList().getValue(
				"META_LICENCE"));
	}

	public void save() {
		this.saveConfigs();
		this.saveMemory();
	}

	public void quit() {
		this.save();
		System.exit(0);
	}

}
