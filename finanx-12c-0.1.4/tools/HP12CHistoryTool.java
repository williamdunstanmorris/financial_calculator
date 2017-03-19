package tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import core.HP12CController;

public class HP12CHistoryTool extends HP12CTool {

	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JToolBar toolBar;

	private JButton copyButton, clearButton, lockButton;

	public HP12CHistoryTool(HP12CController controller) {
		super(controller);
		this.init();
	}

	private void init() {

		this.setTitle("History view");

		this.initToolBar();
		this.initTextArea();
		this.initScrollPane();

		this.addComponent(toolBar, BorderLayout.NORTH);
		this.addComponent(scrollPane, BorderLayout.CENTER);

		this.update();
	}

	private void initTextArea() {
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);

		this.textArea.setBackground(Color.GRAY);

		append("Teste");
		append("Teste");

	}

	private void initScrollPane() {
		this.scrollPane = new JScrollPane(this.textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.toolBar.setVisible(true);
		
	}

	public void initToolBar() {
		this.toolBar = new JToolBar();
		
		this.clearButton = new JButton("Clear");
		this.copyButton = new JButton("Copy");
		this.lockButton = new JButton("Lock");

		this.toolBar.add(this.clearButton);
		this.toolBar.add(this.copyButton);
		this.toolBar.add(this.lockButton);

		this.toolBar.setVisible(true);
		this.toolBar.setFloatable(false);

	}

	public void append(String text) {
		this.textArea.append(text+"\n");
	}
	
}
