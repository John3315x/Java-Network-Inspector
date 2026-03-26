package com.minisiem.utils.Monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class NKMonitor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTextArea area, topArea;

	// 🔹 HILO DE ANIMACIÓN
	public Timer timer;

	public NKMonitor() {
		this.setLayout(new BorderLayout());

		area = new JTextArea();
		area.setBackground(Color.BLACK);
		area.setForeground(Color.GREEN);
		area.setFont(new Font("Consolas", Font.PLAIN, 18));
		area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		area.setCaretColor(Color.GREEN);
		area.setLineWrap(true);
		area.setEditable(false);
		
		topArea = new JTextArea();
		topArea.setBackground(Color.BLACK);
		topArea.setForeground(Color.GREEN);
		topArea.setFont(new Font("Consolas", Font.PLAIN, 18));
		topArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		topArea.setCaretColor(Color.GREEN);
		topArea.setLineWrap(true);
		topArea.setEditable(false);
		
		this.add(topArea, BorderLayout.SOUTH);

		JScrollPane scrollPane = new JScrollPane(area);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// ocultar visualmente la barra
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setPreferredSize(new Dimension(0, 0));
		this.add(scrollPane, BorderLayout.CENTER);
	}

	public Timer loadingAnimationNKMonitor() {
		int animationStart = topArea.getDocument().getLength();
		//topArea.append("Escuchando \n");
		String[] spinner = { "|", "/", "—", "\\" };

		timer = new Timer(150, new ActionListener() {
			int i = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// eliminar el último carácter (spinner anterior)
					int length = topArea.getDocument().getLength();
					if (length > animationStart) {
						topArea.getDocument().remove(length - 1, 1);
					}

					// agregar nuevo símbolo
					topArea.getDocument().insertString(topArea.getDocument().getLength(), spinner[i], null);

					i = (i + 1) % spinner.length;

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		return timer;
	}

}
