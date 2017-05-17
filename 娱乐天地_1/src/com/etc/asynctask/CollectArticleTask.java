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

public class CollectArticleTask extends Thread {
	int userid;
	int articleid;
	Handler handler;
	
	public CollectArticleTask(int userid, int articleid, Handler handler) {
		super();
		this.userid = userid;
		this.articleid = articleid;
		this.handler = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String url = "http://10.0.2.2:8080/Entertainment/ArticleCollectServlet?userid=" + userid + "&&articleid=" + articleid;
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		Message message = this.handler.obtainMessage();
		try {			
			HttpResponse response = client.execute(request);							
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){	
				String responseText = EntityUtils.toString(response.getEntity());
				message.arg1 = 1;
				message.arg2 = Integer.parseInt(responseText.trim());
				//System.out.println(responseText);
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
