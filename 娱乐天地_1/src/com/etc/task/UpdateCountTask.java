package com.etc.task;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

public class UpdateCountTask extends Thread {
	Handler handler;
	int itemid;
	public UpdateCountTask(int itemid,Handler handler){
		this.handler = handler;
		this.itemid = itemid;
	}
	
	public void run() {
		String uri =  "http://10.0.2.2:8080/Entertainment/UpdateCount?itemid="+ itemid;
		HttpParams httpparas = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpparas, 3000);
		HttpConnectionParams.setSoTimeout(httpparas, 5000);
		HttpClient client = new DefaultHttpClient(httpparas);
		HttpPost request = new HttpPost(uri);
		Message message = this.handler.obtainMessage();
		try {
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String gstr = EntityUtils.toString(response.getEntity());
				message.arg1 = 0;
				message.obj = gstr;
				
			}else{
				message.arg1 = 1;
				System.out.println("1");
			}
		} catch (ClientProtocolException e) {
			message.arg1 = 1;
			System.out.println("2");// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			message.arg1 = 1;
			System.out.println("3");// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.sendMessage(message);
	}




}
