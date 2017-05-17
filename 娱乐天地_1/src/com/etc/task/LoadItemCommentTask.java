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

public class LoadItemCommentTask extends Thread {
	int lastid,pagesize;
	Handler handler;
	public LoadItemCommentTask(Handler handler){
		this.handler = handler;
		
	}
	
	@Override
	public void run() {
		// 杩滅▼鏁版嵁鐨勫湴鍧�
		String uri = "http://10.0.2.2:8080/Entertainment/EntertainmentServlet?lastid="+ lastid+"&pagesize=" + pagesize;
		// 璁惧畾瓒呮椂
		HttpParams httpparas = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpparas, 3000);
		HttpConnectionParams.setSoTimeout(httpparas, 5000);
		//寤虹珛杩炴帴
		HttpClient client = new DefaultHttpClient(httpparas);
		//鑾峰彇璇锋眰瀵硅薄
		HttpGet request = new HttpGet(uri);
		//寤虹珛娑堟伅
		Message message = this.handler.obtainMessage();
		try {
			//鎵ц杩炴帴
			HttpResponse response = client.execute(request);
			//鍒ゆ柇澶勭悊缁撴灉
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

