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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Zige extends Activity {
	final String uString="http://192.168.191.1:8080/test02/index.php/Home/InterfaceZyzg/zyzg_search";
	int netcon;
	ProgressDialog dialog;
	ImageButton houtui;
	ListView zlv;
	String result=null;
	SearchView sv;
	List<Map<String, Object>> list;
	SimpleAdapter adapter;
	private Handler handler = new Handler() {
		  public void handleMessage (Message msg) {//此方法在ui线程运行
		  netcon=msg.what;
		  result=msg.obj.toString();
		  if(netcon==0){
				Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		  else{
			  tranfInfo(result);
			  dialog.dismiss();
		  }
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
			 
		setContentView(R.layout.activity_zige);
		houtui=(ImageButton)findViewById(R.id.houtui);
		zlv=(ListView)findViewById(R.id.zlv);
		zlv.setTextFilterEnabled(true);
		
		//设置搜索框属性
		sv=(SearchView)findViewById(R.id.sv);
		sv.setIconifiedByDefault(false);		
		sv.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newtext) {
				// TODO Auto-generated method stub
				if(TextUtils.isEmpty(newtext)){
					zlv.clearTextFilter();
				}
				else{
					zlv.setFilterText(newtext);
				}
				return true;
			}
		});
		sv.setSubmitButtonEnabled(true);
		sv.setQueryHint("搜索");	
		
		//设置列表与适配器
		list=new ArrayList<Map<String,Object>>();
		Map<String, Object> map0=new HashMap<String, Object>();
		map0.put("t1", "资格名称");
		map0.put("t2", "开始时间");
		map0.put("t3", "结束时间");
		list.add(map0);
		//getHttpInfo();
		dialog=ProgressDialog.show(this, "", "加载中(^ * ^)....");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.getHttpInfo(uString, handler);
			}
		}).start();		
		adapter=new SimpleAdapter(this, list, R.layout.dzige,new String[]{"t1","t2","t3"}, new int[]{R.id.ziget1,R.id.ziget2,R.id.ziget3});
		zlv.setAdapter(adapter);
		
		
		houtui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		zlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplication(), "哈哈", Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}
	
	
	//转换数据放入列表
	public void tranfInfo(String string){
		
		try {
			JSONArray jsonArray=new JSONArray(string);
			for(int i=0;i<jsonArray.length();i++){
				Map<String, Object> map=new HashMap<String, Object>();
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				map.put("t1", jsonObject.getString("zyzg_name"));
				map.put("t2", jsonObject.getString("zyzg_time_start"));
				map.put("t3", jsonObject.getString("zyzg_time_end"));
				list.add(map);
				adapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zige, menu);
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
