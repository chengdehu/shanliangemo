package com.etc.entertainment;


import com.etc.listener.btnTouchListener1;
import com.etc.task.LoginTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

	ProgressDialog loginprogress;
	EditText edtUsername,edtPassword;
	String username,password;
	Handler handler;
	Button btnLogin,btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//»•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.activity_login);
		edtUsername=(EditText)findViewById(R.id.edtUsername);
		edtPassword=(EditText)findViewById(R.id.edtPassword);
		btnLogin=(Button) findViewById(R.id.btnLogin);
		btnRegister=(Button) findViewById(R.id.btnRegister);
		
		//btnLogin.setOnTouchListener(new btnTouchListener1());
		//btnRegister.setOnTouchListener(new btnTouchListener1());
		loginprogress=new ProgressDialog(this);
		handler =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==1)
			{
				Intent intent =new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
			}else
			{
				//Toast.makeText(g, text, duration)
			}
			super.handleMessage(msg);
		}};
	}

	public void Login(View v)
	{		
		username=edtUsername.getText().toString();
		password=edtPassword.getText().toString();	
		new LoginTask(this.getApplicationContext(),loginprogress,handler).execute(username,password);
		
	}
	
	public void Register(View v)
	{
		Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
