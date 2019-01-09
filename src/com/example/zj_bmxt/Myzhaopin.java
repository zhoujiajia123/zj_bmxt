package com.example.zj_bmxt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Myzhaopin extends Activity implements OnClickListener{
	Intent intent;
	Button tou,shou,gai;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myzhaopin);
		intent=new Intent();
		tou=(Button)findViewById(R.id.tou);
		shou=(Button)findViewById(R.id.shou);
		gai=(Button)findViewById(R.id.gai);
		tou.setOnClickListener(this);
		shou.setOnClickListener(this);
		gai.setOnClickListener(this);
	}
	
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.tou:
			intent.setClass(Myzhaopin.this, Touzhaopin.class);
			startActivity(intent);
			break;
		case R.id.shou:
			intent.setClass(Myzhaopin.this, Shouzhaopin.class);
			startActivity(intent);		
			break;
		case R.id.gai:
			intent.setClass(Myzhaopin.this, Gaizhaopin.class);
			startActivity(intent);	
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.myzhaopin, menu);
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
