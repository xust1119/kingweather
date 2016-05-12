package com.kingweather.app.activity;


import com.kingweather.app.R;
import com.kingweather.app.model.WeatherInfo;
import com.kingweather.app.util.HttpUtil;
import com.kingweather.app.util.HttpUtil.HttpCallbackListener;
import com.kingweather.app.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {
	private final static int REQUEST_START_CHOOSE_CODE = 1;
	private EditText locationEditText;
	private Button queryLoactionBtn;
	private TextView dateTextView, locationTextView, weatherInfoTextView, tempTextView;
	private LinearLayout weatherInfoLayout;
	
	private void init(){
		locationEditText = (EditText)findViewById(R.id.locationEditText);
		queryLoactionBtn = (Button)findViewById(R.id.queryLoactionBtn);
		dateTextView = (TextView)findViewById(R.id.publish_date);
		locationTextView = (TextView)findViewById(R.id.city_name);
		weatherInfoTextView = (TextView)findViewById(R.id.weather_detail);
		tempTextView = (TextView)findViewById(R.id.temp);
		weatherInfoLayout = (LinearLayout)findViewById(R.id.weather_info);
		
		queryLoactionBtn.setOnClickListener(this);
		
		showWeather();
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		init();
	}
	
	private void queryWeather(String cityCode){
		String address = "http://apis.baidu.com/apistore/weatherservice/cityid?cityid=" + cityCode;
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Utility.handleWeatherJson(MainActivity.this, response);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						showWeather();
					}
				});
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "查询失败哇~~", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void showWeather(){
		if(!Utility.isSelectedLocation(this)){
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			return;
		}
		
		weatherInfoLayout.setVisibility(View.VISIBLE);
		WeatherInfo weatherInfo = Utility.getWeatherInfo(this);
		dateTextView.setText(weatherInfo.getDate());
		locationTextView.setText(weatherInfo.getCityName());
		weatherInfoTextView.setText(weatherInfo.getDescription());
		tempTextView.setText(String.format("%s℃~%s℃", weatherInfo.getTemp1(), weatherInfo.getTemp2()));

}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String name = locationEditText.getText().toString();
		startActivityForResult(ChooseLocationActivity.startAction(this, name), REQUEST_START_CHOOSE_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			locationEditText.setText(data.getStringExtra("selectedItemName"));
			queryWeather(data.getStringExtra("selectItemCode"));
		}
	}
}
