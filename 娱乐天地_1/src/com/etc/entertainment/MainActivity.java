package com.etc.entertainment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.etc.adapter.Entertainmentadapter;
import com.etc.app.MyApp;
import com.etc.entity.Entertainment;
import com.etc.entity.User;
import com.etc.task.LoadEntertainmentTask;
import com.etc.task.LoadNewsTask;
import com.etc.task.LoadTypeTask;
import com.etc.entertainment.MainActivity.LoadEntertainmentCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends Activity {
	
	private ListView lstnews;
 	private String responseText;
	private Entertainmentadapter eadapter;
	private List<Entertainment> data;
	private Handler handler;
	private String itemtype = "0";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//»•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.activity_main);
		lstnews = (ListView) findViewById(R.id.lstnews);
		this.data = new ArrayList<Entertainment>();
		this.eadapter = new Entertainmentadapter(getApplicationContext(),data);
		
		lstnews.setAdapter(eadapter);

		lstnews.setOnItemClickListener(new OnItemClickListenerImpl());		
		this.handler = new Handler(new LoadEntertainmentCallback());
		new LoadEntertainmentTask(handler).start();
		
	}
	class LoadEntertainmentCallback implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			if(msg.arg1 == 0){
				praseGSONString(msg.obj.toString());// TODO Auto-generated method stub
			}else{
				Toast.makeText(getApplicationContext(), "Õ¯¬Á«Î«Û ß∞‹",Toast.LENGTH_SHORT).show();
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
    	 lstnews.setVisibility(View.VISIBLE);
    	 eadapter.notifyDataSetChanged();
    
			}			


     
     public void search(View v){
    	 Intent intent = new Intent(MainActivity.this, SearchActivity.class);			
	     startActivity(intent);
     }
     
     public void news(View v){
     	this.eadapter = new Entertainmentadapter(getApplicationContext(),data);
      	data.clear();
      	eadapter.notifyDataSetChanged();
   		lstnews.setAdapter(eadapter);
   		this.handler = new Handler(new LoadEntertainmentCallback());
  		new LoadEntertainmentTask(handler).start();
      }
      
      public void report(View v){
     	this.eadapter = new Entertainmentadapter(getApplicationContext(),data);
     	data.clear();
     	eadapter.notifyDataSetChanged();
     	itemtype = "1";
  		lstnews.setAdapter(eadapter);
  		this.handler = new Handler(new LoadEntertainmentCallback());
  		MyApp myApp = (MyApp) getApplicationContext();  
  		User user=myApp.getUser();
 		new LoadNewsTask(user.getUserid(),itemtype,handler).start();
      }
      
      public void game(View v){
     	this.eadapter = new Entertainmentadapter(getApplicationContext(),data);
      	data.clear();
      	eadapter.notifyDataSetChanged();
      	itemtype = "gam";
   		lstnews.setAdapter(eadapter);
   		this.handler = new Handler(new LoadEntertainmentCallback());
  		MyApp myApp = (MyApp) getApplicationContext();  
  		User user=myApp.getUser();
   		new LoadNewsTask(user.getUserid(),itemtype,handler).start();
      }
      
      public void movie(View v){
     	 this.eadapter = new Entertainmentadapter(getApplicationContext(),data);
       	data.clear();
       	eadapter.notifyDataSetChanged();
       	itemtype = "mov";
    		lstnews.setAdapter(eadapter);
    		this.handler = new Handler(new LoadEntertainmentCallback());
      		MyApp myApp = (MyApp) getApplicationContext();  
      		User user=myApp.getUser();
       		new LoadNewsTask(user.getUserid(),itemtype,handler).start();
      }
      
      public void music(View v){
     	 this.eadapter = new Entertainmentadapter(getApplicationContext(),data);
       	data.clear();
       	eadapter.notifyDataSetChanged();
       	itemtype = "mus";
    		lstnews.setAdapter(eadapter);
    		this.handler = new Handler(new LoadEntertainmentCallback());
      		MyApp myApp = (MyApp) getApplicationContext();  
      		User user=myApp.getUser();
       		new LoadNewsTask(user.getUserid(),itemtype,handler).start();
      }
      
      public void anime(View v){
     	 this.eadapter = new Entertainmentadapter(getApplicationContext(),data);
       	data.clear();
       	eadapter.notifyDataSetChanged();
       	itemtype = "ani";
    		lstnews.setAdapter(eadapter);
    		this.handler = new Handler(new LoadEntertainmentCallback());
      		MyApp myApp = (MyApp) getApplicationContext();  
      		User user=myApp.getUser();
       		new LoadNewsTask(user.getUserid(),itemtype,handler).start();
      }
     
     public void recommend(View v)
     {
    	 
     }
     
     public void find(View v)
     {
    	 Intent intent =new Intent(MainActivity.this,DiscoverActivity.class);
    	 startActivity(intent);
     }
     
     public void square(View v)
     {
    	 Intent intent =new Intent(MainActivity.this,GroundActivity.class);
    	 startActivity(intent);
     }
     
     public void message(View v)
     {
    	 Intent intent =new Intent(MainActivity.this,ChatFriendsActivity.class);
    	 startActivity(intent);
     }
     
     public void me(View v)
     {
    	 Intent intent =new Intent(MainActivity.this,PersonInfoActivity.class);
    	 startActivity(intent);
     }
     
     private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Entertainment entertainment = data.get(arg2);
			int likes = entertainment.getItemlikes();
			int i = entertainment.getItemid();
			 Intent intent = new Intent(MainActivity.this, MainpageActivity.class);
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}