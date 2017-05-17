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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etc.adapter.LvArticleCollectionAdapter;
import com.etc.entity.ArticleCollection;
import com.etc.task.LoadArticleCollectionTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class ArticleCollectionActivity extends Activity {
	
	List<ArticleCollection> data;
	Handler handler;
	ListView lvArticleCollection;
	ProgressBar pbpbLoadArticleCollection;
	LvArticleCollectionAdapter adapter ;
	int pagesize = 3;
	int lastid = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_collection);
		
		this.lvArticleCollection = (ListView) findViewById(R.id.lvArticleCollection);
		this.pbpbLoadArticleCollection = (ProgressBar) findViewById(R.id.pbLoadArticleCollection);
		this.data = new ArrayList<ArticleCollection>();
		this.adapter = new LvArticleCollectionAdapter(getApplicationContext(), data);
		this.lvArticleCollection.setAdapter(adapter);
		this.handler = new Handler(new LoadArticleCollectionCallback());
		new LoadArticleCollectionTask(lastid, pagesize, handler).start();
	}

	
	class LoadArticleCollectionCallback implements Callback{
		@Override
		public boolean handleMessage(Message msg) {
			pbpbLoadArticleCollection.setVisibility(View.GONE);
			if(msg.arg1==0){
				//System.out.println(msg.obj);
				parseGSONString(msg.obj.toString());
			}else{
				Toast.makeText(getApplicationContext(), "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			return false;
		}
		
	}
	
	//瑙ｆ瀽瀛楃涓�
	private void parseGSONString(String gstr){
		Gson gson = new Gson();
		Type type = new TypeToken<List<ArticleCollection>>(){}.getType();
		List<ArticleCollection> colls = gson.fromJson(gstr, type);
		for(ArticleCollection coll:colls){
			data.add(coll);
		}
		
		lastid = data.get(data.size()-1).getArticleid();
		lvArticleCollection.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
	}
	
public void back1(View v){
		
		Intent intent = new Intent(ArticleCollectionActivity.this, PersonInfoActivity.class);
		
		startActivityForResult(intent, 1);

}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_collection, menu);
		return true;
	}

}
