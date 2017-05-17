package com.etc.entertainment;

import com.etc.app.MyApp;
import com.etc.asynctask.WriteArticleTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddArticleActivity extends Activity {

	private int userid;
	private EditText titleTxt,contTxt;
	private Button btnCommit;
	private Handler handler;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_article);
		
		sp = getSharedPreferences("User1Info", MODE_PRIVATE);
		//获取全局类
    	MyApp myApp = (MyApp) this.getApplicationContext();        //相当于拿到session对象
    	//设置全局变量的值
 
		userid = myApp.getUser().getUserid();
		titleTxt = (EditText) findViewById(R.id.titleTxt);
		contTxt = (EditText) findViewById(R.id.contTxt);
		btnCommit = (Button) findViewById(R.id.btnCommit);
		
		btnCommit.setOnClickListener(new OnClickListenerImpl());
		
	}
	
	private class WriteArticleTaskCallBack implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg1 == 1){
				Intent intent = new Intent(AddArticleActivity.this,GroundActivity.class);
				startActivity(intent);
				finish();
			}
			return true;
		}		
	}
	
	public class OnClickListenerImpl implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			handler = new Handler(new WriteArticleTaskCallBack());
			if(!"".equals(titleTxt.getText().toString())){
				if(!"".equals(contTxt.getText().toString())){
					new WriteArticleTask(userid, titleTxt.getText().toString(), contTxt.getText().toString(), handler).start();	
				}else
					Toast.makeText(getApplicationContext(), "请输入文章内容！", Toast.LENGTH_SHORT).show();
			}else
				Toast.makeText(getApplicationContext(), "请输入文章标题", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_article, menu);
		return true;
	}

}
