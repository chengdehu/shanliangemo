package com.etc.entertainment;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.etc.entity.*;

public class ItemContentActivity extends Activity {

	TextView txtTopic,txtContent;
	ImageView imgContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//»•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.activity_item_content);
		Intent intent=this.getIntent();
		String entertainmentgson=intent.getStringExtra("entertainment");
		Gson gson=new Gson();
		Entertainment entertainment=gson.fromJson(entertainmentgson, Entertainment.class);
		txtTopic.setText(entertainment.getIteminfor());
			
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.item_content, menu);
		return true;
	}

}
