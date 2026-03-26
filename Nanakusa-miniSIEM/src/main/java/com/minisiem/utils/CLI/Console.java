package com.minisiem.utils.CLI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.minisiem.events.log.EventManager;
import com.minisiem.model.Device;
import com.minisiem.model.port.Port;
import com.minisiem.monitor.NetworkMonitor;
import com.minisiem.utils.Monitor.FrameMonitor;
import com.minisiem.utils.Monitor.NKMonitor;

public class Console extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea console;
	private int promptPosition;

	private int consoleMode;
	private boolean locked;

	private final String PROMPT = "Nanakusa> ";
	private final String PROMPT_CONFIGURE = "Nanakusa[*]> ";
	private final String PROMPT_USER = "Nanakusa[username]> ";

	private CommandRouter commandRouter;
	
	// 🔹 HILO DE ANIMACIÓN
    private Timer timer;
    
    // Monitor
    private FrameMonitor frameMonitor;
    private NKMonitor nkMonitor;
    private boolean activeNKMonitor, monitoring;
    
    //Actividades de monitor
    private NetworkMonitor networkMonitor;

	public Console() {

		this.setLayout(new BorderLayout());
		this.consoleMode = 2;
		this.locked = false;
		this.commandRouter = new CommandRouter();
		
		this.nkMonitor = new NKMonitor();
		this.activeNKMonitor = false;
		this.monitoring = false;
		networkMonitor = new NetworkMonitor();

		console = new JTextArea();
		console.setBackground(Color.BLACK);
		console.setForeground(Color.GREEN);
		console.setFont(new Font("Consolas", Font.PLAIN, 18));
		console.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		console.setCaretColor(Color.GREEN);
		console.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(console);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// ocultar visualmente la barra
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setPreferredSize(new Dimension(0, 0));
		this.add(scrollPane, BorderLayout.CENTER);

		console.addKeyListener(new ConsoleKeyListener());

		printData();
		changePrompt();
	}

	private void printData() {
		String copyright = "==================================================\r\n"
				+ " Nanakusa Network CLI Tool\r\n"
				+ " Version v0.9-beta\r\n"
				+ "\r\n"
				+ " Developed by John Chaves\r\n"
				+ " © 2026 All rights reserved\r\n"
				+ "\r\n"
				+ " Type 'help' to see available commands\r\n"
				+ "==================================================";
		
		console.append(copyright);
	}

	private void printPrompt(String prompt) {
		console.append(prompt);
		promptPosition = console.getDocument().getLength();
	}

	private void changePrompt() {
		console.append("\n");
		// Cambiar el Prompt segun el modo de la consola
		if (consoleMode == 0) {
			printPrompt(PROMPT);
		} else if (consoleMode == 1) {
			printPrompt(PROMPT_CONFIGURE);
		} else if (consoleMode == 2) {
			printPrompt(PROMPT_USER);
		}
	}

	private void runCommand(String cmd) {
		// Aquí conectas con tu CommandRouter o lógica interna
		/**
		 * Regex
		 * host -#, host --#, h -#, h --# 
		 */
		Pattern patternA1 = Pattern.compile("(h|host)\\s+(?:0|(--?)([1-9]\\d*))");
		Matcher matcherA1 = patternA1.matcher(cmd);
		
		/**
		 * Regex
		 * sap $ #
		 */
		Pattern patternA2 = Pattern.compile(
			    "sap\\s+" +
			    "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\." +
			    "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\." +
			    "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\." +
			    "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
			    "\\s+" +
			    "(6553[0-5]|655[0-2]\\d|65[0-4]\\d{2}|6[0-4]\\d{3}|[1-5]?\\d{1,4})"
		);
		Matcher matcherA2 = patternA2.matcher(cmd);
		
		/**
		 * Regex
		 * device activity -m #, d a -m #
		 */
		Pattern patternA3 = Pattern.compile(
				"(device\\s+activity|d\\s+a)\\s+(-m)\\s+(1[5-9]|[2-9]\\d|[1-2]\\d{2}|300)"
		);
		Matcher matcherA3 = patternA3.matcher(cmd);
		
		/**
		 * Regex
		 * color # # #
		 */
		Pattern patternC1 = Pattern.compile(
			    "(c|color)\\s+(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\s+" +
			    "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\s+" +
			    "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"
		);
		Matcher matcherC1 = patternC1.matcher(cmd);

		// HELPS
		if (cmd.equals("help") && consoleMode == 0) {
			console.append("\nComandos disponibles:\n");
			console.append("|clear, cls\n"
					+ "|exit\n"
					+ "|login\n"
					+ "|configure, config");

		} else if (cmd.equals("help") && consoleMode == 1) {
			console.append("\nComandos disponibles:\n");
			console.append("|help\n"
					+ "|clear, cls\n"
					+ "|exit\n"
					+ "|show user, su\n"
					+ "|create user, cu\n"
					+ "|delete user, du\n"
					+ "---------------------------------\n"
					+ "|network thread #, nt #\n"
					+ "|port timeout #, pt #\n"
					+ "---------------------------------"
					);
		} else if (cmd.equals("help") && consoleMode == 2) {
			console.append("\nComandos disponibles:\n");
			console.append("|help\n"
					+ "|clear, cls\n"
					+ "|exit\n"
					+ "---------------------------------\n"
					+ "|ip discovery, ip d\n"
					+ "|host 0, h 0, host -#, host --#, h -#, h --#\n"
					+ "|sap $ #\n"
					+ "|pfhs\n"
					+ "|nk -monitor, nk -m\n"
					+ "|nk -monitor close, nk -m c\n"
					+ "|device activity -m #, d a -m # (# >= 15 o # <= 300)\n"
					+ "|device activity -m stop, d a -m s\n"
					+ "|-monitor clear, -monitor cls, -m clear, -m cls\n"
					+ "---------------------------------\n"
					+ "|color # # #, c # # #\n"
					+ "|color reset, color r, c r"
					);
		}

		// CLEARS
		else if (cmd.equals("clear") || cmd.equals("cls")) {
			console.setText("");
			printData();
			changePrompt();

		} 
		
		// EXIT
		else if (cmd.equals("exit")) {
			switch (consoleMode) {
			case 0:
				System.exit(0);
				break;
			case 1:
			case 2:
				consoleMode = 0;
				break;

			default:
				break;
			}
		}

		// COMMANDS MODE 0
		else if ((cmd.equals("configure") || cmd.equals("config")) && consoleMode == 0) {
			consoleMode = 1;
		}

		// COMMANDS MODE 1

		// COMMANDS MODE 2
		else if ((cmd.equals("ip discovery") || cmd.equals("ip d")) && consoleMode == 2) {//ip discovery, ip d
			locked = true;
			
			loadingAnimation().start();

			new Thread(() -> {
				
				List<Device> list = commandRouter.ipDiscovery();
				
				//DETENER ANIMACIÓN 
				timer.stop();

				SwingUtilities.invokeLater(() -> {
					for (Device device : list) {
						console.append("\n" + device);
					}
					changePrompt();
					locked = false;
				});
			}).start();
		}

		else if (matcherA1.matches() && consoleMode == 2) {// host #, h #
			if (matcherA1.matches()) {
				List<Object> devices = new ArrayList<Object>();
				
			    //String comando = matcherA1.group(1);

			    String guiones = matcherA1.group(2); // puede ser null
			    String numeroStr = matcherA1.group(3); // puede ser null

			    int numero;

			    if (guiones == null) {// caso: host 0 o h 0
			        devices = commandRouter.getAllDevices();
			        //numero = 0;
			    } else {
			    	numero = Integer.parseInt(numeroStr);
			    	devices = commandRouter.getOrderVolumeDevices(numero, guiones);
			    }
			    
			    
			    for (Object object : devices) {
			    	Device device = (Device) object;
					console.append("\n" + device.dbFormat());
				}
			} 
		}
		
		else if (matcherA2.matches() && consoleMode == 2) {// sap $ #
			locked = true;		
			String ip = matcherA2.group(1) + "." +
					matcherA2.group(2) + "." +
					matcherA2.group(3) + "." +
					matcherA2.group(4);	
			int numberPorts = Integer.parseInt(matcherA2.group(5));
			
			loadingAnimation().start();
									
			new Thread(() -> {
				
				// 🔹 trabajo pesado FUERA del hilo de UI
				List<Port> ports = commandRouter.scanAllPorts(ip, numberPorts);
				
				//DETENER ANIMACIÓN 
				timer.stop();

				// 🔹 actualizar UI al final
				SwingUtilities.invokeLater(() -> {
					for (Port port : ports) {
						console.append("\n" + port);
					}
					changePrompt();
					locked = false;
				});
			}).start();
		}
		
		else if (cmd.equals("pfhs") && consoleMode == 2) {// pfhs
			locked = true;
			
			loadingAnimation().start();
			
			new Thread(() -> {
				
				List<Device> devices = commandRouter.performFullHostScan();
				
				//DETENER ANIMACIÓN 
				timer.stop();

				SwingUtilities.invokeLater(() -> {
					for (Device device : devices) {
						console.append("\n" + device);
					}
					changePrompt();
					locked = false;
				});
			}).start();
		}
		
		else if ((cmd.equals("nk -monitor") || cmd.equals("nk -m")) && consoleMode == 2) {// nk monitor, nk m
			if (!activeNKMonitor) {
				frameMonitor = new FrameMonitor(1200, 700, nkMonitor);
				activeNKMonitor = true;
				
				nkMonitor.loadingAnimationNKMonitor().start();
			}
		}
		
		else if ((cmd.equals("nk -monitor close") || cmd.equals("nk -m c")) && consoleMode == 2) {// nk monitor close, nk m c
			if (activeNKMonitor) {
				frameMonitor.dispose();
				activeNKMonitor = false;
				
				//detener monitoreo de red
				networkMonitor.stop();
				EventManager.monitor = null;// Para que deje de pintar en el NK Monitor si está activo
				
				nkMonitor.area.setText("");
			}
		}
		
		
		//START MONITOR ACTIVITIES ====================
		else if (matcherA3.matches()) {// device activity -m #, d a -m #
			if (activeNKMonitor) {// Verificar que el nk monitor esté iniciado
				nkMonitor.area.setText("");//Limpiar area
				
				//String comando = matcherA3.group(1); // device activity | d a
			    //String flag = matcherA3.group(2);    // -m
			    int segundos = Integer.parseInt(matcherA3.group(3));
			    
			    monitoring = true;//indicador de monitore activo
			    console.append("\nSe ha iniciado un monitoreo de eventos de red para cada " + segundos + " segundos.");
			    
			    //Se inicia el hilo 
				networkMonitor.start(segundos);
				
				//Se autoriza al Event Manager a pintar en NK Monitor al pasarle el objeto listo
				EventManager.monitor = nkMonitor;
			    
			} else {
				console.append("\nPara hacer uso de esta función es necesario iniciar una instancia de NK Monitor.");
			}
		}
		
		else if ((cmd.equals("-monitor clear") || cmd.equals("-monitor cls") || cmd.equals("-m clear") || cmd.equals("-m cls")) && consoleMode == 2) {
			if(!monitoring)nkMonitor.area.setText("");
		}
		
		//STOP MONITOR ACTIVITIES ====================
		else if ((cmd.equals("device activity -m stop") || cmd.equals("d a -m s")) && consoleMode == 2) {// nk monitor close, nk m c
			if (activeNKMonitor) {
				
				monitoring = false;//indicador de monitore activo
				console.append("\nMonitoreo de eventos de red detenido.");
				
				//detener monitoreo de red
				networkMonitor.stop();
				EventManager.monitor = null;// Para que deje de pintar en el NK Monitor si está activo
			}
		}
		
		else if(matcherC1.matches() && consoleMode == 2) {// color # # #
			int r = Integer.parseInt(matcherC1.group(2));
		    int g = Integer.parseInt(matcherC1.group(3));
		    int b = Integer.parseInt(matcherC1.group(4));
			console.setForeground(new Color(r, g, b));
			console.setCaretColor(new Color(r, g, b));
		}
		else if((cmd.equals("color reset") || cmd.equals("color r") || cmd.equals("c r")) && consoleMode == 2) {// color reset, color r, c r
			console.setForeground(Color.GREEN);
			console.setCaretColor(Color.GREEN);
		}

		else {
			console.append("\nComando no reconocido.");
		}
	}// runCommand
	
	private Timer loadingAnimation() {
		int animationStart = console.getDocument().getLength();
		console.append("\nProcesando ");
		String[] spinner = {"|", "/", "—", "\\"};
		
		timer = new Timer(150, new ActionListener() {
			int i = 0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
		            // eliminar el último carácter (spinner anterior)
		            int length = console.getDocument().getLength();
		            if (length > animationStart) {
		                console.getDocument().remove(length - 1, 1);
		            }

		            // agregar nuevo símbolo
		            console.getDocument().insertString(
		                console.getDocument().getLength(),
		                spinner[i],
		                null
		            );

		            i = (i + 1) % spinner.length;

		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
			}
		});
		return timer;
	}

	private class ConsoleKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int caret = console.getCaretPosition();

			// ----- BLOQUEA TECLAS QUE NO DEBEN MODIFICAR TEXTO ANTERIOR -----

			// No permitir mover el cursor sobre texto previo
			if (caret < promptPosition) {
				console.setCaretPosition(console.getDocument().getLength());
			}

			// Bloquear backspace si está al inicio del prompt
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && caret <= promptPosition) {
				e.consume();
				return;
			}

			// ENTER → Ejecutar comando
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();
				String fullText = console.getText();
				String cmd = fullText.substring(promptPosition).trim();
				
				runCommand(cmd);
				if (!locked && (!cmd.equals("cls") || cmd.equals("clear"))) {
					changePrompt();	
				}
				
				return;
			}

			// Evitar borrar texto previo con Delete
			if (e.getKeyCode() == KeyEvent.VK_DELETE && caret < console.getDocument().getLength()) {
				e.consume();
			}
		}
	}
}
