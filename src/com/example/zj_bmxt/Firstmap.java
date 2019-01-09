package com.example.zj_bmxt;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Firstmap extends Activity  {
	PlanNode stNode;
	PlanNode enNode;
	String start,end;	
	Button bt;
	EditText et1,et2;
	MapView mMapView = null; 
	BaiduMap mBaiduMap=null;
	BMapManager mBMapManager=null; 
	RouteLine route = null;
	OverlayManager routeOverlay = null;
	RoutePlanSearch mSearch = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_firstmap);
		et1=(EditText)findViewById(R.id.et1);
		et2=(EditText)findViewById(R.id.et2);
		bt=(Button)findViewById(R.id.bt);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		start=et1.getText().toString();
		end=et2.getText().toString();
		
		LatLng cenpt = new LatLng(40.8,111.7); 
		//定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder()
		.target(cenpt)
		.build();
		//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		//改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		mSearch = RoutePlanSearch.newInstance(); 
		mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
			
			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetTransitRouteResult(TransitRouteResult result) {
				// TODO Auto-generated method stub
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
			        //未找到结果  
			        return;  
			    }  
			    if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {  
			        //起终点或途经点地址有岐义，通过以下接口获取建议查询信息  
			        //result.getSuggestAddrInfo()  
			        return;  
			    }  
			    if (result.error == SearchResult.ERRORNO.NO_ERROR) {   
			        //创建公交路线规划线路覆盖物   
			        TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);  
			        //设置公交路线规划数据
			        mBaiduMap.setOnMarkerClickListener(overlay);
			        overlay.setData(result.getRouteLines().get(0));  
			        //将公交路线规划覆盖物添加到地图中  
			        overlay.addToMap();  
			        overlay.zoomToSpan();  
			   }  
			}
			
			@Override
			public void onGetMassTransitRouteResult(MassTransitRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetIndoorRouteResult(IndoorRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetBikingRouteResult(BikingRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mBaiduMap.clear();
				stNode = PlanNode.withCityNameAndPlaceName("呼和浩特", et1.getText().toString());  
				enNode = PlanNode.withCityNameAndPlaceName("呼和浩特", et2.getText().toString()); 
				 mSearch.transitSearch((new TransitRoutePlanOption())  
		                    .from(stNode)  
		                    .city("呼和浩特")  
		                    .to(enNode));
			}
		});
	}
	
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.firstmap, menu);
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
