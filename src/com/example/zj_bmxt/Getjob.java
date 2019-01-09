package com.example.zj_bmxt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Getjob extends Activity {
	String uString = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceJob/findjob";
	ListView getjoblist;
	List<Map<String, Object>> list;
	Map<String, Object> map;
	SimpleAdapter adapter;
	ProgressDialog dialog;
	ImageButton addresume, houtui;
	Intent intent = new Intent();
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				break;
			case 1:
				tranfInfo(message.obj.toString());
				getjoblist.setAdapter(adapter);
				dialog.dismiss();
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
		setContentView(R.layout.activity_getjob);
		intent.setClass(Getjob.this, Eachjob.class);
		getjoblist = (ListView) findViewById(R.id.getjoblist);
		addresume = (ImageButton) findViewById(R.id.addresume);
		houtui = (ImageButton) findViewById(R.id.houtui);
		list = new ArrayList<Map<String, Object>>();
		dialog=ProgressDialog.show(this, "", "加载中(^ * ^)....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.getHttpInfo(uString, handler);
			}
		}).start();
		adapter = new SimpleAdapter(this, list, R.layout.getjobdetail, new String[] { "pic", "name", "salary", "need" },
				new int[] { R.id.compic, R.id.comt1, R.id.comt2, R.id.comt3 });
		getjoblist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				intent.putExtra("id", list.get(position).get("id").toString());
				startActivity(intent);
			}
		});

		addresume.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Getjob.this, Addresume.class);
				startActivity(intent);
			}
		});

		houtui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				map.put("id", jsonObject.get("id"));
				map.put("pic", R.drawable.gongsi);
				map.put("name", jsonObject.get("name"));
				map.put("salary", jsonObject.get("salary"));
				map.put("need", jsonObject.get("need"));
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
		getMenuInflater().inflate(R.menu.getjob, menu);
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
