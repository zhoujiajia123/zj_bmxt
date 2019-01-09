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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Gaizhaopin extends Activity {
	String uString = "http://192.168.191.1:8080/test02/index.php/Home/JobResume/searchmyresume";
	String uString2 = "http://192.168.191.1:8080/test02/index.php/Home/JobResume/deleteresume";
	ListView lv;
	SimpleAdapter adapter;
	int number;
	List<Map<String, Object>> list;
	JSONObject jsonObject = new JSONObject();
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
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				//Toast.makeText(getApplication(), message.obj.toString(), Toast.LENGTH_SHORT).show();
				list.remove(number);
				adapter.notifyDataSetChanged();
			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gaizhaopin);
		lv = (ListView) findViewById(R.id.gaizhaopinlv);
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, list, R.layout.ltouzhaopin, new String[] { "resumename" },
				new int[] { R.id.rename });
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "userid", Person.id);
			}
		}).start();

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
				// TODO Auto-generated method stub
				number=position;
				AlertDialog.Builder builder = new AlertDialog.Builder(Gaizhaopin.this);
				builder.setTitle("删除简历").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								String temp = (String) list.get(position).get("resumeid");
								HttpConnInfo.postandgetHttpInfo2(uString2, handler2, "resumeid",temp);
							}
						}).start();
						
						
					}
				}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

					}
				}).create().show();
				return true;
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Gaizhaopin.this, Gaizhaopin1.class);
				intent.putExtra("resumeid", (String) list.get(position).get("resumeid"));
				intent.putExtra("resumename", (String) list.get(position).get("resumename"));
				startActivity(intent);
			}
		});

	}

	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);				
				map = new HashMap<String, Object>();
				map.put("resumeid", jsonObject.getString("id"));
				Log.e("tag", jsonObject.getString("id"));
				map.put("resumename", jsonObject.getString("resume"));
				list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gaizhaopin, menu);
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
