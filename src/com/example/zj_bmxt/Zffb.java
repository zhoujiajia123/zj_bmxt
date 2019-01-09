package com.example.zj_bmxt;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Zffb extends Activity {
	private static final int CAMERA_CODE = 1;
	private static final int GALLERY_CODE = 2;
	String uString = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceRent/add";
	List<Map<String, Object>> list;
	ImageButton tijiao, picktu;
	ImageView gettu;
	ListView flv;
	ProgressDialog dialog;
	Bitmap bm;
	JSONObject jsonObject = new JSONObject();
	String[] form = new String[] { "rent_type", "rent_room", "rent_hall", "rent_toilet", "rent_meters", "rent_layer",
			"rent_allocation", "rent_community", "rent_pay", "rent_title", "rent_phone" };
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				dialog.dismiss();
				if (msg.obj.toString().equals("1")) {
					AlertDialog.Builder builder=new AlertDialog.Builder(Zffb.this);
					builder
					.setTitle("发布成功等待审核")
					.setIcon(R.drawable.duigou)
					.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							finish();
						}
					})
					.create().show();
				} else {
					Toast.makeText(getApplication(), "提交失败", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.activity_zffb);
		flv = (ListView) findViewById(R.id.flv);
		tijiao = (ImageButton) findViewById(R.id.tijiao);
		picktu = (ImageButton) findViewById(R.id.picktu);
		gettu = (ImageView) findViewById(R.id.gettu);
		list = new ArrayList<Map<String, Object>>();
		String[] shuxing = new String[] { "出租类型(整套/单间):", "房间数量:", "客厅数量:", "厕所数量:", "面积:", "层数:", "区域:", "小区:",
				"出租价格(月):", "标题(特色):", "电话:" };

		for (int i = 0; i < 11; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("t0", shuxing[i]);
			list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.d_zffb, new String[] { "t0", "t1" },
				new int[] { R.id.rent_type, R.id.et_type });
		flv.setAdapter(adapter);

		tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getForm();
				dialog=ProgressDialog.show(Zffb.this, "", "玩儿命加载中(^ * ^)....");
				try {
					jsonObject.put("id", Person.id);
					jsonObject.put("picture", convertIconToString(bm));

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						String content = String.valueOf(jsonObject);
						Log.e("tag", content);
						HttpConnInfo.postandgetHttpInfo2(uString, handler, "data", content);
					}
				}).start();
			}
		});

		picktu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeImage();
			}
		});

	}

	public void getForm() {
		for (int i = 0; i < 11; i++) {
			LinearLayout layout = (LinearLayout) flv.getChildAt(i);
			EditText editText = (EditText) layout.findViewById(R.id.et_type);
			try {
				jsonObject.put(form[i], editText.getText().toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
					gettu.setImageBitmap(bm);
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
					gettu.setImageBitmap(bm);
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
		getMenuInflater().inflate(R.menu.zffb, menu);
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
