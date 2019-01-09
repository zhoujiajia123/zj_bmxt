package com.example.zj_bmxt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

public class Showpicture extends Activity {
	Bitmap bitmap;
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_showpicture);
		img=(ImageView)findViewById(R.id.picture);
		Intent intent=getIntent();
		String getstring=intent.getStringExtra("clickpic");
		int type=Integer.parseInt(getstring);
		switch (type) {
		case 1:
			img.setImageBitmap(TravelBean.bitmap1);
			break;
		case 2:
			img.setImageBitmap(TravelBean.bitmap2);
			break;
		case 3:
			img.setImageBitmap(TravelBean.bitmap3);
			break;
		case 4:
			img.setImageBitmap(House.pic1);
			break;
		default:
			break;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.showpicture, menu);
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
