package com.etc.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.etc.app.MyApp;


import com.etc.entity.User;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;


import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public class LoginTask extends AsyncTask<String, Void, Void> {

	ProgressDialog loginprogress;
	Context context;
	String url="http://10.0.2.2:8080/Entertainment/LoginServlet";
	Handler handler;
	public LoginTask(Context context,ProgressDialog loginprogress,Handler handler)
	{
		this.context=context;
		this.loginprogress=loginprogress;
		this.handler = handler;
    }
	
	@Override
	protected void onPreExecute() {
		loginprogress.setMessage("正在登录中...");
		loginprogress.show();
		super.onPreExecute();
	}
	@Override
	protected Void doInBackground(String... arg0) {
//(1.1)实例化HttpClient对象

	    HttpClient client = new DefaultHttpClient();

// (1.2)实例化HttpPost请求对象，传入访问的url
		
	    HttpPost request = new HttpPost(url);
	  
// (1.3)使用NameValuePair对象封装Post请求参数的名-值对，并设置请求参数的编码格式为UTF_8
					
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", arg0[0]));
		params.add(new BasicNameValuePair("password", arg0[1]));

		try {
			    request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
//(1.4)发送请求，并返回HttpResponse对象
			    HttpResponse response = client.execute(request);
//(1.5)获取HttpResponse对象中的返回信息，并通过Handler对象向主线程发送消息，以便更新UI
			    response = client.execute(request);
		        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			
			        String responseText = EntityUtils.toString(response.getEntity());
			        Gson gson=new Gson();
			        User user=gson.fromJson(responseText, User.class);
			    	//获取全局类
			    	MyApp myApp = (MyApp) context;        //相当于拿到session对象
			    	//设置全局变量的值
			    	myApp.setUser(user); 
			    }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(Void result) {
		//获取全局类
		MyApp myApp = (MyApp)context;      //相当于拿到session对象
		//获取全局变量的值
		User user=myApp.getUser();   
		loginprogress.dismiss();
		if(user==null)
		{//仍处于登录界面
			Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessage(-1);
		}else
		{    //跳转到主页面
			Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessage(1);
			
		}
		super.onPostExecute(result);
	}

}
