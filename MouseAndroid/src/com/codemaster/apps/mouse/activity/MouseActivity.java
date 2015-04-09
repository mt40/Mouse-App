package com.codemaster.apps.mouse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.codemaster.apps.mouse.R;
import com.codemaster.apps.mouse.app.MouseAndroid;
import com.codemaster.apps.mouse.helper.TouchHelper;

public class MouseActivity extends Activity {

	private FrameLayout touchInterface;
	private ImageButton keyboardBtn;
	int GLOBAL_TOUCH_POSITION_Y = 0;
	int GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mouse);
		touchInterface = (FrameLayout) findViewById(R.id.frameLayout_Mouse);
		touchInterface.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent e) {
				TouchHelper.TouchHandler(v, e);
				return true;
			}
		});

		final EditText edittext = (EditText) findViewById(R.id.messageInput);
		edittext.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					String s = edittext.getText().toString();
					MouseAndroid.getClientInstance().sendMessageToServer(s);
					edittext.setText("");
				}
				return false;
			}
		});

	};

}
