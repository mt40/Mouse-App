package com.codemaster.apps.mouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;



public class ServerHelper {

	public interface MessageReceivedListener {
		public void onMessageReceived(String message);
		
	}

	public interface ConnectionLossListener {
		public void onConnectionLoss(Exception e);
	}
	
	public interface ServerStartedListener {
		public void onServerStarted();
	}
	
	public interface DeviceConnectedListener {
		public void onDeviceConnected();
	}

	private ServerSocket serverSocket;
	private Socket client;
	private String SERVERIP;
	private int SERVERPORT;

	private boolean isServerPublished;
	private boolean isConnectedToClient;

	private MessageReceivedListener messageHandler;
	private ServerStartedListener serverStartHandler;
	private DeviceConnectedListener deviceConnectedHandler;
	private ConnectionLossListener connectionHandler;

	public ServerHelper() {
		serverSocket = null;
		client = null;
		SERVERIP = "10.0.0.15";
		SERVERPORT = 5000;

		isServerPublished = false;
		isConnectedToClient = false;
	}

	public void setServerPort(int port) {
		SERVERPORT = port;
	}

	public void setOnMessageReceivedListener(MessageReceivedListener func) {
		messageHandler = func;
	}

	public void setOnConnectionLossListener(ConnectionLossListener func) {
		connectionHandler = func;
	}
	
	public void setOnServerStartedListener(ServerStartedListener func) {
		serverStartHandler = func;
	}
	
	public void setOnDeviceConnectedListener(DeviceConnectedListener func) {
		deviceConnectedHandler = func;
	}

	public void startServer() {
		SERVERIP = getLocalIpAddress();
		if (SERVERIP != null) {
			new Thread(new StartServerAsync()).start();
		}
	}

	public void connectToClient() {
		if (isServerPublished) {
			new Thread(new ConnectToClientAsync()).start();
			
		}
	}

	public void startReceiveMessage() {
		if (isConnectedToClient) {
			new Thread(new ReceiveMessageAsync()).start();
		}
	}

	
	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						if (inetAddress instanceof Inet4Address) {
							return ((Inet4Address) inetAddress)
									.getHostAddress().toString();
						}
					}
				}
			}
		} catch (SocketException ex) {
			//Log.e("ServerActivity", ex.toString());
		}
		return null;
	}

	private class StartServerAsync implements Runnable {

		@Override
		public void run() {
			try {
				// Establish a new server with port: SERVERPORT
				serverSocket = new ServerSocket(SERVERPORT);
				serverStartHandler.onServerStarted();
			} catch (Exception e) {
				isServerPublished = false;
			}
			isServerPublished = true;
		}
	}

	private class ConnectToClientAsync implements Runnable {

		@Override
		public void run() {
			try {
				client = serverSocket.accept();
				isConnectedToClient = true;
				System.out.println("Connected to client");
				if (deviceConnectedHandler != null)
				deviceConnectedHandler.onDeviceConnected();
			} catch (IOException e) {
				isConnectedToClient = false;
			} catch (Exception e) {
				isConnectedToClient = false;
				System.out.println(e.toString());
			}
		}

	}

	private class ReceiveMessageAsync implements Runnable {

		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					// Log.d("ServerActivity", line);
					final String message = line;
					//Print the message to console
					System.out.println(message);
					new Thread(new Runnable() {

						@Override
						public void run() {
							messageHandler.onMessageReceived(message);
						}
					}).start();
				}
			} catch (Exception e) {
				isConnectedToClient = false;
				connectionHandler.onConnectionLoss(e);
			}
		}
	}
}