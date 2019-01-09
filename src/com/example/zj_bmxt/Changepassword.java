package com.example.zj_bmxt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Changepassword extends Activity {
	String uString1="http://192.168.191.1:8080/test02/index.php/Home/Login/getchangecount";
	String uString2="http://192.168.191.1:8080/test02/index.php/Home/Login/changepassword";
	SmsManager sManager;
	EditText changepass1,changepass2,changepass4;
	Button changepass3,changepass5;
	String oldpassword,newpassword,checknum,inputchecknum;
	private Handler handler1 = new Handler() {
		  public void handleMessage (Message msg) {//此方法在ui线程运行
		 
		  if(msg.what==0){
				Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_LONG).show();
			}
		  else{
			  checknum=msg.obj.toString();
			  sManager=SmsManager.getDefault();
			  sManager.sendTextMessage(Person.id, null,checknum, null, null);
		  }
		}
	};
	private Handler handler2 = new Handler() {
		  public void handleMessage (Message msg) {//此方法在ui线程运行
		 
		  if(msg.what==0){
				Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_LONG).show();
			}
		  else{
			  if (msg.obj.toString().equals("1")) {
				  Person.login=0;
				  AlertDialog.Builder builder=new AlertDialog.Builder(Changepassword.this);
					builder
					.setTitle("修改密码成功")
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
				  Toast.makeText(getApplication(), "修改失败!", Toast.LENGTH_SHORT).show();
			  }
		  }
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changepassword);
		changepass1=(EditText)findViewById(R.id.changepass1);
		changepass2=(EditText)findViewById(R.id.changepass2);
		changepass3=(Button)findViewById(R.id.changepass3);
		changepass4=(EditText)findViewById(R.id.changepass4);
		changepass5=(Button)findViewById(R.id.changepass5);
		
		changepass3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						oldpassword=changepass1.getText().toString();
						HttpConnInfo.postandgetHttpInfo(uString1, handler1, "id", Person.id, "oldpassword", oldpassword);
					}
				}).start();
			}
		});
		
		changepass5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						newpassword=changepass2.getText().toString();
						inputchecknum=changepass4.getText().toString();
						if (checknum.equals(inputchecknum)) {
							HttpConnInfo.postandgetHttpInfo(uString2, handler2, "id", Person.id, "newpassword", newpassword);
						}
					}
				}).start();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.changepassword, menu);
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
