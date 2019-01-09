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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Touzhaopin1 extends Activity {
	String resumeid, userid, uString = "http://192.168.191.1:8080/test02/index.php/Home/JobResume/findresume";
	TextView resume, name, education, address, sex, want, introduce, phone;
	ImageView rtouxiang;
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:
				Toast.makeText(getApplication(), "服务器连接失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				tranfInfo("[" + message.obj.toString() + "]");
				resume.setText(Resumebean.resume);
				name.setText(Resumebean.name);
				education.setText(Resumebean.education);
				address.setText(Resumebean.address);
				sex.setText(Resumebean.sex);
				want.setText(Resumebean.want);
				phone.setText(Resumebean.phone);
				introduce.setText(Resumebean.introduce);
			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_touzhaopin1);
		resume = (TextView) findViewById(R.id.resume);
		name = (TextView) findViewById(R.id.name);
		education = (TextView) findViewById(R.id.education);
		address = (TextView) findViewById(R.id.address);
		sex = (TextView) findViewById(R.id.sex);
		phone = (TextView) findViewById(R.id.phone);
		want = (TextView) findViewById(R.id.want);
		introduce = (TextView) findViewById(R.id.introduce);
		rtouxiang = (ImageView) findViewById(R.id.rtouxiang);
		Intent intent = getIntent();
		resumeid = intent.getStringExtra("resumeid");
		userid = intent.getStringExtra("userid");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpConnInfo.postandgetHttpInfo2(uString, handler, "resumeid", resumeid);

			}
		}).start();
	}

	public void tranfInfo(String string) {

		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Resumebean.resume = jsonObject.getString("job_staff_resume");
				Resumebean.name = jsonObject.getString("job_staff_name");
				Resumebean.education = jsonObject.getString("job_staff_educate");
				Resumebean.address = jsonObject.getString("job_staff_residence");
				Resumebean.sex = jsonObject.getString("job_staff_sex");
				Resumebean.phone = jsonObject.getString("job_staff_phone");
				Resumebean.want = jsonObject.getString("job_staff_intend");
				Resumebean.introduce = jsonObject.getString("job_staff_remark");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.touzhaopin1, menu);
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
