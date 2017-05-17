package com.etc.entertainment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class DiscoverActivity extends Activity {

	Button button1,button2,button3,button4,button5,button6,button7,button8,button9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//»•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.activity_discover);
		button1=(Button) findViewById(R.id.btnAnimation);
		button2=(Button) findViewById(R.id.btnMovie);
		button3=(Button) findViewById(R.id.btnGame);
		button4=(Button) findViewById(R.id.btnNovel);
		button5=(Button) findViewById(R.id.btnMusic);
		button6=(Button) findViewById(R.id.btnStar);
		button7=(Button) findViewById(R.id.btnSport);
		button8=(Button) findViewById(R.id.btnFood);
		button9=(Button) findViewById(R.id.btnVideo);
		button1.setOnClickListener(new ClickListener());
		button2.setOnClickListener(new ClickListener());
		button3.setOnClickListener(new ClickListener());
		button4.setOnClickListener(new ClickListener());
		button5.setOnClickListener(new ClickListener());
		button6.setOnClickListener(new ClickListener());
		button7.setOnClickListener(new ClickListener());
		button8.setOnClickListener(new ClickListener());
		button9.setOnClickListener(new ClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.discover, menu);
		return true;
	}
	public class ClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			int category;
			switch(arg0.getId())
			{
			case R.id.btnAnimation:
				category=1;
				break;
			case R.id.btnMovie:
				category=2;
				break;
			case R.id.btnGame:
				category=3;
				break;
			case R.id.btnNovel:
				category=4;
				break;
			case R.id.btnMusic:
				category=5;
				break;
			case R.id.btnStar:
				category=6;
				break;
			case R.id.btnSport:
				category=7;
				break;
			case R.id.btnFood:
				category=8;
				break;
			case R.id.btnVideo:
				category=9;
				break;
				default:
					category=10;
			}
			Intent intent=new Intent(DiscoverActivity.this,SortActivity.class);
			intent.putExtra("category", category);
			startActivity(intent);
			
		}
	}
	
	public void recommend(View v)
	{
		Intent intent =new Intent(DiscoverActivity.this,MainActivity.class);
		startActivity(intent);
	}
	public void square(View v)
	{
		Intent intent =new Intent(DiscoverActivity.this,GroundActivity.class);
		startActivity(intent);
	}
	public void message(View v)
	{
		Intent intent =new Intent(DiscoverActivity.this,ChatFriendsActivity.class);
		startActivity(intent);
	}
	public void me(View v)
	{
		Intent intent =new Intent(DiscoverActivity.this,PersonInfoActivity.class);
		startActivity(intent);
	}
	

}
