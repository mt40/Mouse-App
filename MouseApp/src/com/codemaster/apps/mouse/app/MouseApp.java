package com.codemaster.apps.mouse.app;

import java.awt.AWTException;
import java.awt.Robot;

import com.codemaster.apps.mouse.model.ServerHelper;

public class MouseApp {
	
	private static ServerHelper server;
	private static Robot controller;
	
	public static ServerHelper getServerInstance() {
		if (server != null) return server;
		else {
			server = new ServerHelper();
			return server;
		}
	}
	
	public static Robot getMouseController() {
		if (controller != null) return controller;
		else {
			try {
				controller = new Robot();
				return controller;
			} catch (AWTException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}