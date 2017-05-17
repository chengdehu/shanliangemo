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

public class UpdateArticleTask extends Thread {
	
	int articleid;
	int like;
	int dislike;
	Handler handler;
	
	public UpdateArticleTask(int articleid, int like, int dislike,
			Handler handler) {
		super();
		this.articleid = articleid;
		this.like = like;
		this.dislike = dislike;
		this.handler = handler;
	}
	
	@Override
	public void run() {
		String url = "http://10.0.2.2:8080/Entertainment/UpdateArticleServlet?";
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url + "articleid=" + articleid +"&&like=" + like + "&&dislike=" + dislike);
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
