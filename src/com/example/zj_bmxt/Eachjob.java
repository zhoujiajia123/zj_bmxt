package com.example.zj_bmxt;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Eachjob extends Activity {
	TextView getname, getneed, getsalary, getreward, getplace, getnum, getphone, getdetail;
	Button getbt;
	String id, uString = "http://192.168.191.1:8080/test02/index.php/Home/InterfaceJob/searchjob";
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:

				break;
			case 1:
				Log.e("tag", message.obj.toString());
				tranfInfo("["+message.obj.toString()+"]");
				getname.setText(Company.name);
				getneed.setText(Company.need);
				getsalary.setText(Company.salary);
				getreward.setText(Company.reward);
				getplace.setText(Company.place);
				getnum.setText(Company.num);
				getphone.setText(Company.phone);
				getdetail.setText(Company.detail);
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
		setContentView(R.layout.activity_eachjob);
		getname = (TextView) findViewById(R.id.getname);
		getneed = (TextView) findViewById(R.id.getneed);
		getsalary = (TextView) findViewById(R.id.getsalary);
		getreward = (TextView) findViewById(R.id.getreward);
		getplace = (TextView) findViewById(R.id.getplace);
		getnum = (TextView) findViewById(R.id.getnum);
		getphone = (TextView) findViewById(R.id.getphone);
		getdetail = (TextView) findViewById(R.id.getdetail);
		getbt = (Button) findViewById(R.id.getbt);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "id", id);
			}
		}).start();

		getbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Eachjob.this, Myresume.class);
				intent.putExtra("companyid", id);
				startActivity(intent);
			}
		});
	}

	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Company.name = jsonObject.getString("job_company_name");
				Company.need = jsonObject.getString("job_company_intend");
				Company.salary = jsonObject.getString("job_company_pay");
				Company.reward = jsonObject.getString("job_company_welfare");
				Company.place = jsonObject.getString("job_company_workplace");
				Company.num = jsonObject.getString("job_company_contacts");
				Company.phone = jsonObject.getString("job_company_phone");
				Company.detail = jsonObject.getString("job_company_remark");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.eachjob, menu);
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
