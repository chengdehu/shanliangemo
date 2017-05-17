package com.etc.entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.etc.adapter.CommentAdapter;
import com.etc.app.MyApp;
import com.etc.asynctask.ArticleIndexTask;
import com.etc.asynctask.CommentLoadTask;
import com.etc.asynctask.ImageLoadTask;
import com.etc.asynctask.UpdateArticleTask;
import com.etc.asynctask.WriteCommentTask;
import com.etc.entity.Article;
import com.etc.entity.Comment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleActivity extends Activity {

	private int userid;
	private SharedPreferences sp;
	private Comment comment;
	private int like = 0, dislike = 0;
	private String path = "http://10.0.2.2:8080/Entertainment/image/photo/";
	private int articleid;
	private Article article;
	private Handler handler1,handler2,handler3,handler4;
	private TextView nameView, timeView, titleView, contentView;
	private ImageView photo;
	private Button btnComm,btnLike,btnDislike,btnComment;
	private EditText edtComment;
	private LinearLayout commLayout;
	private ListView cmtList;
	private CommentAdapter adapter;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	
	//获取全局类
	MyApp myApp;     //相当于拿到session对象
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_article);
		
		sp = getSharedPreferences("User1Info", MODE_PRIVATE);
		//获取全局类
    	myApp = (MyApp) this.getApplicationContext();        //相当于拿到session对象
    	//设置全局变量的值
		userid = myApp.getUser().getUserid();
		
		
		
		nameView = (TextView) findViewById(R.id.nameIndex);
		timeView = (TextView) findViewById(R.id.publishtime);
		contentView = (TextView) findViewById(R.id.content);
		titleView = (TextView) findViewById(R.id.title);
		edtComment = (EditText) findViewById(R.id.edtComment);
		photo = (ImageView) findViewById(R.id.photoIndex);
		btnComm = (Button) findViewById(R.id.btnComm);
		btnLike = (Button) findViewById(R.id.btnLike);
		btnDislike = (Button) findViewById(R.id.btnDisLike);
		btnComment = (Button) findViewById(R.id.btnComment);
		cmtList = (ListView) findViewById(R.id.cmtList);
		commLayout = (LinearLayout) findViewById(R.id.commLayout);		
		commLayout.setVisibility(View.GONE);
		
		showArticle();
		
		adapter = new CommentAdapter(this, dataList);
		cmtList.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		btnComm.setOnClickListener(new OnClickListenerImpl1());		
		btnLike.setOnClickListener(new OnClickListenerImpl2());
		btnDislike.setOnClickListener(new OnClickListenerImpl3());
		btnComment.setOnClickListener(new OnClickListenerImpl4());
		photo.setOnClickListener(new OnClickListenerImpl5());
		
		this.handler1 = new Handler(new ArticleIndexTaskCallBack());
		new ArticleIndexTask(articleid, handler1).start();
		
		this.handler2 = new Handler(new CommentLoadTaskCallBack());
		new CommentLoadTask(articleid, handler2).start();
		
	}
	
	public void showArticle(){
		
		Intent intent = this.getIntent();
		articleid = intent.getIntExtra("articleid", 0);
		sp = getSharedPreferences("User" + userid + "Info" + articleid , MODE_PRIVATE);
		if("1".equals(sp.getString("like", null)))
			btnLike.setBackgroundResource(R.drawable.liked);
		if("1".equals(sp.getString("dislike", null)))
			btnLike.setBackgroundResource(R.drawable.disliked);
	}
	
	private class ArticleIndexTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				Gson gson = new Gson();
				article =  gson.fromJson((String) msg.obj, Article.class);
				new ImageLoadTask(photo).execute(path + article.getUser().getPhoto());
				nameView.setText(article.getUser().getUsername());
				timeView.setText(article.getPublishtime());
				contentView.setText(article.getArticlecont());	
				titleView.setText(article.getArticletitle());
			}
			return true;
		}	
	}	
	
	private class CommentLoadTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				Gson gson = new Gson();
				JsonParser parser = new JsonParser();
				JsonElement el = parser.parse((String) msg.obj);
				JsonArray jsonArray = null;
				if(el.isJsonArray())
					jsonArray = el.getAsJsonArray();				 
				Iterator it = jsonArray.iterator();				
				while(it.hasNext()){
					 JsonElement e = (JsonElement)it.next();
					 comment =  gson.fromJson(e, Comment.class);						 
					 Map<String, Object> map = new HashMap<String, Object>();
					 map.put("photo", comment.getUser().getPhoto());
					 map.put("userid", comment.getUser().getUserid());
					 map.put("name", comment.getUser().getUsername() + ":");
					 map.put("content", comment.getCommentCont());
					 dataList.add(map);		
				}			
			}
			return true;
		}		
	}	
	
	private class WriteCommentTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 1){
				 Map<String, Object> map = new HashMap<String, Object>();

				 map.put("photo", myApp.getUser().getPhoto());
				 map.put("userid", userid + "");
				 map.put("name", myApp.getUser().getUsername()+":");
				 map.put("content", edtComment.getText().toString());
				 dataList.add(map);
				 adapter = new CommentAdapter(getApplicationContext(), dataList);
				 cmtList.setAdapter(adapter);
			}
			return true;
		}		
	}
	
	private class UpdateArticleTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message arg0) {
			// TODO Auto-generated method stub
			return false;
		}		
	}
	public class OnClickListenerImpl1 implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			commLayout.setVisibility(View.VISIBLE);		
		}	
	}
	
	public class OnClickListenerImpl2 implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			handler4 = new Handler(new UpdateArticleTaskCallBack());
			if(!"1".equals(sp.getString("like", null))){
				if(!"1".equals(sp.getString("dislike", null))){
					Editor editor = sp.edit();
					editor.putString("like", "1");
					editor.commit();
					like = 1;
					dislike = 0;
					new UpdateArticleTask(articleid, like, dislike, handler4).start();
					btnLike.setBackgroundResource(R.drawable.liked);
					Toast.makeText(getApplicationContext(), "赞一个，成功！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "您已踩过这篇文章", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "您已取消赞！", Toast.LENGTH_SHORT).show();
				Editor editor = sp.edit();
				editor.putString("like", "0");
				editor.commit();
				like = -1;
				dislike = 0;
				new UpdateArticleTask(articleid, like, dislike, handler4).start();
				btnLike.setBackgroundResource(R.drawable.like);
			}
		}	
	}
	
	public class OnClickListenerImpl3 implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			handler4 = new Handler(new UpdateArticleTaskCallBack());
			if(!"1".equals(sp.getString("dislike", null))){
				if(!"1".equals(sp.getString("like", null))){
					Editor editor = sp.edit();
					editor.putString("dislike", "1");
					editor.commit();
					dislike = 1;
					like = 0;
					new UpdateArticleTask(articleid, like, dislike, handler4).start();
					Toast.makeText(getApplicationContext(), "踩一个，成功！", Toast.LENGTH_SHORT).show();
					btnDislike.setBackgroundResource(R.drawable.disliked);
				}else{
					Toast.makeText(getApplicationContext(), "您已赞过这篇文章！", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "您已取消踩！", Toast.LENGTH_SHORT).show();
				Editor editor = sp.edit();
				editor.putString("dislike", "0");
				editor.commit();
				dislike = -1;
				like = 0;
				new UpdateArticleTask(articleid, like, dislike, handler4).start();
				btnDislike.setBackgroundResource(R.drawable.dislike);
			}
		}		
	}
	
	public class OnClickListenerImpl4 implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			
			handler3 = new Handler(new WriteCommentTaskCallBack());
			new WriteCommentTask(articleid, userid, edtComment.getText().toString(), handler3).start();
			edtComment.setText("");
			commLayout.setVisibility(View.GONE);		
		}		
	}
	
	public class OnClickListenerImpl5 implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ArticleActivity.this, IndexActivity.class);
			intent.putExtra("userid", "" + article.getUser().getUserid());
			startActivity(intent);
		}		
	}
	
	public void ReturnToG(View v){
		Intent intent = new Intent(ArticleActivity.this, GroundActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article, menu);
		return true;
	}
}
