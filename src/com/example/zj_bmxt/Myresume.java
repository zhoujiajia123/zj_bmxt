package com.example.zj_bmxt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Myresume extends Activity {
	String companyid,uString="http://192.168.191.1:8080/test02/index.php/Home/InterfaceJob/findstaff";
	String uString2="http://192.168.191.1:8080/test02/index.php/Home/InterfaceJob/postSearchjob";
	ListView lv;
	SimpleAdapter adapter;
	List<Map<String, Object>> list;
	JSONObject jsonObject=new JSONObject();
	Map<String, Object> map;	
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				tranfInfo(message.obj.toString());
				lv.setAdapter(adapter);
			default:
				break;
			}

		}
	};
	private Handler handler2 = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:
				
				break;
			case 1:
				if (message.obj.toString().equals("1")) {					
				AlertDialog.Builder builder=new AlertDialog.Builder(Myresume.this);
				builder
				.setTitle("投递成功")
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
			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myresume);
		if (android.os.Build.VERSION.SDK_INT > 9) {
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	}
		lv=(ListView)findViewById(R.id.myresumelv);
		list=new ArrayList<Map<String,Object>>();
		Intent intent=getIntent();
		companyid=intent.getStringExtra("companyid");
		adapter=new SimpleAdapter(this, list, R.layout.resumelv, new String[]{"resume"}, new int[]{R.id.rename});
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "id", Person.id);
			}
		}).start();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				try {
					jsonObject.put("companyid", companyid);
					jsonObject.put("userid", Person.id);
					jsonObject.put("resumeid", list.get(position).get("resumeid"));
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							HttpConnInfo.postandgetHttpInfo2(uString2,handler2, "data", jsonObject.toString());
						}
					}).start();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				map=new HashMap<String, Object>();
				map.put("resume",jsonObject.getString("resume"));
				map.put("resumeid", jsonObject.getString("resumeid"));
				Log.e("tag", jsonObject.getString("resumeid"));
				list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.myresume, menu);
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
