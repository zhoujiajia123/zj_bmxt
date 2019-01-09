package com.example.zj_bmxt;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	int netcon;
	String result;
	ProgressDialog dialog;
	String uString="http://192.168.191.1:8080/test02/index.php/Home/Login/login";
	//String uString="http://192.168.191.1:8080/myweb/LoginJudge";
	TextView tv1,tv2;
	Button bt1,bt2;
	EditText et1,et2;
	TextView tname;
	String id,psw;
	private Handler handler = new Handler() {
		  public void handleMessage (Message msg) {
		  netcon=msg.what;
		  result=msg.obj.toString();
		  if(netcon==0){
			  dialog.dismiss();
				Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_LONG).show();
			}
		  else{
			  dialog.dismiss();
			  if (result.equals("1")) {			
				Person.id=id;
				Person.login=1;
				preferences=getSharedPreferences("info", MODE_PRIVATE);
				editor=preferences.edit();
				editor.putString("id", Person.id);
				editor.putInt("login", Person.login);
				editor.commit();
				finish();
			}
			  else{
				  Toast.makeText(getApplicationContext(), "账号或密码有误", Toast.LENGTH_LONG).show();
			  }
		  }
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		/*if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}*/
		bt1=(Button)findViewById(R.id.bt1);
		bt2=(Button)findViewById(R.id.bt2);
		tv1=(TextView)findViewById(R.id.tv1);
		tv2=(TextView)findViewById(R.id.tv2);
		et1=(EditText)findViewById(R.id.et1); 
		et2=(EditText)findViewById(R.id.et2);
		
		//点击注册
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this,Register.class);
				startActivity(intent);
			}
		});
		
		//点击登录
		bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog=ProgressDialog.show(LoginActivity.this, "", "加载中(^ * ^)....");
				
				
				id=et1.getText().toString();
				psw=et2.getText().toString();
				
					if(TextUtils.isEmpty(id)||TextUtils.isEmpty(psw)){
						dialog.dismiss();
						Toast.makeText(getApplication(), "账号或密码不能为空", Toast.LENGTH_LONG).show();
					}
					else { 
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									 //TODO Auto-generated method stub
									HttpConnInfo.postandgetHttpInfo(uString, handler, "username",id,"password",psw);
								}
							}).start();	
													 	
						}
				}		              
		});	
			//点击忘记密码
		    tv1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, Forget_psw.class);
				startActivity(intent);
			}
		});
		    
		    //点击暂不登录
		    tv2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 finish(); 
				}
			});
		    
		    
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
