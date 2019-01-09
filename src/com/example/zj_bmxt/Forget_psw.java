package com.example.zj_bmxt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forget_psw extends Activity {
	EditText fpass_ed1,fpass_ed2;
	Button fpass_bt1,fpass_bt2;
	int netcon;
	String data;
	SmsManager sManager;
	String result,checknum,passnum;
	String uString="http://192.168.191.1:8080/test02/index.php/Home/Login/suijishu";
	private Handler handler = new Handler() {
		  public void handleMessage (Message msg) {//此方法在ui线程运行
		  netcon=msg.what;
		  result=msg.obj.toString();
		  result="["+result+"]";
		  if(netcon==0){
				Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_LONG).show();
			}
		  else{
			  tranfInfo(result);
			  sManager=SmsManager.getDefault();
			  sManager.sendTextMessage(data, null, checknum, null, null);
		  }
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forget_psw);
		fpass_ed1=(EditText)findViewById(R.id.fpass_ed1);
		fpass_ed2=(EditText)findViewById(R.id.fpass_ed2);
		fpass_bt1=(Button)findViewById(R.id.fpass_bt1);
		fpass_bt2=(Button)findViewById(R.id.fpass_bt2);
		
	fpass_bt1.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						data=fpass_ed1.getText().toString();
						HttpConnInfo.postandgetHttpInfo2(uString, handler,"id",data);
					}
				}).start();			
			}
		});
		
	fpass_bt2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String getcheckdata=fpass_ed2.getText().toString();
			if (getcheckdata.equals(checknum)) {
				sManager=SmsManager.getDefault();
				sManager.sendTextMessage(data, null, passnum, null, null);
			}
		}
	});
	}
	
	
public void tranfInfo(String string){
		
		try {
			JSONArray jsonArray=new JSONArray(string);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				checknum=jsonObject.getString("checknum");
				passnum=jsonObject.getString("password");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forget_psw, menu);
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
