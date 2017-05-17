package com.etc.entertainment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.etc.adapter.Entertainmentadapter;
import com.etc.entity.Entertainment;
import com.etc.task.LoadSearchTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResultActivity extends Activity {
	private ListView lstresult;
 	private String responseText;
	private Entertainmentadapter adapter;
	private List<Entertainment> data;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		//获取发送的Intent
		Intent intent = this.getIntent();

		//获取Intent中的参数
		String words = intent.getStringExtra("words");		
				
		lstresult = (ListView) findViewById(R.id.lstlabelresult);
		this.data = new ArrayList<Entertainment>();
		this.adapter = new Entertainmentadapter(getApplicationContext(),data);
		
		lstresult.setAdapter(adapter);
		lstresult.setOnItemClickListener(new OnItemClickListenerImpl());		
		this.handler = new Handler(new LoadEntertainmentCallback());
		new LoadSearchTask(words,handler).start();
		

	}
	
	class LoadEntertainmentCallback implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			if(msg.arg1 == 0){
				praseGSONString(msg.obj.toString());// TODO Auto-generated method stub
			}else{
				Toast.makeText(getApplicationContext(), "网络请求失败",Toast.LENGTH_SHORT).show();
			}
			return false;
		}
		
	}
	
     private void praseGSONString(String gstr){
    	 Gson gson = new Gson();
    	 Type type = new TypeToken<List<Entertainment>>(){}.getType();
    	 List<Entertainment> ente = gson.fromJson(gstr,type);
    	 for(Entertainment ent:ente){
    		 data.add(ent);
    		 
    	 }
    	 lstresult.setVisibility(View.VISIBLE);
    	 adapter.notifyDataSetChanged();
    
			}			
     
     private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

 		@Override
 		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
 				long arg3) {
 			Entertainment entertainment = data.get(arg2);
 			int likes = entertainment.getItemlikes();
 			int i = entertainment.getItemid();
 			 Intent intent = new Intent(SearchResultActivity.this, MainpageActivity.class);
 			 Bundle bundle = new Bundle();
 			 bundle.putInt("itemid", i);
 			 bundle.putInt("likes", likes);
 			 intent.putExtras(bundle);
 				startActivity(intent);// TODO Auto-generated method stub
 			
 		}
     }


     

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
