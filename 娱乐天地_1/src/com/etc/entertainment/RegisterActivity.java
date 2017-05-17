package com.etc.entertainment;

import com.etc.task.RegisterTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	ProgressDialog RegisterDialog;
	EditText edtUsername,edtPassword,edtPasswordsure;
	Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_register);
		//获取组件
		edtUsername=(EditText)findViewById(R.id.edtUsername);
		edtPassword=(EditText)findViewById(R.id.edtPassword);
		edtPasswordsure=(EditText)findViewById(R.id.edtPasswordsure);
		RegisterDialog=new ProgressDialog(this);
		RegisterDialog.setMessage("正在注册中...");
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1)
				{
					Intent intent=new Intent(RegisterActivity.this,MainActivity.class); 
					startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), "用户名重复", Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};
	}

	public void Confirm(View v)
	{
		String username=edtUsername.getText().toString();
		String password=edtPassword.getText().toString();
		String passwordsure=edtPasswordsure.getText().toString();
		if(password.equals(passwordsure))
		{
			new RegisterTask(getApplicationContext(),RegisterDialog,handler).execute(username,password);
		}else{
			Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
		}
	}
	public void Cancel(View v)
	{
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
