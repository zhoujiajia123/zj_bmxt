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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Travelview extends Activity {
	String siteid, result;
	String uString = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceLygl/find";
	String uPic1 = "";
	String uPic2 = "";
	String uPic3 = "";
	String uXin = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceLygl/dianzan";
	Bitmap b1, b2, b3;
	TravelBean travelBean;
	String sname;
	TextView name, type, open, ticket, introduce, play, phone;
	ImageView img1, img2, img3;
	ImageButton xin;
	ProgressDialog dialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {// 此方法在ui线程运行
			if (msg.what == 0) {
				Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_LONG).show();
			} else {
				result = msg.obj.toString();
				result = "[" + result + "]";
				tranfInfo(result);
				uPic1 = travelBean.getImg1();
				uPic2 = travelBean.getImg2();
				uPic3 = travelBean.getImg3();
				name.setText(travelBean.getName());
				type.setText(travelBean.getType());
				open.setText(travelBean.getOpen());
				ticket.setText(travelBean.getTicket());
				introduce.setText(travelBean.getIntroduce());
				play.setText(travelBean.getPlay());
				phone.setText(travelBean.getPhone());
				TravelBean.bitmap1 = getHttpBitmap(uPic1);
				TravelBean.bitmap2 = getHttpBitmap(uPic2);
				TravelBean.bitmap3 = getHttpBitmap(uPic3);
				img1.setImageBitmap(TravelBean.bitmap1);
				img2.setImageBitmap(TravelBean.bitmap2);
				img3.setImageBitmap(TravelBean.bitmap3);
				dialog.dismiss();
			}
		}
	};
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {// 此方法在ui线程运行
			if (msg.what == 0) {
				Toast.makeText(getApplication(), "网络连接失败", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplication(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_travelview);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		name = (TextView) findViewById(R.id.travel_name);
		type = (TextView) findViewById(R.id.travel_type);
		open = (TextView) findViewById(R.id.travel_open);
		ticket = (TextView) findViewById(R.id.travel_ticket);
		introduce = (TextView) findViewById(R.id.travel_introduce);
		play = (TextView) findViewById(R.id.travel_play);
		phone = (TextView) findViewById(R.id.travel_phone);
		img1 = (ImageView) findViewById(R.id.img1);
		img2 = (ImageView) findViewById(R.id.img2);
		img3 = (ImageView) findViewById(R.id.img3);
		xin = (ImageButton) findViewById(R.id.xin);
		Intent intent = getIntent();
		siteid = intent.getStringExtra("id");
		travelBean = new TravelBean();
		dialog = ProgressDialog.show(this, "", "玩儿命加载中(^ * ^)....");
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "id", siteid);
			}
		}).start();

		xin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Person.login == 0) {
					Toast.makeText(getApplication(), "此功能需要登录", Toast.LENGTH_SHORT).show();
				} else {
					xin.setBackgroundResource(R.drawable.redxin);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							HttpConnInfo.postandgetHttpInfo(uXin, handler2, "siteid", siteid, "userid", Person.id);
						}
					}).start();
				}
			}
		});

		img1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				if(TravelBean.bitmap1==null){
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("clickpic", "1");
				intent.setClass(Travelview.this, Showpicture.class);
				startActivity(intent);
			}
		});
		
		img2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				if(TravelBean.bitmap2==null){
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("clickpic", "2");
				intent.setClass(Travelview.this, Showpicture.class);
				startActivity(intent);
			}
		});
		
		img3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				if(TravelBean.bitmap3==null){
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("clickpic", "3");
				intent.setClass(Travelview.this, Showpicture.class);
				startActivity(intent);
			}
		});
	}

	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				travelBean.setName(jsonObject.getString("lygl_name"));
				travelBean.setIntroduce(jsonObject.getString("lygl_introduce"));
				travelBean.setType(jsonObject.getString("lygl_type"));
				travelBean.setPhone(jsonObject.getString("lygl_phone"));
				travelBean.setPlay(jsonObject.getString("lygl_play"));
				travelBean.setOpen(jsonObject.getString("lygl_open"));
				travelBean.setTicket(jsonObject.getString("lygl_ticket"));
				travelBean.setImg1(jsonObject.getString("lygl_imag1"));
				travelBean.setImg2(jsonObject.getString("lygl_imag2"));
				travelBean.setImg3(jsonObject.getString("lygl_imag3"));
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
		getMenuInflater().inflate(R.menu.travelview, menu);
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
