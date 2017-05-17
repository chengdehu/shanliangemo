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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

public class LoadChatListTask extends Thread {

	int fromUserid = 1;
	// int toUserid = 2;
	Handler handler;
	String responseText;

	public LoadChatListTask(int fromUserid, Handler handler) {
		this.fromUserid = fromUserid;
		// this.toUserid = toUserid;
		this.handler = handler;
	}

	public void run() {
		// 杩滅▼鏁版嵁鍦板潃
		String url = "http://10.0.2.2:8080/Entertainment/ChatListServlet?fromUserid="
				+ fromUserid;
		// 璁剧疆瓒呮椂鍊�
		HttpParams httpparas = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpparas, 3000);
		HttpConnectionParams.setSoTimeout(httpparas, 5000);
		// 瀹炰緥鍖朒ttpClient瀵硅薄
		HttpClient client = new DefaultHttpClient();

		// 瀹炰緥鍖杙ost璇锋眰瀵硅薄
		HttpPost request = new HttpPost(url);

		// 寤虹珛娑堟伅
		Message message = this.handler.obtainMessage();

		// 鍙戝嚭post璇锋眰
		try {
			// 灏佽璇锋眰鍙傛暟
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("fromUserid", fromUserid + ""));

			// 璁剧疆璇锋眰鍙傛暟
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// 鍙戝嚭post璇锋眰
			HttpResponse response = client.execute(request);

			// 鎺ユ敹杩斿洖鏁版嵁
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				responseText = EntityUtils.toString(response.getEntity());

				message.arg1 = 0;
				message.obj = responseText;
			}
		} catch (ClientProtocolException e) {
			message.arg1 = 1;
			e.printStackTrace();
		} catch (IOException e) {
			message.arg1 = 1;
			e.printStackTrace();
		}
		handler.sendMessage(message);
	}
}
