package com.etc.asynctask;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

public class ArticleLoadTask extends Thread {
	String count;
	int userid = 0;
	Handler handler;
	
	public ArticleLoadTask(int userid, Handler handler) {
		super();
		this.userid = userid;
		this.handler = handler;
	}

	public ArticleLoadTask(String count, Handler handler) {
		super();
		this.count = count;
		this.handler = handler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String url = "";
		if(userid == 0)
			url = "http://10.0.2.2:8080/Entertainment/ArticleListServlet?count=" + count;
		else if(userid > 0)
			url = "http://10.0.2.2:8080/Entertainment/UserArticleServlet?userid=" + userid;
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		Message message = this.handler.obtainMessage();
		try {
			HttpResponse response = client.execute(request);							
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){					
				String responseText = EntityUtils.toString(response.getEntity());
				message.arg1 = 1;
				message.obj = responseText;
			}				
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			message.arg1 = 0;
		} catch (IOException e) {
			e.printStackTrace();
			message.arg1 = 0;
		}
		handler.sendMessage(message);
	}
	
}
