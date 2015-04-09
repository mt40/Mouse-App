package com.codemaster.apps.mouse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.codemaster.apps.customview.RoundMenuItem;
import com.codemaster.apps.customview.RoundExpandableMenu;
import com.codemaster.apps.mouse.R;

public class TestViewActivity extends Activity {
	
	RoundExpandableMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_viewtest);
		
		menu = (RoundExpandableMenu)findViewById(R.id.custom1);
		menu.setCoreImage(this, R.drawable.defend);
		
		RoundMenuItem item1 = new RoundMenuItem(this, R.drawable.thunder, menu);
		RoundMenuItem item2 = new RoundMenuItem(this, R.drawable.thunder, menu);
		RoundMenuItem item3 = new RoundMenuItem(this, R.drawable.thunder, menu);
		RoundMenuItem item4 = new RoundMenuItem(this, R.drawable.thunder, menu);
		RoundMenuItem item5 = new RoundMenuItem(this, R.drawable.thunder, menu);
		
		menu.addMenuItem(item1);
		menu.addMenuItem(item2);
		menu.addMenuItem(item3);
		menu.addMenuItem(item4);
		menu.addMenuItem(item5);
		
		menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				menu.toggle();
			}
		});
		
		super.onCreate(savedInstanceState);
	}
	
}