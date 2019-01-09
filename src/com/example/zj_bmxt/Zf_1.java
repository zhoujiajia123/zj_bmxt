package com.example.zj_bmxt;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Zf_1 extends Activity {
	ProgressDialog dialog;
	Button call;
	String ti;
	String id, uString="http://192.168.191.1:8080/test02/index.php/Home/InterfaceRent/find";
	TextView title,type,price,base,location,community,phone;
	ImageView img1;
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				break;
			case 1:				
				tranfInfo("["+message.obj.toString()+"]");
				Log.e("tag", message.obj.toString());
				title.setText(House.title);
				type.setText(House.type);
				price.setText(House.price);
				base.setText(House.base);
				location.setText(House.location);
				community.setText(House.community);
				phone.setText(House.phone);
				House.pic1=getHttpBitmap(House.img1);
				img1.setImageBitmap(House.pic1);
				dialog.dismiss();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zf_1);
		if (android.os.Build.VERSION.SDK_INT > 9) {
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	}
		title=(TextView)findViewById(R.id.title);
		type=(TextView)findViewById(R.id.type);
		price=(TextView)findViewById(R.id.price);
		base=(TextView)findViewById(R.id.base);
		location=(TextView)findViewById(R.id.location);
		community=(TextView)findViewById(R.id.community);
		phone=(TextView)findViewById(R.id.phone);
		img1=(ImageView)findViewById(R.id.img1);
		call=(Button)findViewById(R.id.call);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		Log.e("tag", id);
		dialog=ProgressDialog.show(this, "", "加载中(^ * ^)....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub				
				
				Log.e("tag", id);
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "id", id);
			}
		}).start();
		
		call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + phone.getText().toString()));
				startActivity(intent);
			}
		});
		
		img1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("clickpic", "4");
				intent.setClass(Zf_1.this, Showpicture.class);
				startActivity(intent);
			}
		});

	}
	
	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				House.title=jsonObject.getString("rent_title");
				House.type=jsonObject.getString("rent_type");
				House.price=jsonObject.getString("rent_pay");
				House.base=jsonObject.getString("base");
				House.location=jsonObject.getString("rent_allocation");
				House.community=jsonObject.getString("rent_community");
				House.phone=jsonObject.getString("rent_phone");
				House.img1=jsonObject.getString("rent_imag1");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Bitmap getHttpBitmap(String url) {

		try {
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setUseCaches(true); // 使用缓存
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("charset", "utf-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			conn.disconnect();
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zf_1, menu);
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
