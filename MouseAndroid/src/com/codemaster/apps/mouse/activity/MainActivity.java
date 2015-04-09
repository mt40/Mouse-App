package com.codemaster.apps.mouse.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.codemaster.apps.mouse.R;
import com.codemaster.apps.mouse.app.MouseAndroid;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button) findViewById(R.id.btnTestView))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent i = new Intent(getApplicationContext(),
								TestViewActivity.class);
						startActivity(i);
					}
				});
	}

	public void onClickConnect(View v) {
		EditText serverIP;
		String serverIPAddress = "";
		int serverPort = Integer
				.parseInt(((EditText) findViewById(R.id.ContentInput))
						.getText().toString());
		serverIP = (EditText) findViewById(R.id.ServerAddressInput);
		serverIPAddress = serverIP.getText().toString();
		MouseAndroid.getClientInstance().connectToServer(serverIPAddress,
				serverPort);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(serverIP.getWindowToken(), 0);
	}

	public void onClickPPT(View v) {
		Intent i = new Intent(this, PPTControlActivity.class);
		this.startActivity(i);
	}

	public void onOpenMouseActivity(View v) {
		Intent i = new Intent(this, MouseActivity.class);
		this.startActivity(i);
	}

}
