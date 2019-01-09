package com.example.zj_bmxt;

import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.LinearLayout;
import android.widget.Toast;

public class Addresume extends Activity {
	private static final int CAMERA_CODE = 1;
	private static final int GALLERY_CODE = 2;
	private static final int CROP_CODE = 3;
	String uString = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceJob/addstaff";
	EditText resume, name, education, address, sex, phone, want, introduce;
	ImageView rtouxiang;
	Button tijiao;
	Bitmap bm;
	JSONObject jsonObject = new JSONObject();
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				if(message.obj.toString().equals("1")){
					AlertDialog.Builder builder=new AlertDialog.Builder(Addresume.this);
					builder
					.setTitle("添加成功")
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
					
				}
				
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addresume);
		resume = (EditText) findViewById(R.id.resume);
		name = (EditText) findViewById(R.id.name);
		education = (EditText) findViewById(R.id.education);
		address = (EditText) findViewById(R.id.address);
		sex = (EditText) findViewById(R.id.sex);
		phone = (EditText) findViewById(R.id.phone);
		want = (EditText) findViewById(R.id.want);
		introduce = (EditText) findViewById(R.id.introduce);
		rtouxiang = (ImageView) findViewById(R.id.rtouxiang);
		tijiao = (Button) findViewById(R.id.tijiao);

		tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Resumebean.resume = resume.getText().toString();
						Resumebean.name = name.getText().toString();
						Resumebean.education = education.getText().toString();
						Resumebean.address = address.getText().toString();
						Resumebean.sex = sex.getText().toString();
						Resumebean.phone = phone.getText().toString();
						Resumebean.want = want.getText().toString();
						Resumebean.introduce = introduce.getText().toString();
						getForm();
						HttpConnInfo.postandgetHttpInfo2(uString, handler, "data", jsonObject.toString());
					}
				}).start();
			}
		});

		rtouxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeImage();
			}
		});

	}

	public void getForm() {
		try {
			jsonObject.put("id", Person.id);
			jsonObject.put("resume", Resumebean.resume);
			jsonObject.put("name", Resumebean.name);
			jsonObject.put("education", Resumebean.education);
			jsonObject.put("address", Resumebean.address);
			jsonObject.put("sex", Resumebean.sex);
			jsonObject.put("phone", Resumebean.phone);
			jsonObject.put("want", Resumebean.want);
			jsonObject.put("introduce", Resumebean.introduce);
			jsonObject.put("picture", convertIconToString(bm));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// 用户点击了取消
			if (data == null) {
				return;
			} else {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap bm = extras.getParcelable("data");
					// Uri uri = saveBitmap(bm, "temp");
					//startImageZoom(uri);
				}
			}
			break;
		case GALLERY_CODE:
			if (data == null) {
				return;
			} else {
				Uri uri = data.getData();
				startImageZoom(uri);
			}
			break;
		case CROP_CODE:
			if (data == null) {
				return;
			} else {
				Bundle extras = data.getExtras();
				if (extras != null) {
					bm = extras.getParcelable("data");
					rtouxiang.setImageBitmap(bm);
				}
			}
			break;
		default:
			break;
		}
	}

	private void startImageZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_CODE);
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
		getMenuInflater().inflate(R.menu.addresume, menu);
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
