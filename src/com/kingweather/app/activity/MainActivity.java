package com.kingweather.app.activity;


import com.kingweather.app.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements OnClickListener {
	
	private EditText locationEditText;
	private Button queryLoactionBtn;
	
	private void init(){
		locationEditText = (EditText)findViewById(R.id.locationEditText);
		queryLoactionBtn = (Button)findViewById(R.id.queryLoactionBtn);
		
		queryLoactionBtn.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		init();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String name = locationEditText.getText().toString();
		startActivity(ChooseLocationActivity.startAction(this, name));
	}
}
