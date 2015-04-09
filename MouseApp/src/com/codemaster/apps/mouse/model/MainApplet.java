package com.codemaster.apps.mouse.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.StyleConstants;

import com.codemaster.apps.mouse.app.MouseApp;
import com.codemaster.apps.mouse.app.StringValues;
import com.codemaster.apps.mouse.helper.MouseHelper;
import com.codemaster.apps.mouse.model.ServerHelper.DeviceConnectedListener;
import com.codemaster.apps.mouse.model.ServerHelper.MessageReceivedListener;
import com.codemaster.apps.mouse.model.ServerHelper.ServerStartedListener;

@SuppressWarnings("serial")
public class MainApplet extends JFrame {

	private TransparentTextPane status;
	private TransparentTextPane credit;
	private TransparentTextPane ipAddress;
	private TransparentTextPane portNumber;

	private TransparentPane leftSidePane;
	private TransparentPane mainContentPane;
	private JButton startService;
	private JButton startWaitingForDevice;
	private JButton startUsing;

	private Integer port;

	public void create() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					createAppletGUI();
				}

			});
		} catch (Exception e) {

		}
	}

	public MainApplet(String title) {
		super(title);

	}

	private void createStatusTextPane() {
		status = new TransparentTextPane(128);
		status.setBounds(230, 530, 550, 30);
		status.setEditable(false);
		Border line = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		status.setBorder(line);
		status.setCustomText(StringValues.STATUS_SERVICEIDLE, Color.RED, 16,
				true, true, StyleConstants.ALIGN_CENTER);

		this.getContentPane().add(status);
	}

	private void createLeftSidePane() {
		leftSidePane = new TransparentPane();
		TitledBorder title;
		Border line = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		title = BorderFactory.createTitledBorder(line, "3 easy steps to use",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("courier",
						Font.PLAIN, 12), Color.WHITE);
		leftSidePane.setBounds(20, 20, 200, 300);
		leftSidePane.setBorder(title);
		leftSidePane.setLayout(null);
		this.getContentPane().add(leftSidePane);
	}

	private void createCreditTextPane() {
		credit = new TransparentTextPane(128);
		TitledBorder title;
		Border line = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		title = BorderFactory.createTitledBorder(line, "Credits",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("courier",
						Font.PLAIN, 12), Color.WHITE);
		credit.setBounds(20, 340, 200, 223);
		credit.setBorder(title);
		credit.setCustomText(StringValues.CREDITS, Color.BLACK, 11, false,
				false, StyleConstants.ALIGN_LEFT);
		this.getContentPane().add(credit);
	}

	private void createMainContentPane() {
		mainContentPane = new TransparentPane();
		TitledBorder title;
		Border line = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		title = BorderFactory.createTitledBorder(line, "About this app",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("courier",
						Font.PLAIN, 12), Color.WHITE);
		mainContentPane.setBounds(230, 20, 550, 500);
		mainContentPane.setBorder(title);
		this.getContentPane().add(mainContentPane);
	}

	private void createStartServiceButton() {
		startService = new JButton();
		startService.setText("Start service");
		startService.setBounds(10, 30, 180, 36);
		startService.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MouseApp.getServerInstance().setOnServerStartedListener(
						new ServerStartedListener() {

							@Override
							public void onServerStarted() {
								status.setCustomText(
										StringValues.STATUS_SERVICESTARTED,
										Color.BLUE, 16, true, true,
										StyleConstants.ALIGN_CENTER);
								portNumber.setCustomText(
										"Password: " + port.toString(),
										Color.BLACK, 12, true, false,
										StyleConstants.ALIGN_LEFT);
								startWaitingForDevice.setEnabled(true);
							}
						});
				Random rand = new Random();
				port = 1000 + rand.nextInt(8999);
				MouseApp.getServerInstance().setServerPort(port);
				MouseApp.getServerInstance().startServer();
			}
		});
		leftSidePane.add(startService);
	}

	private void createStartWaitButton() {
		startWaitingForDevice = new JButton();
		startWaitingForDevice.setText("Start waiting");
		startWaitingForDevice.setBounds(10, 76, 180, 36);
		startWaitingForDevice.setEnabled(false);
		startWaitingForDevice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MouseApp.getServerInstance().setOnDeviceConnectedListener(
						new DeviceConnectedListener() {

							@Override
							public void onDeviceConnected() {
								status.setCustomText(
										StringValues.STATUS_CONNECTED,
										Color.MAGENTA, 16, true, true,
										StyleConstants.ALIGN_CENTER);
								startUsing.setEnabled(true);
							}
						});
				status.setCustomText(StringValues.STATUS_WAITING, Color.YELLOW,
						16, true, true, StyleConstants.ALIGN_CENTER);
				MouseApp.getServerInstance().connectToClient();
			}
		});
		leftSidePane.add(startWaitingForDevice);
	}

	private void createStartUsingButton() {
		startUsing = new JButton();
		startUsing.setText("Start using");
		startUsing.setBounds(10, 122, 180, 36);
		startUsing.setEnabled(false);
		startUsing.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				status.setCustomText(StringValues.STATUS_RUNNING, Color.BLACK,
						16, true, true, StyleConstants.ALIGN_CENTER);
				MouseApp.getServerInstance().setOnMessageReceivedListener(
						new MessageReceivedListener() {

							@Override
							public void onMessageReceived(String message) {
								MouseHelper.MouseSignalHandler(message);
							}
						});
				MouseApp.getServerInstance().startReceiveMessage();
			}
		});
		leftSidePane.add(startUsing);
	}

	private void createAddressTextPane() {
		ipAddress = new TransparentTextPane(0);
		ipAddress.setBounds(10, 218, 180, 36);
		ipAddress.setCustomText("Machine IP: "
				+ MouseApp.getServerInstance().getLocalIpAddress(),
				Color.BLACK, 12, true, false, StyleConstants.ALIGN_LEFT);
		ipAddress.setEditable(false);
		leftSidePane.add(ipAddress);
	}

	private void createPortTextPane() {
		portNumber = new TransparentTextPane(0);
		portNumber.setBounds(10, 264, 180, 36);
		portNumber.setCustomText("Password: ", Color.BLACK, 12, true, false,
				StyleConstants.ALIGN_LEFT);
		portNumber.setEditable(false);
		leftSidePane.add(portNumber);
	}

	private void createAppletGUI() {
		createMainWindow();
		createStatusTextPane();
		createLeftSidePane();
		createMainContentPane();
		createCreditTextPane();

		createStartServiceButton();
		createStartWaitButton();
		createStartUsingButton();
		createAddressTextPane();
		createPortTextPane();
	}

	private void createMainWindow() {
		int width = 800;
		int height = 600;
		Toolkit theKit = getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setResizable(false);

		setBounds((wndSize.width - 800) / 2, (wndSize.height - 600) / 2, width,
				height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		getContentPane().setBackground(Color.DARK_GRAY);

		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			javax.swing.SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			System.out.println("Look and feel not set.");
		}

		this.setVisible(true);
	}
}