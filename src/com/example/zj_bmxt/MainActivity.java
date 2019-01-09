package com.example.zj_bmxt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class MainActivity extends Activity {
	SharedPreferences preferences;
	private final int TIME=1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		preferences=getSharedPreferences("info", MODE_PRIVATE);
		Person.login=preferences.getInt("login", 0);
		Person.id=preferences.getString("id", "");
		new Handler().postDelayed(new Runnable(){
	    	public void run(){
	    		Intent intent=new Intent(MainActivity.this,PrimaryView.class);
	    		MainActivity.this.startActivity(intent);
	    		finish();
	    	}
	    },TIME);	
	     
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
