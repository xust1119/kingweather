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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ChooseLocationActivity extends Activity implements OnClickListener {
	public static final String LOCATIONNAME = "location";
	private ListView listView;
	private Button okButton;
	private ProgressDialog progressDialog;
	private String locationName;
	
	private void init(){
		listView = (ListView)findViewById(R.id.locationsListView);
		okButton = (Button)findViewById(R.id.chooseLocationBtn);
		locationName = getIntent().getExtras().getString(LOCATIONNAME);
		
		okButton.setOnClickListener(this);
	}
	
	private void initListView(List<LocationInfo> infos){
		String[] strInfos = new String[infos.size()];
		for(int i = 0; i < infos.size(); ++i){
			strInfos[i] = infos.get(i).toString();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfos);
		listView.setAdapter(adapter);
	}
	
	private void queryFromHttp(String name){
		showProgressDialog();
		String address = "http://apis.baidu.com/apistore/weatherservice/citylist?cityname=" + name;
		
		Log.d("MyTag", address);
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(final String response) {
				// TODO Auto-generated method stub
				Log.d("MyTag", "in onFinish~~~");
				Log.d("MyTag", response.toString());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						List<LocationInfo> infos = Utility.handleLocationResponse(response);
						initListView(infos);
						closeProgressDialog();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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
