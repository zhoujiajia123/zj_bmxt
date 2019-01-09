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

public class School extends Activity implements OnClickListener{
	Button schoolbt1,schoolbt2,schoolbt3;
	Intent intent=new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_school);
		intent.setClass(School.this, Diffschool.class);
		schoolbt1=(Button)findViewById(R.id.schoolbt1);
		schoolbt2=(Button)findViewById(R.id.schoolbt2);
		schoolbt3=(Button)findViewById(R.id.schoolbt3);
		schoolbt1.setOnClickListener(this);
		schoolbt2.setOnClickListener(this);
		schoolbt3.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.schoolbt1:
			intent.putExtra("type", "小学");
			startActivity(intent);
			break;
		case R.id.schoolbt2:
			intent.putExtra("type", "中学");
			startActivity(intent);
			break;
		case R.id.schoolbt3:
			intent.putExtra("type", "大学");
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.school, menu);
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
