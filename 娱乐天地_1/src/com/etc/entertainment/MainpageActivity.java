package com.etc.entertainment;



import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etc.adapter.CommentAdapter;
import com.etc.app.MyApp;
import com.etc.entity.Entertainment;
import com.etc.entity.ItemComment;
import com.etc.entity.Itemcontent;
import com.etc.entity.User;
import com.etc.task.AsynBitmapTask;
import com.etc.task.LoadCommentTask;
import com.etc.task.LoadContentTask;
import com.etc.task.LoadNewsTask;
import com.etc.task.LoadWriteCommentTask;
import com.etc.task.UpdateCountTask;
import com.etc.task.UpdateLikeTask;
import com.etc.task.UpdateUserCount;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
public class MainpageActivity extends Activity {
	private TextView txtwords;
	private ImageView imgimage;
	private TextView txtnumber;
	private ListView lstcomment;
	private LinearLayout commLayout;
	private EditText edtComment;
	private ItemComment comment;
	Handler handler,handler1,handler2,handler3;
	List<Itemcontent> data;
	String ImageBaseUrl = "http://10.0.2.2:8080/Entertainment/image/photo/";
	int likes,itemid;
	boolean isdone = false;
	String responsetext,edittext;
	private CommentAdapter adapter;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_mainpage);
		txtnumber = (TextView) findViewById(R.id.txtnumber);
		txtwords = (TextView) findViewById(R.id.txtwords);
		imgimage = (ImageView) findViewById(R.id.imgimage);
		lstcomment = (ListView) findViewById(R.id.lstcomment);
		commLayout = (LinearLayout) findViewById(R.id.commLayout);	
		edtComment = (EditText) findViewById(R.id.edtComment);
		commLayout.setVisibility(View.GONE);
		this.data = new ArrayList<Itemcontent>();
		//获取发送的Intent
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		//获取Intent中的参数
		itemid = bundle.getInt("itemid");
		likes = bundle.getInt("likes");
		txtnumber.setText(""+likes);
		this.handler = new Handler(new LoadContentCallback());
		new LoadContentTask(itemid,handler).start();
		this.handler1 = new Handler(){
			public void handleMessage(Message msg) {   				
			}    	
	};
	
	new UpdateCountTask(itemid, handler1).start();
	adapter = new CommentAdapter(this, dataList);
	lstcomment.setAdapter(adapter);
	adapter.notifyDataSetChanged();
	this.handler2 = new Handler(new CommentLoadTaskCallBack());
	new LoadCommentTask(itemid, handler2).start();
	this.handler3 = new Handler(){
		public void handleMessage(Message msg) { 	    		 
	    	 }	
};
	MyApp myApp = (MyApp) getApplicationContext();  
	User user=myApp.getUser();
    new UpdateUserCount(itemid,user.getUserid(), handler3).start();
		

	}
	class LoadContentCallback implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			if(msg.arg1 == 0){
				prasestring(msg.obj.toString());// TODO Auto-generated method stub
			}else{
				Toast.makeText(getApplicationContext(), "网络请求失败",Toast.LENGTH_SHORT).show();
			}
			return false;// TODO Auto-generated method stub
		}
		
	}
	
	private void prasestring(String gstr){
		 Gson gson = new Gson();
    	 Type type = new TypeToken<List<Itemcontent>>(){}.getType();
    	 List<Itemcontent> item = gson.fromJson(gstr, type);
    	 for(Itemcontent it:item){
    		 data.add(it);
    	 }
    	 for(int i = 0;i<data.size();i++){
    	 Itemcontent itemcontent = data.get(i);
    	 
    	 switch(itemcontent.getCount_type()){
    	 case 1:
    		 txtwords.setText(itemcontent.getCount_infor());
    		 break;
    	 case 2:
    		 new AsynBitmapTask(imgimage).execute(ImageBaseUrl + itemcontent.getCount_infor());
    		 break;
    		 default:
    			 break;
    	 }
    	 }
	}
	
	public void applaud(View v){
    	if(isdone){
    		Toast.makeText(getApplicationContext(), "您已点过赞了",Toast.LENGTH_SHORT).show();
    	}else{
    		likes++;
    		txtnumber.setText(""+likes);
    		this.handler = new Handler(){
    			public void handleMessage(Message msg) {   				
    				}    	
    		};
    		new UpdateLikeTask(itemid, handler).start();
    		isdone = true;
    	}
  
    }
	
	private class CommentLoadTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 0){
				Gson gson = new Gson();
				JsonParser parser = new JsonParser();
				JsonElement el = parser.parse((String) msg.obj);
				JsonArray jsonArray = null;
				if(el.isJsonArray())
					jsonArray = el.getAsJsonArray();				 
				Iterator it = jsonArray.iterator();				
				while(it.hasNext()){
					 JsonElement e = (JsonElement)it.next();
					 comment =  gson.fromJson(e, ItemComment.class);						 
					 Map<String, Object> map = new HashMap<String, Object>();
					 map.put("photo", comment.getUser().getPhoto());
					 map.put("userid", comment.getUser().getUserid());
					 map.put("name", comment.getUser().getUsername() + ":");
					 map.put("content", comment.getComment_cont());
					 dataList.add(map);		
				}			
			}
			return true;
		}		
	}	
    
    public void back(View v){
    	finish();
    }
    
    public void comment(View v){
    	commLayout.setVisibility(View.VISIBLE);
    }
    
    public void write(View v){
    	handler3 = new Handler(new WriteCommentTaskCallBack());
    	MyApp myApp= (MyApp)getApplicationContext();
    	User user = myApp.getUser();
		new LoadWriteCommentTask(itemid,user.getUserid(),edtComment.getText().toString(), handler3).start();
		edittext = edtComment.getText().toString();
		edtComment.setText("");
		commLayout.setVisibility(View.GONE);	
    }
    
    private class WriteCommentTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 0){
				 Map<String, Object> map = new HashMap<String, Object>();
				 map.put("photo", "1.gif");
				 MyApp myApp= (MyApp)getApplicationContext();
			     User user = myApp.getUser();
				 map.put("userid", user.getUserid() + "");//全局变量
				 map.put("name", user.getUsername()+":");
				 map.put("content", edittext+"");
				 dataList.add(map);
				 adapter.notifyDataSetChanged();
			}
			return true;
		}		
	}
    

    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainpage, menu);
		return true;
	}

}