package tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.HP12CConfiguration;
import core.HP12CController;

public abstract class HP12CTool {

	protected HP12CController controller;
	protected HP12CConfiguration cfg;
	protected String basePath;
	protected String skinPath;

	protected JFrame frame;
	protected JPanel panel;
	
	private Image img;
	private ImageIcon ico;

	// Main panel size
	protected int panelHeight, panelWidth;

	public HP12CTool(HP12CController controller) {
		this.controller = controller;
		this.initPanel();
		this.initFrame();
	}

	private void initFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setMinimumSize(new Dimension(panelWidth, panelHeight));
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}

	private void initPanel() {
		panel = new JPanel();

		this.setDefaultSize();

		panel.setLayout(new BorderLayout());

	}

	public void addComponent(JComponent comp, String layout) {
		this.panel.add(comp, layout);
		this.panel.revalidate();
		this.panel.repaint();
	}

	public void setController(HP12CController controller) {
		this.controller = controller;
	}

	public HP12CController getController() {
		return this.controller;
	}

	public void setConfigs(HP12CConfiguration cfg) {
		this.cfg = cfg;
	}

	public void setTitle(String title) {
		this.frame.setTitle(title);
	}

	public void setDefaultSize() {
		// Main panel size
		this.panelHeight = 300;
		this.panelWidth = 200;
	}

	public void setSize(double size) {

		// Main panel size
		this.panelHeight = (int) (panelHeight * size);
		this.panelWidth = (int) (panelWidth * size);
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public void update() {
		// TODO
	}

	private ImageIcon createImageIcon(int w, int h, String path) {
		
		try {
			
			InputStream i = loadByRelativePath(path);
			
			this.ico = new ImageIcon(ImageIO.read(i));
			this.img = ico.getImage().getScaledInstance(w, h,
					Image.SCALE_SMOOTH);
			this.ico = new ImageIcon(this.img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.ico;
	}

	// Returns a file from the file system or from the Jar file
	public InputStream loadByRelativePath(String path) {

		File f = new File(this.basePath + path);
		
		if (f.isFile()) {
			try {
				return new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			return this.getClass().getResourceAsStream("/resources/" + path);
		}
		
		return null;
	}
	
	private boolean existBaseDirecory(){
		File dir = new File(this.basePath);
		return (dir.exists());
	}
	
	private void findPaths() {

		this.basePath = System.getProperty("user.home") + "/.finanx12c/";

		if (!existBaseDirecory()) {
			this.basePath = "/resources/";
		}

		if (cfg.getSkin() != "") {
			this.skinPath = "skins/" + cfg.getSkin() + "/";
		} else {
			this.skinPath = "skins/default/";
		}

	}

}
