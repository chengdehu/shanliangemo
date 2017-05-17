package com.etc.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.widget.Toast;

public class ChatDeleteTask extends AsyncTask<String, Void, Void> {

	Context context;
	String url = "http://10.0.2.2:8080/Entertainment/ChatDeleteServlet";

	public ChatDeleteTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... arg0) {

		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fromUserid", arg0[0]));
		params.add(new BasicNameValuePair("toUserid", arg0[1]));
		params.add(new BasicNameValuePair("time", arg0[2]));

		try {
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				String responseText = EntityUtils
						.toString(response.getEntity());

				if (responseText.equals("")) {
					Toast.makeText(context, "数据删除成功", Toast.LENGTH_SHORT)
							.show();
					return null;
				} 
			} 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	}

}
