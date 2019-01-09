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
		//�����ͼ״̬
		MapStatus mMapStatus = new MapStatus.Builder()
		.target(cenpt)
		.build();
		//����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		//�ı��ͼ״̬
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
			        //δ�ҵ����  
			        return;  
			    }  
			    if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {  
			        //���յ��;�����ַ����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ  
			        //result.getSuggestAddrInfo()  
			        return;  
			    }  
			    if (result.error == SearchResult.ERRORNO.NO_ERROR) {   
			        //��������·�߹滮��·������   
			        TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);  
			        //���ù���·�߹滮����
			        mBaiduMap.setOnMarkerClickListener(overlay);
			        overlay.setData(result.getRouteLines().get(0));  
			        //������·�߹滮��������ӵ���ͼ��  
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
				stNode = PlanNode.withCityNameAndPlaceName("���ͺ���", et1.getText().toString());  
				enNode = PlanNode.withCityNameAndPlaceName("���ͺ���", et2.getText().toString()); 
				 mSearch.transitSearch((new TransitRoutePlanOption())  
		                    .from(stNode)  
		                    .city("���ͺ���")  
		                    .to(enNode));
			}
		});
	}
	
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
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
