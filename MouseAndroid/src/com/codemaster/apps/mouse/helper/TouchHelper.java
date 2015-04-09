package com.codemaster.apps.mouse.helper;

import android.view.MotionEvent;
import android.view.View;

import com.codemaster.apps.mouse.app.MouseAndroid;

public class TouchHelper {
	static int GLOBAL_TOUCH_POSITION_Y = 0;
	static int GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;
	static float mPreviousX = 0;
	static float mPreviousY = 0;
	static String PreviousEvent = "";

	public static void TouchHandler(View v, MotionEvent e) {
		// TODO Auto-generated method stub
		float x = e.getX();
		float y = e.getY();
		int pointerCount = e.getPointerCount();

		if (pointerCount == 2) {
			int action = e.getActionMasked();
			String actionString;

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				GLOBAL_TOUCH_POSITION_Y = (int) e.getY(1);

				break;
			case MotionEvent.ACTION_UP:
				GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;

				break;
			case MotionEvent.ACTION_MOVE:
				GLOBAL_TOUCH_CURRENT_POSITION_Y = (int) e.getY(1);
				int diff = GLOBAL_TOUCH_POSITION_Y
						- GLOBAL_TOUCH_CURRENT_POSITION_Y;
				if (diff > 0) {
					actionString = "Scrolling up";
				} else {
					actionString = "Scrolling down";
				}
				PreviousEvent = actionString;
				MouseAndroid.getClientInstance().sendMessageToServer(
						actionString);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				GLOBAL_TOUCH_POSITION_Y = (int) e.getY(1);
				actionString = "2 fingers touched";
				PreviousEvent = "2 fingers tocched";
				MouseAndroid.getClientInstance().sendMessageToServer(
						actionString);
				break;

			default:
				actionString = "";
			}

			pointerCount = 0;

		} else {
			GLOBAL_TOUCH_POSITION_Y = 0;
			GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;

			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mPreviousX = e.getX();
				mPreviousY = e.getY();
				MouseAndroid.getClientInstance().sendMessageToServer("touched");
				PreviousEvent = "touched";

				break;

			case MotionEvent.ACTION_MOVE:
				Float dx = x - mPreviousX;
				Float dy = y - mPreviousY;
				if (PreviousEvent == "Scrolling up"
						|| PreviousEvent == "Scrolling down")
					break;
				PreviousEvent = "Move";
				MouseAndroid.getClientInstance().sendMessageToServer(
						dx.toString() + " " + dy.toString());
				break;

			case MotionEvent.ACTION_UP:
				mPreviousX = e.getX();
				mPreviousY = e.getY();
				if (PreviousEvent == "touched")
					MouseAndroid.getClientInstance().sendMessageToServer(
							"Left Clicked");
				else if (PreviousEvent == "2 fingers tocched")
					MouseAndroid.getClientInstance().sendMessageToServer(
							"Right Clicked");
				else
					MouseAndroid.getClientInstance().sendMessageToServer(
							"released");
				
				break;

			default:
				break;
			}

			mPreviousX = x;
			mPreviousY = y;
			//abc

		}

	}
}
