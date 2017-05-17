package com.etc.entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.etc.adapter.ArticleAdapter;
import com.etc.adapter.MyListView;
import com.etc.adapter.TopicAdapter;
import com.etc.app.MyApp;
import com.etc.asynctask.ArticleLoadTask;
import com.etc.asynctask.FindFriendsTask;
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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class GroundActivity extends Activity {
	private int count = 5;
	private SharedPreferences sp;
	private int userid;
	private Article article;
	private Handler handler;
	private ViewFlipper viewFlipper;
	private GridView topicGridView;
	private MyListView articleList;
	private Button btnAddArticle,btnSearch;
	private EditText searchTxt;
	private String ids;
	private ScrollView scrollView;
	MyApp myApp;
	
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> topicList = new ArrayList<Map<String, Object>>();
	
	private String[]topics = {"和谐", "oxygen", "Olympics", "car"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ground);

		sp = getSharedPreferences("User1Info", MODE_PRIVATE);
		//获取全局类
    	myApp = (MyApp) this.getApplicationContext();        //相当于拿到session对象
    	//设置全局变量的值
		userid = myApp.getUser().getUserid();
		
		this.handler = new Handler(new FindFriendsTaskCallBack());
		new FindFriendsTask(userid,handler).start();
		

		scrollView = (ScrollView) findViewById(R.id.scrollView);
		btnAddArticle = (Button) findViewById(R.id.btnAdd);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		searchTxt = (EditText) findViewById(R.id.searchTxt);
		articleList = (MyListView) findViewById(R.id.articleList);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		topicGridView = (GridView) findViewById(R.id.topicGridView);

		viewFlipper.setOnClickListener(new OnTopicListenerImpl());
		
		btnAddArticle.setOnClickListener(new OnAddClickListenerImpl());	
		btnSearch.setOnClickListener(new OnSearchClickListenerImpl());

		final ArticleAdapter groundAdapter = new ArticleAdapter(this, dataList);
		articleList.setAdapter(groundAdapter);	
		groundAdapter.notifyDataSetChanged();
		articleList.setOnItemClickListener(new OnItemClickListenerImpl());
		setListViewHeightBasedOnChildren(articleList);


		TopicAdapter topicadapter = new TopicAdapter(this, topicList);
		topicGridView.setAdapter(topicadapter);
		topicGridView.setOnItemClickListener(new OnTopicClickListenerImpl());
		
		showTopics();
		this.handler = new Handler(new ArticleLoadTaskCallBack());
		new ArticleLoadTask(count + "", handler).start();
		viewFlipper.startFlipping();
		
		scrollView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View view, MotionEvent motionEvent) {
				int startY = 0, endY = 0;
				// TODO Auto-generated method stub
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startY = view.getScrollY();
					break;
				case MotionEvent.ACTION_UP:
					endY = view.getScrollY();
					if(startY == 0 && endY == 0){
						count += 5;
						dataList.clear();
						new ArticleLoadTask(count + "", handler).start();						
						groundAdapter.setCount(count);
						groundAdapter.notifyDataSetChanged();
						Toast.makeText(getApplicationContext(), "刷新成功！", Toast.LENGTH_SHORT).show();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					int scrollY = view.getScrollY();
					if(scrollY == 0)
						viewFlipper.startFlipping();
					else
						viewFlipper.stopFlipping();							
					break;               
				default:     
					break;             
					}           
				return false;
			}
		});
	}
	
	public void showTopics(){
		for(int i = 0; i < topics.length; i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("topicphoto", R.drawable.hot);
			map.put("topic", topics[i]);
			topicList.add(map);
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

	private class FindFriendsTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				ids = (String)msg.obj;
				ids = ids.replace("[", "");
				ids = ids.replace("]", "");
				System.out.println(ids);
				sp = getSharedPreferences("User1Info", MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.putString("friends_id", ids);
				editor.commit();
			}
			return true;		
		}		
	}
	
	private class ArticleLoadTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
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
	
	private class OnAddClickListenerImpl implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(GroundActivity.this,AddArticleActivity.class);
			startActivity(intent);
		}		
	}

	private class OnSearchClickListenerImpl implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(!"".equals(searchTxt.getText().toString())){
				String searchString = searchTxt.getText().toString();
				Intent intent = new Intent(GroundActivity.this,TopicActivity.class);
				intent.putExtra("topic", searchString);
				startActivity(intent);	
			}else
				Toast.makeText(getApplicationContext(), "请输入话题再进行搜索！！！！！！！！", Toast.LENGTH_SHORT).show();
		}		
	}
	
	public class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
		
		Intent intent = new Intent(GroundActivity.this,ArticleActivity.class);
		intent.putExtra("articleid", (Integer) map.get("articleid"));
		startActivity(intent);		
		finish();
		}
	}

	public class OnTopicClickListenerImpl implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
		
		Intent intent = new Intent(GroundActivity.this,TopicActivity.class);
		intent.putExtra("topic", map.get("topic").toString());
		startActivity(intent);		
		}
	}
	
	public class OnTopicListenerImpl implements OnClickListener{

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
		view = viewFlipper.getCurrentView();
		String topic = (String) view.getTag();
		Intent intent = new Intent(GroundActivity.this,TopicActivity.class);
		intent.putExtra("topic", topic);
		startActivity(intent);		
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ground, menu);
		return true;
	}
}
