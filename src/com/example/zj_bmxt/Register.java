package com.example.zj_bmxt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class Register extends Activity {

	EditText rshouji,rmima,rrmima;
	Button rbt;
	String mima;
	String shouji;
	String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		rshouji=(EditText)findViewById(R.id.rshouji);
		rmima=(EditText)findViewById(R.id.rmima);
		rrmima=(EditText)findViewById(R.id.rrmima);
		rbt=(Button)findViewById(R.id.rbt);
			
		rbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mima=rmima.getText().toString();
				shouji=rshouji.getText().toString();
				if(shouji.length()!=11){
					Toast.makeText(getApplicationContext(), "�ֻ��Ŵ���", Toast.LENGTH_SHORT).show();
				}
				else{
					
					if(!(rmima.getText().toString().equals(rrmima.getText().toString()))){
						Toast.makeText(getApplicationContext(), "���벻һ��", Toast.LENGTH_SHORT).show();
					}
					else{
						
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								mima=rmima.getText().toString();
								shouji=rshouji.getText().toString();
								Looper.prepare();
								checkRegister(shouji, mima);
								Looper.loop();
							}
						}).start();
					}
				}
			}
		});
	}
	
	public void checkRegister(String name,String psw){
		try{
			String data="username="+ URLEncoder.encode(name, "UTF-8") +"&password="+ URLEncoder.encode(psw, "UTF-8");			
			HttpURLConnection connection;
			String urlstring="http://192.168.191.1:8080/test02/index.php/Home/Login/register";
			
			//String urlstring="http://192.168.191.1:8080/myweb/LoginJudge";
			URL url=new URL(urlstring);
			connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(5000);  
	        connection.setConnectTimeout(5000);
	        connection.setRequestProperty("charset","utf-8");
	        connection.setDoInput(true);
	        connection.setDoOutput(true);	       
			OutputStream outputStream=connection.getOutputStream();
			outputStream.write(data.getBytes());
			outputStream.flush();
			Log.e("tag", "��ʽ��ȷ");
			if(connection.getResponseCode()==200){
				Log.e("tag", "���ӳɹ�");
				// ��ȡ��Ӧ������������  
                InputStream is = connection.getInputStream();  
                // �����ֽ����������  
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                // �����ȡ�ĳ���  
                int len = 0;  
                // ���建����  
                byte buffer[] = new byte[1024];  		                 
                while ((len = is.read(buffer)) != -1) {  
                    baos.write(buffer, 0, len);  
                }  
                // �ͷ���Դ  
                is.close();  
                baos.close();
                // �����ַ���  
                result = new String(baos.toByteArray()); 
				 if(result.equals("true")){
					 AlertDialog.Builder builder=new AlertDialog.Builder(this);
						builder
						.setTitle("ע��ɹ�")
						.setIcon(R.drawable.duigou)
						.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								finish();
							}
						})
						.create().show();
					 
				 }
				 else{
					 Toast.makeText(getApplication(), "ע��ʧ��", Toast.LENGTH_SHORT).show();
				 }
			}
			
			else if(connection.getResponseCode()==404){
				Toast.makeText(getApplicationContext(), "404", Toast.LENGTH_SHORT).show();
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("tgp", "�����쳣");
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
