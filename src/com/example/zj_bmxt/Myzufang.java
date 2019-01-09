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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Myzufang extends Activity {
	final String uString = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceRent/findmyrent";
	String uString2="http://192.168.191.1:8080/test02/index.php/Home/InterfaceRent/delete";
	int number;
	List<Map<String, Object>> list;
	ImageButton zback, fabu;
	SimpleAdapter adapter;
	ListView lv;
	private Handler handler = new Handler() {
		  public void handleMessage (Message msg) {//此方法在ui线程运行

		switch (msg.what) {
		case 0:
			Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_LONG).show();
			break;
		case 1:
			tranfInfo(msg.obj.toString());
			lv.setAdapter(adapter);
			break;
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
		setContentView(R.layout.activity_myzufang);
		list=new ArrayList<Map<String,Object>>();
		new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.getHttpInfo(uString, handler);
			}
		}).start();
		adapter=new SimpleAdapter(this, list, R.layout.zufang_detail, new String[]{"t0","t1","t2","t3"}, new int[]{R.id.houseimg,R.id.housetitle,R.id.houseplace,R.id.houseprice});
		lv=(ListView)findViewById(R.id.myzufang);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "userid", Person.id);
			}
		}).start();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Myzufang.this, Myzufang1.class);
				intent.putExtra("rent_id", (String)list.get(position).get("rent_id"));
				startActivity(intent);
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
				// TODO Auto-generated method stub
				number=position;
				AlertDialog.Builder builder = new AlertDialog.Builder(Myzufang.this);
				builder.setTitle("删除发布的信息").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								String temp = (String) list.get(position).get("rent_id");
								HttpConnInfo.postandgetHttpInfo2(uString2, handler2, "rent_id",temp);
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
	}
	
	public void tranfInfo(String string){			
		try {
			JSONArray jsonArray=new JSONArray(string);
			for(int i=0;i<jsonArray.length();i++){
				Map<String, Object> map=new HashMap<String, Object>();
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				map.put("rent_id", jsonObject.getString("rent_id"));
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
		getMenuInflater().inflate(R.menu.myzufang, menu);
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
