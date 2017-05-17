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
import com.etc.entertainment.MainActivity;
import com.etc.entertainment.RegisterActivity;
import com.etc.entity.User;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public class RegisterTask extends AsyncTask<String, Void, Void> {

	Context context;
	Handler handler;
	ProgressDialog RegisterDialog;
	String url="http://10.0.2.2:8080/Entertainment/RegisterServlet";
	public RegisterTask(Context context,ProgressDialog RegisterDialog,Handler handler)
	{
		this.context=context;
		this.RegisterDialog=RegisterDialog;
		this.handler=handler;
	}
	@Override
	protected void onPreExecute() {
		//RegisterDialog.setMessage("正在注册中...");
		super.onPreExecute();
	}
	
	@Override
	protected Void doInBackground(String... arg0) {
		
	    HttpClient client = new DefaultHttpClient();
	    HttpPost request = new HttpPost(url);
					
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", arg0[0]));
		params.add(new BasicNameValuePair("password", arg0[1]));

		try {
		    request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));

		    HttpResponse response = client.execute(request);
		    
	        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		
		        String responseText = EntityUtils.toString(response.getEntity()); 
		        System.out.println("response="+responseText);
		        if(responseText.charAt(0)=='f')
		        {
		        	System.out.println("进入错误区域"+responseText.charAt(0));
		        	return null;
		        }
		        System.out.println("未进入错误区域"+responseText.charAt(0));
		        Gson gson=new Gson();
		        User user=gson.fromJson(responseText, User.class);
		        	    
		    	//获取全局类
		    	MyApp myApp = (MyApp) context;    
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
	
		MyApp myApp = (MyApp) context; 
		if(myApp.getUser()!=null)
		{
			handler.sendEmptyMessage(1);
		}else{
			handler.sendEmptyMessage(-1);
		}
		super.onPostExecute(result);
	}

}
