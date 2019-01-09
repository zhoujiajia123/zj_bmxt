package com.example.zj_bmxt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class Schoolview extends Activity {
	String id,uString="http://192.168.191.1:8080/test02/index.php/Home/InterfaceXxgk/find";
	TextView name,address,symbol,introduce;
	ProgressDialog dialog;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				break;
			case 1:				
				tranfInfo("["+msg.obj.toString()+"]");
				name.setText(Schoolbean.name);
				address.setText(Schoolbean.address);
				symbol.setText(Schoolbean.symbol);
				introduce.setText(Schoolbean.introduce);
				dialog.dismiss();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_schoolview);
		name=(TextView)findViewById(R.id.name);
		address=(TextView)findViewById(R.id.address);
		symbol=(TextView)findViewById(R.id.symbol);
		introduce=(TextView)findViewById(R.id.introduce);
		Intent intent=getIntent();
		id=intent.getStringExtra("id");
		dialog=ProgressDialog.show(this, "", "加载中(^ * ^)....");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "id", id);
			}
		}).start();
	}
	
public void tranfInfo(String string){
		
		try {
			JSONArray jsonArray=new JSONArray(string);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				Schoolbean.name=jsonObject.getString("xxgk_name");
				Schoolbean.address=jsonObject.getString("xxgk_address");
				Schoolbean.symbol=jsonObject.getString("xxgk_property");
				Schoolbean.introduce=jsonObject.getString("xxgk_introduce");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schoolview, menu);
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
