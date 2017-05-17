package com.etc.task;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

public class LoadMyArticleCollectionTask extends Thread {
	int lastid,pagesize;
	Handler handler;
	public LoadMyArticleCollectionTask(Handler handler) {

		this.handler = handler;
	}
	
	@Override
	public void run() {
		//远程数据地址
		String uri ="http://10.0.2.2:8080/Entertainment/MyArticleCollectionServlet?lastid=" + lastid+"&pagesize="+ pagesize;
		// 设定超时
		HttpParams httpparas = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpparas, 3000);
		HttpConnectionParams.setSoTimeout(httpparas, 5000);
		//建立连接
		HttpClient client = new DefaultHttpClient(httpparas);
		//获取请求对象
		HttpGet request = new HttpGet(uri);
		//建立消息
		Message message = this.handler.obtainMessage();
		try {
			//执行连接
			HttpResponse response = client.execute(request);
			//判断处理结果
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					String gstr = EntityUtils.toString(response.getEntity());
					message.arg1 = 0;
					message.obj = gstr;
			}else{
				message.arg1 = 1;
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
