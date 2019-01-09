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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Zufang extends Activity {
	final String uString="http://192.168.191.1:8080/test02/index.php/Home/InterfaceRent/search";
	String result=null;
	int netcon;
	ProgressDialog dialog;
	List<Map<String, Object>> list;
	ImageButton zback,fabu;
	SimpleAdapter adapter;
	ListView listView;
	private Handler handler = new Handler() {
		  public void handleMessage (Message msg) {//此方法在ui线程运行
		  netcon=msg.what;
		  result=msg.obj.toString();
		  if(netcon==1){
				tranfInfo(result);
				listView.setAdapter(adapter);
				dialog.dismiss();
			}
		  else{
			  Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_LONG).show();
			  dialog.dismiss();
		  }
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zufang);
		zback=(ImageButton)findViewById(R.id.zback);
		fabu=(ImageButton)findViewById(R.id.fabu);
		list=new ArrayList<Map<String,Object>>();
		dialog = ProgressDialog.show(this, "", "加载中(^ * ^)....");
		new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.getHttpInfo(uString, handler);
			}
		}).start();
		adapter=new SimpleAdapter(this, list, R.layout.zufang_detail, new String[]{"t0","t1","t2","t3"}, new int[]{R.id.houseimg,R.id.housetitle,R.id.houseplace,R.id.houseprice});
		listView=(ListView)findViewById(R.id.houselist);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				
					Intent intent=new Intent();
					intent.setClass(Zufang.this,Zf_1.class);
					intent.putExtra("id", (String)list.get(position).get("id"));
					startActivity(intent);
				
			}
		});
		
		zback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		fabu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent();
				intent.setClass(Zufang.this, Zffb.class);
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
					map.put("id", jsonObject.getString("rent_id"));
					map.put("t0", R.drawable.house);
					map.put("t1", "标题："+jsonObject.getString("rent_title"));
					map.put("t2", "地点："+jsonObject.getString("rent_allocation"));
					map.put("t3", "价格："+jsonObject.getString("rent_pay")+"/月");
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
		getMenuInflater().inflate(R.menu.zufang, menu);
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
