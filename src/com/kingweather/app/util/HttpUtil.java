package com.kingweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtil {
	
	public interface HttpCallbackListener{
		void onFinish(String response);
		void onError(Exception e);
	}
	
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection conn = null;
				Log.d("MyTag", address);
				try {
					URL url = new URL(address);
					conn = (HttpURLConnection)url.openConnection();
					conn.setReadTimeout(8000);
					conn.setRequestMethod("GET");
					conn.setReadTimeout(8000);
					conn.setRequestProperty("apikey",  "b121661547925f82c7b63ba1bacca7bb");
					
					InputStream ins = conn.getInputStream();
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(ins,"UTF-8"));
					StringBuilder response = new StringBuilder();
					String line;
					
					while((line = bufReader.readLine()) != null){
						response.append(line);
					}
					
					if(listener != null){
						listener.onFinish(response.toString());
					}
				}catch(Exception e){
					if(listener != null){
						listener.onError(e);
					}
				}finally{
					if(conn != null){
						conn.disconnect();
					}
				}
			}
		}).start();;
	}
}
