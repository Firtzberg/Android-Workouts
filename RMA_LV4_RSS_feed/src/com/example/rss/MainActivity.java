package com.example.rss;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity{
	private static final String NEWS_LIST_PREFERENCE_KEY = "Feed";
	private static final String NEWS_LAST_REFRESH_PREFERENCE_KEY = "LastRefresh";
	NewsAdapter adapter;
	ListView lv;
	Spinner CategorySpinner;
	ArrayAdapter<String> CategoryAdapter;
	ArrayList<News> news = new ArrayList<News>();

	public static final String RSS = "http://www.bug.hr/rss/vijesti/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		NewsAdapter.AllCategories = getString(R.string.all_categories);
		News.defaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		lv = (ListView) findViewById(R.id.lvNews);
		CategorySpinner = (Spinner) findViewById(R.id.spCategory);
		if (adapter == null) {
			adapter = new NewsAdapter(this);
			Date today = new Date();
			long mils = today.getTime();
			long time = mils % 1000 * 60 * 60 * 24;
			today.setTime(mils - time);
			boolean done = false;
			try {
				if (XMLHandler.inputFormat.parse(
						getPreferences(MODE_PRIVATE).getString(
								NEWS_LAST_REFRESH_PREFERENCE_KEY, "")).after(
						today)) {
					populateAdapterFromPreferences();
					done = true;
				}
			} catch (ParseException e) {
			}
			if (!done)
				this.populateAdapterFromWeb();
		}
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				News clicked = (News) adapter.getItem(position);
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						clicked.Link);
				startActivity(browserIntent);
			}
		});
		CategorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				MainActivity.this.adapter.setCategoryFilter(parent.getItemAtPosition(position)
						.toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				MainActivity.this.adapter.setCategoryFilter(null);
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();

		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putString(NEWS_LIST_PREFERENCE_KEY, adapter.ToXML());

		editor.commit();
	}

	private void populateAdapterFromPreferences() {
		adapter.clear();
		try {
			ArrayList<News> list = XMLHandler
					.parseNewsList(new ByteArrayInputStream(getPreferences(
							MODE_PRIVATE).getString(NEWS_LIST_PREFERENCE_KEY,
							"").getBytes("UTF-8")));
			if (list == null) {
				populateAdapterFromWeb();
				return;
			}
			adapter.addRange(list);
			refreshCategories();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void populateAdapterFromWeb() {
		Toast.makeText(this, "Please wait...", Toast.LENGTH_LONG).show();
		AsyncTask<String, Void, ArrayList<News>> Task = new AsyncTask<String, Void, ArrayList<News>>() {

			private Exception exception;

			@Override
			protected ArrayList<News> doInBackground(String... ignore) {
				try {
					return XMLHandler.parseNewsList(new URL(RSS).openStream());
				} catch (Exception e) {
					this.exception = e;
					return null;
				}
			}

			@Override
			protected void onPostExecute(ArrayList<News> newsList) {
				if (exception != null) {
					Toast.makeText(getBaseContext(),
							"Problems connecting to the internet",
							Toast.LENGTH_LONG).show();
					exception.printStackTrace();
					return;
				} else {
					SharedPreferences.Editor editor = getPreferences(
							MODE_PRIVATE).edit();
					editor.putString(NEWS_LAST_REFRESH_PREFERENCE_KEY,
							XMLHandler.inputFormat.format(new Date()));
					editor.commit();
					adapter.clear();
					adapter.addRange(newsList);
					refreshCategories();
				}
			}
		};
		Task.execute();
	}
	
	private void refreshCategories(){
		Object o = CategorySpinner.getSelectedItem();
		String old;
		if(o!= null)
			old = o.toString();
		else old = NewsAdapter.AllCategories;
		CategoryAdapter = new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_spinner_dropdown_item,
				adapter.getCategoryList());
		CategoryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		CategorySpinner.setAdapter(CategoryAdapter);
		int pos = CategoryAdapter.getPosition(old);
		if(pos >-1)
			CategorySpinner.setSelection(pos);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void refresh(View v) {
		populateAdapterFromWeb();
	}
}
