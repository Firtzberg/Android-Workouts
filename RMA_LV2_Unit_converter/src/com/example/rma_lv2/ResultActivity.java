package com.example.rma_lv2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_result);

		Bundle Extra = getIntent().getExtras();
		((TextView) findViewById(R.id.original)).setText(Float.toString(Extra
				.getFloat(Constants.EXTRA_ORIGINAL_VALUE))
				+ Extra.getString(Constants.EXTRA_OLD_UNIT_SYMBOL));
		((TextView) findViewById(R.id.converted)).setText(Float.toString(Extra
				.getFloat(Constants.EXTRA_CONVERTED_VALUE))
				+ Extra.getString(Constants.EXTRA_NEW_UNIT_SYMBOL));
		((TextView) findViewById(R.id.equality)).setText("=");

	}

}
