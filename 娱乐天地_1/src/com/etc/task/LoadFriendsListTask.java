package com.etc.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
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

public class LoadFriendsListTask extends Thread {

	int userid = 1;
	// int toUserid = 2;
	Handler handler;
	String responseText;

	public LoadFriendsListTask(int userid, Handler handler) {
		this.userid = userid;
		this.handler = handler;
	}

	public void run() {
		// 远程数据地址
		String url = "http://10.0.2.2:8080/Entertainment/FriendsListServlet?userid="
				+ userid;
		System.out.println(url);
		// 设置超时值
		HttpParams httpparas = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpparas, 3000);
		HttpConnectionParams.setSoTimeout(httpparas, 5000);
		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 建立消息
		Message message = this.handler.obtainMessage();

		// 发出post请求
		try {
			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("fromUserid", userid + ""));
			// params.add(new BasicNameValuePair("toUserid", toUserid+""));

			// 设置请求参数
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// 发出post请求
			HttpResponse response = client.execute(request);

			// 接收返回数据
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				responseText = EntityUtils.toString(response.getEntity());

				message.arg1 = 0;
				message.obj = responseText;
				System.out.println(responseText);
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
