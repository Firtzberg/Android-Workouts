package com.example.lifecycleapp;

import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	this.addAction(Method.onCreate);
    }
    @Override
    public void onPause(){
    	super.onPause();
    	this.addAction(Method.onPause);
    }
    @Override
    public void onResume(){
    	super.onResume();
    	this.addAction(Method.onResume);
    }
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	this.addAction(Method.onDestroy);
    }
    @Override
    public void onStart(){
    	super.onStart();
    	this.addAction(Method.onStart);
    }
    @Override
    public void onRestart(){
    	super.onRestart();
    	this.addAction(Method.onRestart);
    }
    @Override
    public void onStop(){
    	super.onStop();
    	this.addAction(Method.onStop);
    }
    public void Click(View w){
    	this.addAction(Method.onClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    private void addAction(Method m) {
    	Date time = new Date();
    	String Des;
    	int c;
   		switch(m){
    		case onCreate:
    			Des = "onCreate";
    			c = Color.WHITE;
    			break;
    		case onStart:
    			Des="onStart";
    			c=Color.GREEN;
    			break;
    		case onResume:
    			Des="onResume";
    			c=Color.YELLOW;
    			break;
    		case onStop:
    			Des="onStop";
    			c=Color.RED;
    			break;
    		case onDestroy:
    			Des="onDestroy";
    			c=Color.BLACK;
    			break;
    		case onPause:
    			Des="onPause";
    			c=Color.MAGENTA;
    			break;
    		case onRestart:
    			Des="onRestart";
    			c=Color.BLUE;
    			break;
    		default:
    			Des="onClick";
    			c=Color.WHITE;
   		}
   		LinearLayout child = new LinearLayout(this);
   		child.setOrientation(LinearLayout.HORIZONTAL);
   		child.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
   		TextView tw = new TextView(this);
   		tw.setBackgroundColor(0xff333333);
   		tw.setTextColor(0xffdddddd);
   		tw.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 5f));
   		tw.setText(time.toString());
   		child.addView(tw);
   		tw = new TextView(this);
   		tw.setBackgroundColor(c);
   		tw.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 2f));
   		tw.setText("Action: "+Des+"()");
   		child.addView(tw);
   		LinearLayout ll = (LinearLayout)findViewById(R.id.linlay);
   		ll.addView(child);
    }
}
