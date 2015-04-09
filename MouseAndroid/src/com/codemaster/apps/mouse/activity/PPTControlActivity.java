package com.codemaster.apps.mouse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.codemaster.apps.mouse.R;
import com.codemaster.apps.mouse.app.MouseAndroid;

public class PPTControlActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ppt_control_activity);
		Button btnNext = (Button) findViewById(R.id.nextBtn);
		Button btnBack = (Button) findViewById(R.id.backBtn);
		Button btnStart = (Button) findViewById(R.id.StartBtn);
		Button btnEnd = (Button) findViewById(R.id.EndBtn);
		btnNext.setOnClickListener(btnListener);
		btnBack.setOnClickListener(btnListener);
		btnStart.setOnClickListener(btnListener);
		btnEnd.setOnClickListener(btnListener);
	}

	private OnClickListener btnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String s = "";
			s = ((Button) v).getText().toString();
			if (s.equals("NEXT")) {
				MouseAndroid.getClientInstance().sendMessageToServer(
						"NextSlide");
			} else if (s.equals("START")) {
				MouseAndroid.getClientInstance().sendMessageToServer(
						"StartSlide");
			} else if (s.equals("END")) {
				MouseAndroid.getClientInstance()
						.sendMessageToServer("EndSlide");
			} else {
				MouseAndroid.getClientInstance().sendMessageToServer(
						"BackSlide");
			}

		}
	};

}
