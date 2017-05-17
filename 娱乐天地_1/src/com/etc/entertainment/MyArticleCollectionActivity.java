package com.etc.entertainment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etc.adapter.LvMyArticleCollectionAdapter;
import com.etc.entity.MyArticleCollection;
import com.etc.task.LoadMyArticleCollectionTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyArticleCollectionActivity extends Activity {
	
	List<MyArticleCollection> data;
	Handler handler;
	ListView lvMyArticleCollection;
	ProgressBar pbpbLoadMyArticleCollection;
	LvMyArticleCollectionAdapter adapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_collection);
		
		this.lvMyArticleCollection = (ListView) findViewById(R.id.lvArticleCollection);
		this.pbpbLoadMyArticleCollection = (ProgressBar) findViewById(R.id.pbLoadArticleCollection);
		this.data = new ArrayList<MyArticleCollection>();
		this.adapter = new LvMyArticleCollectionAdapter(getApplicationContext(), data);
		this.lvMyArticleCollection.setAdapter(adapter);
		this.handler = new Handler(new LoadMyArticleCollectionCallback());
		new LoadMyArticleCollectionTask(handler).start();
	}

	
	class LoadMyArticleCollectionCallback implements Callback{
		@Override
		public boolean handleMessage(Message msg) {
			pbpbLoadMyArticleCollection.setVisibility(View.GONE);
			if(msg.arg1==0){
				//System.out.println(msg.obj);
				parseGSONString(msg.obj.toString());
			}else{
				Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
			}
			return false;
		}
		
	}
	
	//瑙ｆ瀽瀛楃涓�
	private void parseGSONString(String gstr){
		Gson gson = new Gson();
		Type type = new TypeToken<List<MyArticleCollection>>(){}.getType();
		List<MyArticleCollection> colls = gson.fromJson(gstr, type);
		for(MyArticleCollection coll:colls){
			data.add(coll);
		}
		

		lvMyArticleCollection.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_collection, menu);
		return true;
	}

}
