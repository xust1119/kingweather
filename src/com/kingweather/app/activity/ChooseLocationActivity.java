package com.kingweather.app.activity;

import java.util.List;

import com.kingweather.app.R;
import com.kingweather.app.model.LocationInfo;
import com.kingweather.app.util.HttpUtil;
import com.kingweather.app.util.Utility;
import com.kingweather.app.util.HttpUtil.HttpCallbackListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ChooseLocationActivity extends Activity{
	public static final String LOCATIONNAME = "location";
	private ListView listView;
	private ProgressDialog progressDialog;
	private String locationName;
	private List<LocationInfo> locationinfos;
	
	private void init(){
		listView = (ListView)findViewById(R.id.locationsListView);
		locationName = getIntent().getExtras().getString(LOCATIONNAME);
		
	}
	
	private void initListView(List<LocationInfo> infos){
		locationinfos = infos;
		String[] strInfos = new String[infos.size()];
		for(int i = 0; i < infos.size(); ++i){
			strInfos[i] = infos.get(i).toString();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfos);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int index,
					long arg3) {
				LocationInfo selectedLocation = locationinfos.get(index);
				Intent i = new Intent();
				i.putExtra("selectItemCode", selectedLocation.getId());
				i.putExtra("selectedItemName", selectedLocation.toString());
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}
	
	private void queryFromHttp(String name){
		showProgressDialog();
		String address = "http://apis.baidu.com/apistore/weatherservice/citylist?cityname=" + name;
		
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(final String response) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						List<LocationInfo> infos = Utility.handleLocationResponse(response);
						closeProgressDialog();
						
						if(infos.size() == 0){
							Toast.makeText(ChooseLocationActivity.this, "无法查询到输入的地点，请检查！", Toast.LENGTH_SHORT).show();
							finish();
						}
						else{
							initListView(infos);
						}
					}
				});
				
				
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						closeProgressDialog();
						Toast.makeText(ChooseLocationActivity.this, "查询失败哇~~", Toast.LENGTH_SHORT).show();
						finish();
					}
				});
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_activity);
		
		init();
		queryFromHttp(locationName);
	}
	
	public void showProgressDialog(){
		if(progressDialog == null){
			progressDialog = new ProgressDialog(this);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage("加载中。。。。。。");
		}
		progressDialog.show();
	}
	
	public void closeProgressDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
	
	
	public static Intent startAction(Context context, String locationName){
		Intent i = new Intent(context, ChooseLocationActivity.class);
		i.putExtra(LOCATIONNAME, locationName);
		return i;
	}
}
