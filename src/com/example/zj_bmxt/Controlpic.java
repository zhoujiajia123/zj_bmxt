package com.example.zj_bmxt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Controlpic {
	public static String convertIconToString(Bitmap bitmap)
    {
	  if(bitmap!=null){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
	  }
	  else {
		  return "no";
	  }
    }
	
	public static void getHttpBitmap(String url,Bitmap bitmap) {
		  try{
				URL imageUrl = new URL(url);
				HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
				conn.setRequestMethod("POST");	
	            conn.setUseCaches(true); //使用缓存
				conn.setReadTimeout(10000);  
		        conn.setConnectTimeout(5000);
		        conn.setRequestProperty("charset","utf-8");
		        conn.setDoInput(true);
		        conn.setDoOutput(true);
		        conn.connect();	
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				conn.disconnect();	
		  	}
			catch(IOException e){
				e.printStackTrace();
			}
	
	        
	     }
}
