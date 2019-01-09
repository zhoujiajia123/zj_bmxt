package com.example.zj_bmxt;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Providejob extends Activity {
	String uString="http://192.168.191.1:8080/test02/index.php/Home/InterfaceJob/addjob";
	JSONObject jsonObject=new JSONObject();
	String name,need;
	Button providebt;
	EditText providename,provideneed,providesalary,providereward,provideplace,providenum,providephone,providedetail;
	private Handler handler=new Handler(){
		public void handleMessage(Message message){
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				//sendSuccess();
				if(message.obj.toString().equals("1")){
					 AlertDialog.Builder builder=new AlertDialog.Builder(Providejob.this);
						builder
						.setTitle("提交成功等待审核")
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
					Toast.makeText(getApplication(), "提交失败失败", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.activity_providejob);
		providebt=(Button)findViewById(R.id.providebt);
		providename=(EditText)findViewById(R.id.providename);
		provideneed=(EditText)findViewById(R.id.provideneed);
		providesalary=(EditText)findViewById(R.id.providesalary);
		providereward=(EditText)findViewById(R.id.providereward);
		provideplace=(EditText)findViewById(R.id.provideplace);
		providenum=(EditText)findViewById(R.id.providenum);
		providephone=(EditText)findViewById(R.id.providephone);
		providedetail=(EditText)findViewById(R.id.providedetail);
		
		
		providebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Company.name=providename.getText().toString();
						Company.need=provideneed.getText().toString();
						Company.salary=providesalary.getText().toString();
						Company.reward=providereward.getText().toString();
						Company.place=provideplace.getText().toString();
						Company.num=providenum.getText().toString();
						Company.phone=providephone.getText().toString();
						Company.detail=providedetail.getText().toString();
						getForm();
						Log.e("tag", jsonObject.toString());
						HttpConnInfo.postandgetHttpInfo2(uString, handler, "data", jsonObject.toString());
					}
				}).start();
			}
		});
	}
	
	public void getForm(){
		try {
			jsonObject.put("id", Person.id);
			jsonObject.put("name", Company.name);			
			jsonObject.put("need", Company.need);
			jsonObject.put("salary", Company.salary);
			jsonObject.put("reward", Company.reward);
			jsonObject.put("place", Company.place);
			jsonObject.put("contacts", Company.num);
			jsonObject.put("phone", Company.phone);
			jsonObject.put("detail", Company.detail);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendSuccess(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("提交成功")
		.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				finish();
			}
		}).create().show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.providejob, menu);
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
