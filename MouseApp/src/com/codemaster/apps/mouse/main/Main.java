package com.codemaster.apps.mouse.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.codemaster.apps.mouse.model.MainApplet;
import com.codemaster.apps.mouse.model.ServerHelper;
import com.codemaster.apps.mouse.model.ServerHelper.MessageReceivedListener;

public class Main {
	private static ServerHelper serverHelper;
	private static Robot controller;

	public static void CreateWindow() {
		JFrame aWindow = new JFrame("Mouse");
		int width = 800;
		int height = 600;

		aWindow.setBounds(50, 100, width, height);
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container content = aWindow.getContentPane();
		content.setLayout(null);

		final JTextArea serverStatus = new JTextArea();
		serverStatus.setBounds(10, 10, 200, 30);
		serverStatus.setText("Not established");
		serverStatus.setEditable(false);
		serverStatus.setEnabled(false);

		JButton startServer = new JButton();
		startServer.setBounds(new Rectangle(10, 60, 150, 30));
		startServer.setText("Start server");
		startServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				serverHelper.startServer();
				serverStatus.setText("Server started");

			}
		});

		JButton startListening = new JButton();
		startListening.setBounds(new Rectangle(10, 90, 150, 30));
		startListening.setText("Start listening");
		startListening.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				serverHelper.connectToClient();
				serverStatus.setText("Waiting for client");
			}
		});

		JButton startReceiveMess = new JButton();
		startReceiveMess.setBounds(new Rectangle(10, 120, 150, 30));
		startReceiveMess.setText("Start Receive");
		startReceiveMess.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				serverHelper.startReceiveMessage();
			}
		});

		serverHelper
				.setOnMessageReceivedListener(new MessageReceivedListener() {

					@Override
					public void onMessageReceived(String message) {
						if (!message.equals("touched")
								&& !message.equals("released")) {
							Point p = MouseInfo.getPointerInfo().getLocation();
							Float dx = (float) 0, dy = (float) 0;
							Scanner input = new Scanner(message);

							if (input.hasNextFloat())
								dx = input.nextFloat();
							if (input.hasNextFloat())
								dy = input.nextFloat();

							controller.mouseMove(p.x
									+ (int) (dx.intValue() * 3), p.y
									+ (int) (dy.intValue() * 3));
							input.close();
						}
					}
				});

		content.add(serverStatus);
		content.add(startServer);
		content.add(startListening);
		content.add(startReceiveMess);

		content.setBackground(Color.DARK_GRAY);

		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			javax.swing.SwingUtilities.updateComponentTreeUI(aWindow);
		} catch (Exception e) {
			System.err.println("Look and feel not set.");
		}

		aWindow.setVisible(true);

	}

	public static void main(String args[]) {
		/*
		serverHelper = new ServerHelper();
		serverHelper.setServerPort(5000);
		try {
			controller = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		CreateWindow();
		*/
		MainApplet app = new MainApplet("Mouse");
		app.create();
	}
}