package com.example.rma_lv2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		if(v.getClass() != ImageView.class)
			return;
		Intent i = new Intent();
		i.setClass(this, SelectActivity.class);
		i.putExtra(Constants.EXTRA_QUANTITY_ID, v.getId());
		startActivity(i);
	}
}
