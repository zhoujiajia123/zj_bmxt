package com.example.zj_bmxt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class HttpConnInfo {

	static public void getHttpInfo(String uString,Handler handler){
		
		URL url;
		 Message message=new Message();
		try {
			url = new URL(uString);
			HttpURLConnection connection=null;
			connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(5000);  
	        connection.setConnectTimeout(3000);
	        connection.setRequestProperty("charset","utf-8");
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        if(connection.getResponseCode()==200){
	        	Log.e("tag", "连接成功");
	        	InputStream is = connection.getInputStream();    
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                int len = 0;  
                byte buffer[] = new byte[1024];    
                while ((len = is.read(buffer)) != -1) {  
                    baos.write(buffer, 0, len);  
                }  
                // 释放资源  
                is.close();  
                baos.close();
                message.obj= new String(baos.toByteArray());
                Log.e("tag", (String)message.obj);
				message.what=1;				               
	        }
	        else if(connection.getResponseCode()==404){
	        	message.what=0;
	        	message.obj="false";
	        }
	      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.what=0;
			message.obj="false";
		} 
		finally {
			handler.sendMessage(message);
		}	
	}
	
static public void postandgetHttpInfo(String uString,Handler handler,String postIdentify1,String postData1,String postIdentify2,String postData2){
		
		URL url;
		Message message=new Message();
		
		String postInfo=postIdentify1+"="+postData1+"&"+postIdentify2+"="+postData2;
		try {
			url = new URL(uString);
			HttpURLConnection connection=null;
			connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(5000);  
	        connection.setConnectTimeout(5000);
	        connection.setRequestProperty("charset","utf-8");
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        OutputStream outputStream=connection.getOutputStream();
			outputStream.write(postInfo.getBytes());
			outputStream.flush();
	        if(connection.getResponseCode()==200){
	        	Log.e("tag", "连接成功");
	        	InputStream is = connection.getInputStream();    
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                int len = 0;  
                byte buffer[] = new byte[1024];    
                while ((len = is.read(buffer)) != -1) {  
                    baos.write(buffer, 0, len);  
                }  
                // 释放资源  
                is.close();  
                baos.close();
                message.obj= new String(baos.toByteArray());	
				message.what=1;				               
	        }	
	        else if(connection.getResponseCode()==404){
	        	message.what=0;
	        	message.obj="false";
	        }
	      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message.what=0;
			message.obj="false";
		} 
		finally {
			handler.sendMessage(message);
		}	
	}

static public void postandgetHttpInfo2(String uString,Handler handler,String postIdentify1,String postData1){
	
	URL url;
	Message message=new Message();
	
	
	try {
		String postInfo=postIdentify1+"="+URLEncoder.encode(postData1, "UTF-8");
		url = new URL(uString);
		HttpURLConnection connection=null;
		connection=(HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setReadTimeout(5000);  
        connection.setConnectTimeout(5000);
        connection.setRequestProperty("charset","utf-8");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        OutputStream outputStream=connection.getOutputStream();
		outputStream.write(postInfo.getBytes());
		outputStream.flush();
        if(connection.getResponseCode()==200){
        	Log.e("tag", "连接成功");
        	InputStream is = connection.getInputStream();    
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            int len = 0;  
            byte buffer[] = new byte[1024];    
            while ((len = is.read(buffer)) != -1) {  
                baos.write(buffer, 0, len);  
            }  
            // 释放资源  
            is.close();  
            baos.close();
            message.obj= new String(baos.toByteArray());	
			message.what=1;				               
        }	
        else if(connection.getResponseCode()==404){
        	message.what=0;
        	message.obj="false";
        }
      
	} catch (IOException e) {
		// TODO Auto-generated catch block
		message.what=0;
		message.obj="false";
	} 
	finally {
		handler.sendMessage(message);
	}	
}
	
static public String postandgetHttpInfo3(String uString,String postIdentify1,String postData1){
	
	URL url;
	String result=null;
	String postInfo=postIdentify1+"="+postData1;
	try {
		url = new URL(uString);
		HttpURLConnection connection=null;
		connection=(HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setReadTimeout(5000);  
        connection.setConnectTimeout(5000);
        connection.setRequestProperty("charset","utf-8");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        OutputStream outputStream=connection.getOutputStream();
		outputStream.write(postInfo.getBytes());
		//outputStream.flush();
        if(connection.getResponseCode()==200){
        	Log.e("tag", "连接成功");
        	InputStream is = connection.getInputStream();    
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            int len = 0;  
            byte buffer[] = new byte[1024];    
            while ((len = is.read(buffer)) != -1) {  
                baos.write(buffer, 0, len);  
            }  
            // 释放资源  
            is.close();  
            baos.close();
            result= new String(baos.toByteArray());	
        }	
        else if(connection.getResponseCode()==404){
        	
        }
      
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	return result;
}

}
