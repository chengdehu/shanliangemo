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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etc.adapter.LvItemCommentAdapter;
import com.etc.entity.ItemComment;
import com.etc.task.LoadItemCommentTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CommentActivity extends Activity {
	
	List<ItemComment> data;
	Handler handler;
	ListView lvItemComment;
	ProgressBar pbpbLoadItemComment;
	LvItemCommentAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		
		this.lvItemComment = (ListView) findViewById(R.id.lvItemComment); 
		this.pbpbLoadItemComment = (ProgressBar) findViewById(R.id.pbLoadItemComment);
		this.data = new ArrayList<ItemComment>();	
		this.adapter = new LvItemCommentAdapter(getApplicationContext(), data);
		this.lvItemComment.setAdapter(adapter);
		this.handler = new Handler(new LoadItemCommentCallback());
		new LoadItemCommentTask(handler).start();	
		
	}
	//娑堟伅鍥炶皟
	class LoadItemCommentCallback implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			pbpbLoadItemComment.setVisibility(View.GONE);
			if(msg.arg1==0){
				//System.out.println(msg.obj);
				parseGSONString(msg.obj.toString());
			}else{
				Toast.makeText(getApplicationContext(), "鏁版嵁璇锋眰澶辫触", Toast.LENGTH_SHORT).show();
			}
			return false;
		}
		
	}
	
	//瑙ｆ瀽json瀛楃涓�
	private void parseGSONString(String gstr){
		Gson gson = new Gson();
		Type type = new TypeToken<List<ItemComment>>(){}.getType();
		List<ItemComment> coms = gson.fromJson(gstr, type);
		for(ItemComment com:coms){
			data.add(com);
		}
		//System.out.println(data);	
		lvItemComment.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
	}
	


}
