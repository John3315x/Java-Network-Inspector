package com.minisiem.utils.Monitor;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class FrameMonitor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameMonitor(int width, int height, NKMonitor monitor) {
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.getContentPane().add(monitor);
		this.setVisible(true);
		this.setTitle("NK-Monitor");
		this.setIconImage(loadIcon("ico.png"));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		int x = (screenSize.width - frameSize.width) / 2;
		int y = (screenSize.height - frameSize.height) / 2;;
		this.setLocation(x, y);
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
