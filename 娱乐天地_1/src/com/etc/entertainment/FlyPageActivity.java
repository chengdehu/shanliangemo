package com.etc.entertainment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;

public class FlyPageActivity extends Activity {

	Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//»•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.activity_fly_page);
		
		handler =new Handler(){
			@Override
			public void handleMessage(Message msg) {

					Intent intent =new Intent(FlyPageActivity.this,LoginActivity.class);
					startActivity(intent);
				super.handleMessage(msg);
			}
		};
		new Thread(new DelayThread()).start();
	}

	
	
	public class DelayThread implements Runnable
	{

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.sendEmptyMessage(1);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fly_page, menu);
		return true;
	}

}
