package com.example.zj_bmxt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Shouzhaopin extends Activity {
	String uString="http://192.168.191.1:8080/test02/index.php/Home/JobResume/searchreceiveresume";
	ListView lv;
	SimpleAdapter adapter;
	List<Map<String, Object>> list;
	JSONObject jsonObject = new JSONObject();
	Map<String, Object> map;
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Log.e("tag", message.obj.toString());
				tranfInfo(message.obj.toString());			
				lv.setAdapter(adapter);
			default:
				break;
			}

		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shouzhaopin);
		lv = (ListView) findViewById(R.id.shouzhaopinlv);
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, list, R.layout.lshoujianli, new String[] { "shperson" ,"company"},
				new int[] {R.id.shperson,R.id.shcompany });
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
				Intent intent=new Intent();
				intent.setClass(Shouzhaopin.this, Touzhaopin1.class);
				intent.putExtra("resumeid", (String)list.get(position).get("resumeid"));
				intent.putExtra("userid", (String)list.get(position).get("userid"));
				startActivity(intent);
			}
		});
	}
	
	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				map=new HashMap<String, Object>();
				map.put("shperson",jsonObject.getString("name"));
				map.put("resumeid",jsonObject.getString("resumeid"));
				map.put("company",jsonObject.getString("company"));
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
		getMenuInflater().inflate(R.menu.shouzhaopin, menu);
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
