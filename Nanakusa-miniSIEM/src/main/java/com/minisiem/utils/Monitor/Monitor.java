package com.minisiem.utils.Monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Monitor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextArea area;
	
	public Monitor() {
		this.setLayout(new BorderLayout());
		
		area = new JTextArea();
		area.setBackground(Color.BLACK);
		area.setForeground(Color.GREEN);
		area.setFont(new Font("Consolas", Font.PLAIN, 18));
		area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		area.setCaretColor(Color.GREEN);
		area.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(area);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// ocultar visualmente la barra
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setPreferredSize(new Dimension(0, 0));
		this.add(scrollPane, BorderLayout.CENTER);
	}

}
