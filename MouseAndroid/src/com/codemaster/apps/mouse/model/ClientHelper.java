package com.codemaster.apps.mouse.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ClientHelper {

	private String SERVERIPADDRESS;
	private int SERVERPORT;
	private Socket socket;
	private boolean isConnectedToServer;

	public ClientHelper() {
		// abs ehfg
		// test annffnf
		SERVERIPADDRESS = "";
		isConnectedToServer = false;
	}

	public void connectToServer(String serverIP, int serverPort) {
		SERVERIPADDRESS = serverIP;
		SERVERPORT = serverPort;
		if (!SERVERIPADDRESS.equals("")) {
			new Thread(new ClientThreadStart()).start();
		}

	}

	public void stopClient() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isConnectedToServer = false;
	}

	public ArrayList<String> findAllAvailableServer() {
		ArrayList<String> listServerIP = new ArrayList<String>();

		try {
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) ee.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						if (inetAddress instanceof Inet4Address) {
							String serverAddress = ((Inet4Address) inetAddress)
									.getHostAddress().toString();

							listServerIP.add(serverAddress);
						}
					}
				}
			}
			return listServerIP;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public void sendMessageToServer(String message) {
		new Thread(new ClientThreadSent(message)).start();
	}

	private class ClientThreadSent implements Runnable {
		String read = "";

		public ClientThreadSent(String message) {
			read = message;
			// TODO Auto-generated constructor stub
		}

		public void run() {
			if (isConnectedToServer) {
				try {

					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())),
							true);
					out.println(read);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	private class ClientThreadStart implements Runnable {
		public void run() {
			try {
				InetAddress serverAddr = InetAddress.getByName(SERVERIPADDRESS);
				try {

					socket = new Socket(serverAddr, SERVERPORT);

					isConnectedToServer = true;

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
					// e.printStackTrace();
				}

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isConnectedToServer = false;
			}

		}
	}

}
