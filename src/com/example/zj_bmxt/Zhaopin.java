package com.example.zj_bmxt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Zhaopin extends Activity implements OnClickListener{
	Button zhaopinbt1,zhaopinbt2;
	Intent intent1;
	Intent intent2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhaopin);
		zhaopinbt1=(Button)findViewById(R.id.zhaopinbt1);
		zhaopinbt2=(Button)findViewById(R.id.zhaopinbt2);
		zhaopinbt1.setOnClickListener(this);
		zhaopinbt2.setOnClickListener(this);
		intent1=new Intent(Zhaopin.this, Getjob.class);
		intent2=new Intent(Zhaopin.this, Providejob.class);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.zhaopinbt1:
			startActivity(intent1);
			break;
		case R.id.zhaopinbt2:
			startActivity(intent2);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zhaopin, menu);
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
