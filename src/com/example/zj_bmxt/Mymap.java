package com.example.zj_bmxt;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BusLineOverlay;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Mymap extends Activity {
	private int busLineIndex = 0;
	private int nodeIndex = -2;
	MapView mapView;
	private BaiduMap mBaiduMap=null;
	private BusLineResult route=null;
	private PoiSearch mSearch=null;
	private BusLineSearch mBusLineSearch=null;
	private List<String> busLineIDList = null;
	private List<String> stationName = null;
	BusLineOverlay overlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_mymap);
		mapView=(MapView)findViewById(R.id.bmapView);
		mBaiduMap=mapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		overlay = new BusLineOverlay(mBaiduMap);
		mSearch = PoiSearch.newInstance();
		mBusLineSearch = BusLineSearch.newInstance();
		busLineIDList = new ArrayList<String>();
		stationName = new ArrayList<String>();
		LatLng cenpt = new LatLng(40.8,111.7); 
		//定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder()
		.target(cenpt)
		.build();
		//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		//改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		mSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
			
			@Override
			public void onGetPoiResult(PoiResult result) {
				// TODO Auto-generated method stub
				 if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			           Toast.makeText(getApplication(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
			            return;
			        }
				 busLineIDList.clear();
				 for (PoiInfo poi : result.getAllPoi()) {
			            if (poi.type == PoiInfo.POITYPE.BUS_LINE
			                    || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
			                busLineIDList.add(poi.uid);
			            }
			        }
			        searchNextBusline(null);
			        //route = null;
			}
			
			@Override
			public void onGetPoiIndoorResult(PoiIndoorResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetPoiDetailResult(PoiDetailResult arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mBusLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
			
			@Override
			public void onGetBusLineResult(BusLineResult result) {
				// TODO Auto-generated method stub
				mBaiduMap.clear();
		        route = result;
		        overlay.removeFromMap();
		        mBaiduMap.setOnMarkerClickListener(overlay);
		        overlay.setData(result);
		        overlay.addToMap();
		        overlay.zoomToSpan();
			}
		});
		
		
	}
	
	public void searchButtonProcess(View v) {
        busLineIDList.clear();
        busLineIndex = 0;
        EditText editCity = (EditText) findViewById(R.id.city);
        EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
        // 发起poi检索，从得到所有poi中找到公交线路类型的poi，再使用该poi的uid进行公交详情搜索
        mSearch.searchInCity((new PoiCitySearchOption()).city(
                editCity.getText().toString())
                        .keyword(editSearchKey.getText().toString()));
    }

    public void searchNextBusline(View v) {
    	
        if (busLineIndex >= busLineIDList.size()) {
            busLineIndex = 0;
        }
        if (busLineIndex >= 0 && busLineIndex < busLineIDList.size()
                && busLineIDList.size() > 0) {
            mBusLineSearch.searchBusLine((new BusLineSearchOption()
                    .city(((EditText) findViewById(R.id.city)).getText()
                           .toString()).uid(busLineIDList.get(busLineIndex))));

            busLineIndex++;
        }
    }
    
    public void showDetail(View v){
    	if(route==null){
    		Toast.makeText(getApplication(), "请先查询路线", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	stationName.clear();
    	
    	for(int i=0;i<route.getStations().size();i++){
    		stationName.add(route.getStations().get(i).getTitle());
    	}
    	ListView listView=new ListView(this);
    	ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stationName);
    	listView.setAdapter(adapter);
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	builder.setView(listView).create().show();
    }
    
    

	
	 @Override
	    protected void onPause() {
	        super.onPause();
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();
	    }

	    @Override
	    protected void onDestroy() {
	        mSearch.destroy();
	        mBusLineSearch.destroy();
	        super.onDestroy();
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mymap, menu);
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
