package hr.nemamdomenu.rasturi;

import java.io.IOException;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private LinearLayout root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.root = (LinearLayout) this.findViewById(R.id.root);
		try {
			ArrayList<InspiringPerson> ips = HandleXML.getInspiringPersons(
					getResources().getAssets().open("Data.xml"),
					getApplicationContext());
			if (ips != null)
				for (InspiringPerson x : ips) {
					this.root.addView(x);
				}
		} catch (IOException e) {
			TextView tv = new TextView(getApplicationContext());
			tv.setText(R.string.data_missing);
			this.root.addView(tv);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
