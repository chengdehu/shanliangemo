package com.etc.entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.etc.adapter.ArticleAdapter;
import com.etc.asynctask.TopicTask;
import com.etc.entity.Article;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class TopicActivity extends Activity {

	private String topic = "";
	private TextView topicView;
	private ListView list;
	private ArticleAdapter adapter;
	private Handler handler;
	private Article article;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_topic);
		Intent intent = this.getIntent();
		topic = intent.getStringExtra("topic");
		
		topicView = (TextView) findViewById(R.id.topic);
		list = (ListView) findViewById(R.id.list);
		
		adapter = new ArticleAdapter(this, dataList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListenerImpl());
		this.handler = new Handler(new TopicTaskCallBack());
		new TopicTask(topic,handler).start();
	}

	private class TopicTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				topicView.setText(topic);
				
				Gson gson = new Gson();
				JsonParser parser = new JsonParser();
				JsonElement el = parser.parse((String)msg.obj);
				 
				JsonArray jsonArray = null;
				if(el.isJsonArray())
					jsonArray = el.getAsJsonArray();				 
				Iterator it = jsonArray.iterator();
				
				while(it.hasNext()){
					 JsonElement e = (JsonElement)it.next();
					 article =  gson.fromJson(e, Article.class);						 
					 Map<String, Object> map = new HashMap<String, Object>();
					 map.put("articleid", article.getArticleid());	
					 map.put("photo", article.getUser().getPhoto());
					 map.put("name", article.getUser().getUsername());
					 map.put("content", article.getArticlecont());
					 map.put("date", article.getPublishtime());
					 map.put("userid", "" + article.getUserid());
					 dataList.add(map);
				}
			}
			return true;
		}
	}

	public class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
		
		Intent intent = new Intent(TopicActivity.this,ArticleActivity.class);
		intent.putExtra("articleid", (Integer) map.get("articleid"));
		startActivity(intent);		
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.topic, menu);
		return true;
	}

}
