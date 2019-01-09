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
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Travel extends Activity {
	int temp, netcon;
	ListView travellist;
	String result, uString = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceLygl/search";
	String uString2 = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceLygl/dianzan";
	SimpleAdapter adapter;
	BaseAdapter myadapter;
	List<Map<String, Object>> list;
	ProgressDialog dialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			netcon = msg.what;
			result = msg.obj.toString();
			if (netcon == 0) {
				dialog.dismiss();
				Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_LONG).show();
			} else {
				dialog.dismiss();
				tranfInfo(result);
				travellist.setAdapter(myadapter);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_travel);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		travellist = (ListView) findViewById(R.id.travellv);
		list = new ArrayList<Map<String, Object>>();
		dialog = ProgressDialog.show(this, "", "加载中(^ * ^)....");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.getHttpInfo(uString, handler);
			}
		}).start();

		myadapter = new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				final ViewHolder viewHolder;
				temp = position;
				// TODO Auto-generated method stub
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.travellistview, parent, false);
				viewHolder.imageView0 = (ImageView) convertView.findViewById(R.id.tralist0);
				viewHolder.textView1 = (TextView) convertView.findViewById(R.id.tralist1);
				viewHolder.textView2 = (TextView) convertView.findViewById(R.id.tralist2);
				viewHolder.textView1.setText((String) list.get(position).get("name"));
				viewHolder.textView2.setText(String.valueOf(list.get(position).get("sup")) + "人喜欢");
				switch (temp) {
				case 0:
					viewHolder.imageView0.setImageResource(R.drawable.diyi);
					break;
				case 1:
					viewHolder.imageView0.setImageResource(R.drawable.dier);
					break;
				case 2:
					viewHolder.imageView0.setImageResource(R.drawable.disan);
					break;
				default:
					break;
				}
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}
		};

		travellist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Travel.this, Travelview.class);
				intent.putExtra("id", (String) list.get(position).get("id"));
				startActivity(intent);
			}
		});
	}

	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				map.put("id", jsonObject.getString("id"));
				map.put("name", jsonObject.getString("name"));
				map.put("sup", jsonObject.getInt("sup_count"));
				list.add(map);
				// adapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public final class ViewHolder {
		ImageView imageView0;
		TextView textView1;
		TextView textView2;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel, menu);
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
