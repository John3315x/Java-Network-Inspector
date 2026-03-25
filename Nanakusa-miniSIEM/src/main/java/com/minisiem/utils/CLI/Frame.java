package com.minisiem.utils.CLI;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Frame(int width, int height, Console console) {
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.getContentPane().add(console);
		this.setVisible(true);
		this.setTitle("Nanakusa-MiniSIEM");
		this.setIconImage(loadIcon("ico.png"));

	}

	private Image loadIcon(String path) {
		try {
			return ImageIO.read(getClass().getResourceAsStream("/Image/" + path));
		} catch (IOException | IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
