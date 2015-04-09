package com.codemaster.apps.mouse.app;

import com.codemaster.apps.mouse.model.ClientHelper;

import android.app.Application;

public class MouseAndroid extends Application {

	private static ClientHelper ClientHelper;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public static ClientHelper getClientInstance() {
		if (ClientHelper != null)
			return ClientHelper;
		else {
			ClientHelper = new ClientHelper();
			return ClientHelper;
		}
	}
}