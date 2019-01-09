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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Diffschool extends Activity {
	String result,uString="http://192.168.191.1:8080/test02/index.php/Home/InterfaceXxgk/search";
	ListView schoollv;
	List<Map<String, Object>> list;
	SimpleAdapter adapter;
	ProgressDialog dialog;
	String type;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				Toast.makeText(getApplication(), "连接服务器失败", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				break;
			case 1:
				result=msg.obj.toString();
				tranfInfo(result);
				schoollv.setAdapter(adapter);
				dialog.dismiss();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_diffschool);
		schoollv=(ListView)findViewById(R.id.schoollist);
		Intent intent=getIntent();
		type=intent.getStringExtra("type");
		list=new ArrayList<Map<String,Object>>();
		dialog=ProgressDialog.show(this, "", "加载中(^ * ^)....");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "type", type);
			}
		}).start();
		
		adapter=new SimpleAdapter(this, list, R.layout.schoolline, new String[]{"schoolname"}, new int[]{R.id.schoolname} );
		schoollv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Diffschool.this,Schoolview.class );
				intent.putExtra("id", (String)list.get(position).get("id"));
				startActivity(intent);
			}
		});
	}
	
public void tranfInfo(String string){
		
		try {
			JSONArray jsonArray=new JSONArray(string);
			for(int i=0;i<jsonArray.length();i++){
				Map<String, Object> map=new HashMap<String, Object>();
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				map.put("id", String.valueOf(jsonObject.getInt("id")));
				map.put("schoolname", jsonObject.getString("name"));
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
		getMenuInflater().inflate(R.menu.diffschool, menu);
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
