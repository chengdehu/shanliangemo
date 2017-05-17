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


import com.etc.entity.User;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;


import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public class LoginTask extends AsyncTask<String, Void, Void> {

	ProgressDialog loginprogress;
	Context context;
	String url="http://10.0.2.2:8080/Entertainment/LoginServlet";
	Handler handler;
	public LoginTask(Context context,ProgressDialog loginprogress,Handler handler)
	{
		this.context=context;
		this.loginprogress=loginprogress;
		this.handler = handler;
    }
	
	@Override
	protected void onPreExecute() {
		loginprogress.setMessage("���ڵ�¼��...");
		loginprogress.show();
		super.onPreExecute();
	}
	@Override
	protected Void doInBackground(String... arg0) {
//(1.1)ʵ����HttpClient����

	    HttpClient client = new DefaultHttpClient();

// (1.2)ʵ����HttpPost������󣬴�����ʵ�url
		
	    HttpPost request = new HttpPost(url);
	  
// (1.3)ʹ��NameValuePair�����װPost�����������-ֵ�ԣ���������������ı����ʽΪUTF_8
					
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", arg0[0]));
		params.add(new BasicNameValuePair("password", arg0[1]));

		try {
			    request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
//(1.4)�������󣬲�����HttpResponse����
			    HttpResponse response = client.execute(request);
//(1.5)��ȡHttpResponse�����еķ�����Ϣ����ͨ��Handler���������̷߳�����Ϣ���Ա����UI
			    response = client.execute(request);
		        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			
			        String responseText = EntityUtils.toString(response.getEntity());
			        Gson gson=new Gson();
			        User user=gson.fromJson(responseText, User.class);
			    	//��ȡȫ����
			    	MyApp myApp = (MyApp) context;        //�൱���õ�session����
			    	//����ȫ�ֱ�����ֵ
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
		//��ȡȫ����
		MyApp myApp = (MyApp)context;      //�൱���õ�session����
		//��ȡȫ�ֱ�����ֵ
		User user=myApp.getUser();   
		loginprogress.dismiss();
		if(user==null)
		{//�Դ��ڵ�¼����
			Toast.makeText(context, "�û������������", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessage(-1);
		}else
		{    //��ת����ҳ��
			Toast.makeText(context, "��¼�ɹ�", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessage(1);
			
		}
		super.onPostExecute(result);
	}

}
