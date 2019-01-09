package com.example.zj_bmxt;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class PrimaryView extends Activity {
	TextView textview1, textview2, zhaopin, zige, zufang, luyou, xuexiao, qita;
	static Selfinfo selfinfo = new Selfinfo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_primary_view);
		Typeface iconfont1 = Typeface.createFromAsset(getAssets(), "icon/iconfont.ttf");
		textview1 = (TextView) findViewById(R.id.m1);
		textview2 = (TextView) findViewById(R.id.m2);
		zhaopin = (TextView) findViewById(R.id.mttv1);
		zige = (TextView) findViewById(R.id.mttv2);
		zufang = (TextView) findViewById(R.id.mttv3);
		luyou = (TextView) findViewById(R.id.mttv4);
		xuexiao = (TextView) findViewById(R.id.mttv5);
		qita = (TextView) findViewById(R.id.mttv6);
		textview1.setTypeface(iconfont1);
		textview2.setTypeface(iconfont1);
		zhaopin.setTypeface(iconfont1);
		zige.setTypeface(iconfont1);
		zufang.setTypeface(iconfont1);
		luyou.setTypeface(iconfont1);
		xuexiao.setTypeface(iconfont1);
		qita.setTypeface(iconfont1);

		textview2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				textview2.setTextColor(Color.RED);
				textview1.setTextColor(Color.BLACK);
				Changeview();
			}
		});

		textview1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				textview1.setTextColor(Color.RED);
				textview2.setTextColor(Color.BLACK);
				Backview();
			}
		});

		zhaopin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Person.login == 1) {
					Intent intent = new Intent();
					intent.setClass(PrimaryView.this, Zhaopin.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplication(), "此功能需要登录", Toast.LENGTH_SHORT).show();
				}
			}
		});

		zige.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PrimaryView.this, Zige.class);
				startActivity(intent);
			}
		});

		zufang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Person.login == 1) {
					Intent intent = new Intent();
					intent.setClass(PrimaryView.this, Zufang.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplication(), "此功能需要登录", Toast.LENGTH_SHORT).show();
				}

			}
		});

		luyou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Person.login == 1) {
					Intent intent = new Intent();
					intent.setClass(PrimaryView.this, Travel.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplication(), "此功能需要登录", Toast.LENGTH_SHORT).show();
				}
			}

		});

		xuexiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PrimaryView.this, School.class);
				startActivity(intent);
			}
		});

		qita.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PrimaryView.this, Mymap.class);
				startActivity(intent);
			}
		});
	}

	public void Changeview() {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.framelayout, selfinfo);
		transaction.commit();
	}

	public void Backview() {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.remove(selfinfo);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.primary_view, menu);
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
