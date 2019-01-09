package com.example.zj_bmxt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Gaizhaopin1 extends Activity {
	String resumename,resumeid,uString="http://192.168.191.1:8080/test02/index.php/Home/JobResume/findresume";
	String uString2="http://192.168.191.1:8080/test02/index.php/Home/JobResume/changeresume";
	EditText resume,name,education,address,sex,phone,want,introduce;
	ImageView rtouxiang;
	Intent intent;
	Button tijiao;
	JSONObject jsonObject;
	private Handler handler=new Handler(){
		public void handleMessage(Message message){
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				//Toast.makeText(getApplication(), message.obj.toString(), Toast.LENGTH_SHORT).show();
				tranfInfo("["+message.obj.toString()+"]");
				resume.setText(Resumebean.resume);
				name.setText(Resumebean.name);
				education.setText(Resumebean.education);
				address.setText(Resumebean.address);
				sex.setText(Resumebean.sex);
				want.setText(Resumebean.want);
				phone.setText(Resumebean.phone);
				introduce.setText(Resumebean.introduce);
				break;
			default:
				break;
			}
		}
	};
	private Handler handler2=new Handler(){
		public void handleMessage(Message message){
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				//sendSuccess();
				if (message.obj.toString().equals("1")) {
					 AlertDialog.Builder builder=new AlertDialog.Builder(Gaizhaopin1.this);
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
		setContentView(R.layout.activity_gaizhaopin1);
		resume=(EditText)findViewById(R.id.resume);
		name=(EditText)findViewById(R.id.name);
		education=(EditText)findViewById(R.id.education);
		address=(EditText)findViewById(R.id.address);
		sex=(EditText)findViewById(R.id.sex);
		phone=(EditText)findViewById(R.id.phone);
		want=(EditText)findViewById(R.id.want);
		introduce=(EditText)findViewById(R.id.introduce);
		rtouxiang=(ImageView)findViewById(R.id.rtouxiang);
		tijiao=(Button)findViewById(R.id.tijiao);
		intent=getIntent();
		resumename=intent.getStringExtra("resumename");
		resumeid=intent.getStringExtra("resumeid");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("tag", resumeid);
				HttpConnInfo.postandgetHttpInfo(uString, handler, "resumeid", resumeid, "userid", Person.id);
			}
		}).start();
		
		tijiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("tag", resumeid);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						jsonObject=new JSONObject();
						Resumebean.resume=resume.getText().toString();
						Resumebean.name=name.getText().toString();
						Resumebean.education=education.getText().toString();
						Resumebean.address=address.getText().toString();
						Resumebean.sex=sex.getText().toString();
						Resumebean.phone=phone.getText().toString();
						Resumebean.want=want.getText().toString();
						Resumebean.introduce=introduce.getText().toString();
						getForm();
						HttpConnInfo.postandgetHttpInfo2(uString2, handler2, "data", jsonObject.toString());
					}
				}).start();
			}
		});
	}
	
	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Resumebean.resume = jsonObject.getString("job_staff_resume");
				Resumebean.name = jsonObject.getString("job_staff_name");
				Resumebean.education = jsonObject.getString("job_staff_educate");
				Resumebean.address = jsonObject.getString("job_staff_residence");
				Resumebean.sex = jsonObject.getString("job_staff_sex");
				Resumebean.phone = jsonObject.getString("job_staff_phone");
				Resumebean.want = jsonObject.getString("job_staff_intend");
				Resumebean.introduce = jsonObject.getString("job_staff_remark");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getForm(){
		try {
			jsonObject.put("resumeid", resumeid);
			jsonObject.put("userid", Person.id);
			jsonObject.put("resume", Resumebean.resume);
			jsonObject.put("name", Resumebean.name);			
			jsonObject.put("education", Resumebean.education);
			jsonObject.put("address", Resumebean.address);
			jsonObject.put("sex", Resumebean.sex);
			jsonObject.put("phone", Resumebean.phone);
			jsonObject.put("want", Resumebean.want);
			jsonObject.put("introduce", Resumebean.introduce);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gaizhaopin1, menu);
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
