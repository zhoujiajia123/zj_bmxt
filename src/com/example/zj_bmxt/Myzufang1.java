package com.example.zj_bmxt;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Myzufang1 extends Activity {
	private static final int CAMERA_CODE = 1;
	private static final int GALLERY_CODE = 2;
	JSONObject jsonObject=new JSONObject();
	String uString="http://192.168.191.1:8080/test02/index.php/Home/InterfaceRent/findrent";
	String rent_id,uString2="http://192.168.191.1:8080/test02/index.php/Home/InterfaceRent/change";
	EditText type, room, hall, toilet, meter, layer, location, community, price, title, phone;
	Button tijiao;
	ImageView tupian;
	Bitmap bm;
	ProgressDialog dialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
				break;
			case 1:
				
				tranfInfo("["+msg.obj.toString()+"]");
				type.setText(House.type);
				room.setText(House.room);
				hall.setText(House.hall);
				toilet.setText(House.toilet);
				meter.setText(House.meter);
				layer.setText(House.layer);
				location.setText(House.location);
				community.setText(House.community);
				price.setText(House.price);
				title.setText(House.title);
				phone.setText(House.phone);
				tupian.setImageBitmap(getHttpBitmap(House.img1));
				dialog.dismiss();
				break;
			default:
				break;
			}
		}
	};
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
				break;
			case 1:
				if(msg.obj.toString().equals("1")){
				AlertDialog.Builder builder=new AlertDialog.Builder(Myzufang1.this);
				builder
				.setTitle("修改成功")
				.setIcon(R.drawable.duigou)
				.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						finish();
					}
				})
				.create().show();
				}
				else{
					Toast.makeText(getApplication(), "修改失败", Toast.LENGTH_SHORT).show();
				}
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
		setContentView(R.layout.activity_myzufang1);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		type = (EditText) findViewById(R.id.type);
		room = (EditText) findViewById(R.id.room);
		hall = (EditText) findViewById(R.id.hall);
		toilet = (EditText) findViewById(R.id.toilet);
		meter = (EditText) findViewById(R.id.meter);
		layer = (EditText) findViewById(R.id.layer);
		location = (EditText) findViewById(R.id.location);
		community = (EditText) findViewById(R.id.community);
		price = (EditText) findViewById(R.id.price);
		title = (EditText) findViewById(R.id.title);
		phone = (EditText) findViewById(R.id.phone);
		tijiao = (Button) findViewById(R.id.tijiao);
		tupian = (ImageView) findViewById(R.id.tupian);
		
		Intent intent=getIntent();
		rent_id=intent.getStringExtra("rent_id");
		dialog=ProgressDialog.show(this, "", "玩儿命加载中(^ * ^)....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.postandgetHttpInfo(uString, handler, "userid", Person.id, "rent_id", rent_id);
			}
		}).start();
		
		tijiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							jsonObject.put("userid", Person.id);
							jsonObject.put("rent_id", rent_id);
							jsonObject.put("type", type.getText().toString());
							jsonObject.put("room", room.getText().toString());
							jsonObject.put("hall", hall.getText().toString());
							jsonObject.put("toilet", toilet.getText().toString());
							jsonObject.put("meter", meter.getText().toString());
							jsonObject.put("layer", layer.getText().toString());
							jsonObject.put("location", location.getText().toString());
							jsonObject.put("community", community.getText().toString());
							jsonObject.put("price", price.getText().toString());
							jsonObject.put("title", title.getText().toString());
							jsonObject.put("phone", phone.getText().toString());
							jsonObject.put("picture", convertIconToString(bm));
							HttpConnInfo.postandgetHttpInfo2(uString2, handler2,  "data", jsonObject.toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		
		tupian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeImage();
			}
		});
	}
	
	public void tranfInfo(String string){			
		try {
			JSONArray jsonArray=new JSONArray(string);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				House.title=jsonObject.getString("rent_title");
				House.type=jsonObject.getString("rent_type");
				House.price=jsonObject.getString("rent_pay");
				House.room=jsonObject.getString("rent_room");
				House.hall=jsonObject.getString("rent_hall");
				House.toilet=jsonObject.getString("rent_toilet");
				House.meter=jsonObject.getString("rent_meters");
				House.layer=jsonObject.getString("rent_layer");
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
	
	public void changeImage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(new String[] { "拍照", "从图库中选择" }, new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					chooseFromCamera();
					break;
				case 1:
					chooseFromGallery();
					break;
				}
			}
		}).create().show();
	}
	
	private void chooseFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, CAMERA_CODE);
	}

	private void chooseFromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, GALLERY_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CAMERA_CODE:
			if (data == null) {
				return;
			} else {
				Bundle extras = data.getExtras();
				if (extras != null) {
					bm = extras.getParcelable("data");
					tupian.setImageBitmap(bm);
				}
			}
			break;
		case GALLERY_CODE:
			if (data == null) {
				return;
			} else {
				Uri uri = data.getData();
				ContentResolver cr = this.getContentResolver();
				try {
					bm = BitmapFactory.decodeStream(cr.openInputStream(uri));
					tupian.setImageBitmap(bm);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

	public String convertIconToString(Bitmap bitmap) {
		if (bitmap != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] appicon = baos.toByteArray();// 转为byte数组
			return Base64.encodeToString(appicon, Base64.DEFAULT);
		} else {
			return "no";
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.myzufang1, menu);
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
