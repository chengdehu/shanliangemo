package com.etc.entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.etc.adapter.ArticleAdapter;
import com.etc.app.MyApp;
import com.etc.asynctask.AddFriendTask;
import com.etc.asynctask.ArticleLoadTask;
import com.etc.asynctask.CollectArticleTask;
import com.etc.asynctask.DeleteArticleTask;
import com.etc.asynctask.FindUserTask;
import com.etc.asynctask.ImageLoadTask;
import com.etc.entity.Article;
import com.etc.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class IndexActivity extends Activity {

	private String ids;
	private SharedPreferences sp;
	private int userid,index_userid;
	private int articleid, i;
	private Article article;
	private String path = "http://10.0.2.2:8080/Entertainment/image/photo/";
	private Handler handler1,handler2,handler3,handler4,handler5;
	private ImageView photoView;
	private TextView nameView;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	private ArticleAdapter adapter;
	private ListView mylist;
	private Builder builder;
	private Dialog dialog,dlgAdd;
	private Button btnAddFriend;
	private LinearLayout layout;
	MyApp myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
		
		sp = getSharedPreferences("User1Info", MODE_PRIVATE);
		//获取全局类
    	myApp = (MyApp) this.getApplicationContext();        //相当于拿到session对象
    	//设置全局变量的值
		userid = myApp.getUser().getUserid();
		
		//NUllpointer问题
		ids = sp.getString("friends_id", null);

		layout = (LinearLayout) findViewById(R.id.linearlayout_add);
		photoView = (ImageView) findViewById(R.id.photoIndex);
		nameView = (TextView) findViewById(R.id.nameIndex);
		mylist = (ListView) findViewById(R.id.artiListView);
		btnAddFriend = (Button) findViewById(R.id.btnAddFriend);
		
		Intent intent = this.getIntent();
		index_userid = Integer.parseInt(intent.getStringExtra("userid").trim());
		
		if(!"".equals(ids)){
			int[]friends_id = StringtoInt(ids);
			for(int i =0;i < friends_id.length; i++){
				if(index_userid == friends_id[i] || index_userid == userid){
					layout.setVisibility(View.GONE);
					btnAddFriend.setVisibility(View.GONE);
				}
			}
		}
		
		adapter = new ArticleAdapter(this, dataList);
		mylist.setAdapter(adapter);	
		mylist.setOnItemLongClickListener(new LongClick());
		mylist.setOnItemClickListener(new OnItemClickListenerImpl());

		this.handler1 = new Handler(new FindUserTaskCallBack());
		new FindUserTask(index_userid,handler1).start();
		
		this.handler2 = new Handler(new ArticleLoadTaskCallBack());
		new ArticleLoadTask(index_userid,handler2).start();
	}
		

	public int[] StringtoInt(String str) {  
		int ret[] = new int[str.length()];   
		StringTokenizer toKenizer = new StringTokenizer(str, ",");   
		int i = 0;  
		while (toKenizer.hasMoreElements()){   
			ret[i++] = Integer.valueOf(toKenizer.nextToken().trim());  
		}
		return ret;
	}
	
	private class FindUserTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				Gson gson = new Gson();
				User user =  gson.fromJson((String)msg.obj, User.class);
				new ImageLoadTask(photoView).execute(path + user.getPhoto());
				nameView.setText(user.getUsername());
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
	
	private class DeleteArticleTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
				dataList.remove(i);
				adapter.notifyDataSetChanged();
			}else
				Toast.makeText(getApplicationContext(), "删除失败！", Toast.LENGTH_SHORT).show();
			return true;
		}		
	}
	
	private class CollectArticleTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				if(msg.arg2 == 1){
					Toast.makeText(getApplicationContext(), "收藏成功！", Toast.LENGTH_SHORT).show();
				}else
					Toast.makeText(getApplicationContext(), "您已收藏过这篇文章!", Toast.LENGTH_SHORT).show();
			}else
				Toast.makeText(getApplicationContext(), "收藏失败!", Toast.LENGTH_SHORT).show();
			return true;
		}		
	}
	
	
	private class AddFriendTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				layout.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), "正在发送添加好友请求，请等待对方确认！", Toast.LENGTH_SHORT).show();
			    Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_SHORT).show();
			}
			return true;
		}		
	}
	
	private class LongClick implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View arg1,
				int position, long arg3) {
			// TODO Auto-generated method stub
			i = position;
			Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
			articleid = (Integer) map.get("articleid");
			createDialog();
			dialog.show();
			return false;
		}

	}
	
	public class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
		
		Intent intent = new Intent(IndexActivity.this,ArticleActivity.class);
		intent.putExtra("articleid", (Integer) map.get("articleid"));
		startActivity(intent);		
		}
	}
	
	public void createDialog(){
		if(index_userid == userid){
			builder = new Builder(this);
			builder.setTitle("删除操作");
			builder.setMessage("是否删除这篇文章？");
			builder.setPositiveButton("是", new deleteListener());
			builder.setNegativeButton("否", null);	
			dialog = builder.create();
		}else{
			builder = new Builder(this);
			builder.setTitle("收藏操作");
			builder.setMessage("是否收藏这篇文章？");
			builder.setPositiveButton("是", new collectListener());
			builder.setNegativeButton("否", null);	
			dialog = builder.create();
		}
	}
	
	private class deleteListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dlg, int id) {
			// TODO Auto-generated method stub
			if(id == dlg.BUTTON_POSITIVE && dlg == dialog){
				handler3 = new Handler(new DeleteArticleTaskCallBack());
				new DeleteArticleTask(articleid, handler3).start();
			}
		}		
	}
	
	private class collectListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dlg, int id) {
			// TODO Auto-generated method stub	
			if(id == dlg.BUTTON_POSITIVE && dlg == dialog){
				handler4 = new Handler(new CollectArticleTaskCallBack());
				new CollectArticleTask(userid, articleid, handler4).start();
			}
		}		
	}
	
	private class addListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dlg, int id) {
			// TODO Auto-generated method stub	
			if(id == dlg.BUTTON_POSITIVE && dlg == dlgAdd){
				handler5 = new Handler(new AddFriendTaskCallBack());
				new AddFriendTask(userid, index_userid, handler5).start();
			}
		}		
	}
	
	public void Add(View v){
		builder = new Builder(this);
		builder.setTitle("添加好友操作");
		builder.setMessage("是否添加对方为您的好友？");
		builder.setPositiveButton("是", new addListener());
		builder.setNegativeButton("否", null);	
		dlgAdd = builder.create();
		dlgAdd.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}
}
