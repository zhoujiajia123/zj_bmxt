package com.example.zj_bmxt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class Detailinfo extends Activity {
	private static final int CAMERA_CODE = 1;
	private static final int GALLERY_CODE = 2;
    private static final int CROP_CODE = 3;
    SimpleAdapter simpleAdapter;
    Drawable dw;
    ImageButton dim;
	ListView dlv;
	ImageButton baocun;
	int netcon=0;
	String result=null;
	String outcome=null;
	String name=null;
	int sex=0;
	int age=0;
	String touxiang=null;
	String information=null;
	EditText newname;
	TextView nickname;
	TextView sexchoice;
	TextView newage;
	Bitmap picture;
	String[] sexarray=new String[]{"男","女"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detailinfo);
		if (android.os.Build.VERSION.SDK_INT > 9) {
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	}
		dim=(ImageButton)findViewById(R.id.dim);
		dlv=(ListView)findViewById(R.id.dlv);
		baocun=(ImageButton)findViewById(R.id.baocun);	
		getSelfInformation(Person.id);
		getHttpBitmap(touxiang);
		String[] val=new String[]{"头像","昵称","性别","年龄"};
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> map0=new HashMap<String, Object>();
		 map0.put("h", val[0]);
		 map0.put("t", null);
		 map0.put("i",picture);
		 list.add(map0);
		 Map<String, Object> map1=new HashMap<String, Object>();
		 map1.put("h", val[1]);
		 map1.put("t", name);
		 map1.put("i", R.drawable.qianjin);
		 list.add(map1);
		 Map<String, Object> map2=new HashMap<String, Object>();
		 map2.put("h", val[2]);
		 map2.put("t", sexarray[sex]);
		 map2.put("i", R.drawable.qianjin);
		list.add(map2);
		Map<String, Object> map3=new HashMap<String, Object>();
		 map3.put("h", val[3]);
		 map3.put("t", age);
		 map3.put("i", R.drawable.qianjin);
		list.add(map3);
		simpleAdapter=new SimpleAdapter(this,list,R.layout.dinfo_li,new String[]{"h","t","i"}, new int[]{R.id.dinfotv1,R.id.dinfotv2,R.id.dinfotv3});
		simpleAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Object data, String arg2) {
				// TODO Auto-generated method stub
				if(view instanceof ImageView&&data instanceof Bitmap){
					ImageView i=(ImageView)view;
					i.setImageBitmap((Bitmap)data);
					return true;
				}
				return false;
			}
		});
		dlv.setAdapter(simpleAdapter);		
		dlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int which, long arg3) {
				// TODO Auto-generated method stub
				switch (which) {					
				case 0:
					changeImage();
					break;
				case 1:
					changeName();
					break;
					
				case 2:
					changeSex();					
					break;
				case 3:
					changeAge();
				default:
					break;
				}
			}
		});
		
		
		//后退
		dim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//保存修改
		baocun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				JSONObject js = new JSONObject();
				try {
					js.put("id", Person.id);
					js.put("name", name);
					js.put("sex", String.valueOf(sex));
					js.put("age", String.valueOf(age));
					js.put("picture",convertIconToString(picture));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String content=String.valueOf(js);
				try {
					String urlstring="http://192.168.191.1:8080/test02/index.php/Home/Login/self_info_change";
					String data="data="+URLEncoder.encode(content, "UTF-8");
					URL url;				
					url = new URL(urlstring);
					HttpURLConnection connection=null;
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
			        if(connection.getResponseCode()==200){
			        	Log.e("tag", content);
			        	Log.e("tag", "发送个人信息连接成功");
			        	InputStream is = connection.getInputStream();    
		                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		                int len = 0;  
		                byte buffer[] = new byte[1024];    
		                while ((len = is.read(buffer)) != -1) {  
		                    baos.write(buffer, 0, len);  
		                }  
		                // 释放资源  
		                outcome=new String(baos.toByteArray());
		                if (outcome.equals("1")) {
		                	//Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show();
		                	AlertDialog.Builder builder=new AlertDialog.Builder(Detailinfo.this);
							builder
							.setTitle("修改成功")
							.setIcon(R.drawable.duigou)
							.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									finish();
								}
							})
							.create().show();
						 
					 }
		                else {
		                	Toast.makeText(getApplication(), "修改失败", Toast.LENGTH_SHORT).show();
		                }
							                
		                is.close();  
		                baos.close();
		               Log.e("tag", result);
			        }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
		});
	
		
	}
	
	
	//获取个人信息
	public void getSelfInformation(String id){
		try {		 
					String data="id="+URLEncoder.encode(id, "UTF-8");
					String urlstring="http://192.168.191.1:8080/test02/index.php/Home/Login/self_info_search";
					URL url;				
					url = new URL(urlstring);
					HttpURLConnection connection=null;
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
			        if(connection.getResponseCode()==200){
			        	Log.e("tag", "个人信息连接成功");
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
		               result = new String(baos.toByteArray());
		               result="["+result+"]";
		               Log.e("tag", result);
			        }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		

		try {
			JSONArray jsonArray;
			jsonArray = new JSONArray(result);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				name=jsonObject.getString("name");
				sex=jsonObject.getInt("sex");
				age=jsonObject.getInt("age");
				touxiang=jsonObject.getString("touxiang");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
										
	}
		
	//修改头像
	public void changeImage(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder
		.setItems(new String[] {"拍照","从图库中选择"}, new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO Auto-generated method stub
				switch(which){
				case 0:
					chooseFromCamera();
					break;
				case 1:
					chooseFromGallery();
					break;
				}
			}
		}).create().show();
	}
	
	//修改昵称
	@SuppressLint("InflateParams")
	public void changeName(){
		LinearLayout layout1=(LinearLayout)getLayoutInflater().inflate(R.layout.changename, null);
		newname=(EditText)layout1.findViewById(R.id.newname);
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder
		.setTitle("修改昵称")
		.setView(layout1)
		.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				LinearLayout layout2=(LinearLayout)dlv.getChildAt(1);
				nickname=(TextView)layout2.findViewById(R.id.dinfotv2);
				name=newname.getText().toString();
				nickname.setText(name);
			}
		})
		.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}).create().show();
							
	}
	
	//修改性别
	public void changeSex(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("修改性别")
		.setSingleChoiceItems(sexarray, 0, new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO Auto-generated method stub
				LinearLayout layout=(LinearLayout)dlv.getChildAt(2);
				sexchoice=(TextView)layout.findViewById(R.id.dinfotv2);
				sex=which;
				sexchoice.setText(sexarray[sex]);
			}
		})
		.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}).create().show();
		
	}
	
	//修改年龄
	public void changeAge(){
		new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker dp, int year, int month, int day) {
				// TODO Auto-generated method stub
				SimpleDateFormat format1=new SimpleDateFormat("yyyy");
				SimpleDateFormat format2=new SimpleDateFormat("mmmm");
				SimpleDateFormat format3=new SimpleDateFormat("dddd");
				int nowYear=Integer.parseInt(format1.format(new Date()));
				int nowMonth=Integer.parseInt(format2.format(new Date()));
				int nowDay=Integer.parseInt(format3.format(new Date()));
				age=nowYear-year;
				if(nowMonth<month){
					age--;
				}
				else{
					if(nowMonth==month){
						if(nowDay<day){
							age--;
						}
					}
				}
				LinearLayout layout=(LinearLayout)dlv.getChildAt(3);
				newage=(TextView)layout.findViewById(R.id.dinfotv2);
				newage.setText(String.valueOf(age));
			}
		}, 2000, 0, 1).show();
	}
	
	private void chooseFromCamera() {
	    //构建隐式Intent
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    //调用系统相机
	    startActivityForResult(intent, CAMERA_CODE);
	  }
	 
	  /**
	   * 从相册选择图片
	   */
	  private void chooseFromGallery() {
	    //构建一个内容选择的Intent
	    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	    //设置选择类型为图片类型
	    intent.setType("image/*");
	    //打开图片选择
	    startActivityForResult(intent, GALLERY_CODE);
	  }
	  
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    switch (requestCode){
		      case CAMERA_CODE:
		        //用户点击了取消
		        if(data == null){
		          return;
		        }else{
		          Bundle extras = data.getExtras();
		          if (extras != null){
		            //获得拍的照片
		            Bitmap bm = extras.getParcelable("data");
		            //将Bitmap转化为uri
		            Uri uri = saveBitmap(bm, "temp");
		            //启动图像裁剪
		            startImageZoom(uri);
		          }
		        }
		        break;
		      case GALLERY_CODE:
		        if (data == null){
		          return;
		        }else{		         
		          Uri uri = data.getData();
		          startImageZoom(uri);
		        }
		        break;
		      case CROP_CODE:
		        if (data == null){
		          return;
		        }else{
		          Bundle extras = data.getExtras();
		          if (extras != null){
		            //获取到裁剪后的图像
		            Bitmap bm = extras.getParcelable("data");
		            picture=bm;
		            LinearLayout layout=(LinearLayout)dlv.getChildAt(0);
					ImageView mImageView=(ImageView)layout.findViewById(R.id.dinfotv3);
		            mImageView.setImageBitmap(bm);		            
		          }
		        }
		        break;
		      default:
		        break;
		    }
		  }
	  
	  private Uri saveBitmap(Bitmap bm, String dirPath) {
		    //新建文件夹用于存放裁剪后的图片
		    File tmpDir = new File(Environment.getExternalStorageDirectory() + "/" + dirPath);
		    if (!tmpDir.exists()){
		      tmpDir.mkdir();
		    }
		 
		    //新建文件存储裁剪后的图片
		    File img = new File(tmpDir.getAbsolutePath() + "/avator.png");
		    try {
		      //打开文件输出流
		      FileOutputStream fos = new FileOutputStream(img);
		      //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
		      bm.compress(Bitmap.CompressFormat.PNG,100,fos);
		      //刷新输出流
		      fos.flush();
		      //关闭输出流
		      fos.close();
		      //返回File类型的Uri
		      return Uri.fromFile(img);
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		      return null;
		    } catch (IOException e) {
		      e.printStackTrace();
		      return null;
		    }
		 
		  }
	  
	  private void startImageZoom(Uri uri){
		    //构建隐式Intent来启动裁剪程序
		    Intent intent = new Intent("com.android.camera.action.CROP");
		    //设置数据uri和类型为图片类型
		    intent.setDataAndType(uri, "image/*");
		    //显示View为可裁剪的
		    intent.putExtra("crop", true);
		    //裁剪的宽高的比例为1:1
		    intent.putExtra("aspectX", 1);
		    intent.putExtra("aspectY", 1);
		    //输出图片的宽高均为150
		    intent.putExtra("outputX", 200);
		    intent.putExtra("outputY", 200);
		    //裁剪之后的数据是通过Intent返回
		    intent.putExtra("return-data", true);
		    startActivityForResult(intent, CROP_CODE);
		  }



	  public String convertIconToString(Bitmap bitmap)
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
	     
	  public void getHttpBitmap(String url) {
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
				picture = BitmapFactory.decodeStream(is);
				conn.disconnect();	
		  	}
			catch(IOException e){
				e.printStackTrace();
			}
	  }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailinfo, menu);
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
