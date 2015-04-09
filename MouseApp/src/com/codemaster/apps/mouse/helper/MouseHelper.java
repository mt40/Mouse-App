package com.codemaster.apps.mouse.helper;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import com.codemaster.apps.mouse.app.MouseApp;

public class MouseHelper {

	public static void MouseSignalHandler(String message) {
		if (!message.equals("touched") && !message.equals("released")) {

			if (message.equals("Left Clicked")) {
				MouseApp.getMouseController().mousePress(
						InputEvent.BUTTON1_MASK);
				MouseApp.getMouseController().mouseRelease(
						InputEvent.BUTTON1_MASK);
			} else if (message.equals("Right Clicked")) {
				MouseApp.getMouseController().mousePress(
						InputEvent.BUTTON3_MASK);
				MouseApp.getMouseController().mouseRelease(
						InputEvent.BUTTON3_MASK);
			} else if (message.equals("Scrolling up")) {
				MouseApp.getMouseController().mouseWheel(-1);

			} else if (message.equals("Scrolling down")) {
				MouseApp.getMouseController().mouseWheel(1);
			} else if (message.equals("NextSlide")) {
				MouseApp.getMouseController().keyPress(KeyEvent.VK_PAGE_DOWN);
				MouseApp.getMouseController().keyRelease(KeyEvent.VK_PAGE_DOWN);
			} else if (message.equals("BackSlide")) {
				MouseApp.getMouseController().keyPress(KeyEvent.VK_PAGE_UP);
				MouseApp.getMouseController().keyRelease(KeyEvent.VK_PAGE_UP);

			} else if (message.equals("StartSlide")) {
				MouseApp.getMouseController().keyPress(KeyEvent.VK_F5);
				MouseApp.getMouseController().keyRelease(KeyEvent.VK_F5);

			} else if (message.equals("EndSlide")) {
				MouseApp.getMouseController().keyPress(KeyEvent.VK_ESCAPE);
				MouseApp.getMouseController().keyRelease(KeyEvent.VK_ESCAPE);

			} else {
				Point p = MouseInfo.getPointerInfo().getLocation();
				Float dx = (float) 0, dy = (float) 0;
				Scanner input = new Scanner(message);

				if (input.hasNextFloat())
					dx = input.nextFloat();
				if (input.hasNextFloat())
					dy = input.nextFloat();

				MouseApp.getMouseController().mouseMove(
						p.x + (int) (dx.intValue() * 3),
						p.y + (int) (dy.intValue() * 3));
				input.close();
			}

		}

	}
}