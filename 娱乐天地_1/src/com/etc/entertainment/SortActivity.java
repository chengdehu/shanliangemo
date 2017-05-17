package com.etc.entertainment;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.etc.adapter.LineAdapter;
import com.etc.entity.Entertainment;
import com.etc.task.ImageLoadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SortActivity extends Activity {

	List<Entertainment> datalist;
	
	ListView lvwSort;
	ImageView img;
	Handler handler;
	int category;
	LineAdapter adapter;
	Context context;
	String url="http://10.0.2.2:8080/Entertainment/CategoryServlet";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_sort);
		//获取组件
		lvwSort=(ListView) findViewById(R.id.lvwSort);
		img=(ImageView) findViewById(R.id.imgTopic);
		datalist=new ArrayList<Entertainment>();
		//加载主题图片
		//http://10.0.2.2:8080/TestWeb/image/photo/4.gif
		new ImageLoadTask(img).execute("http://10.0.2.2:8080/Entertainment/image/photo/movietopic.jpg");
		
		Intent intent =this.getIntent();
		category=intent.getIntExtra("category", 0);
		adapter=new LineAdapter(this.getApplicationContext(),datalist);
		context=this.getApplicationContext();
		lvwSort.setAdapter(adapter);
		lvwSort.setOnItemClickListener(new ClickListener());
		handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.what==1)
				{
					lvwSort.setVisibility(View.VISIBLE);
					
					
					adapter=new LineAdapter(context,datalist);
					//adapter.notifyDataSetChanged();
					lvwSort.setAdapter(adapter);
				}
				
			}
		};
		new Thread(new LoadDataTask()).start();
	}

	
	
	public class LoadDataTask implements Runnable
	{

		@Override
		public void run() {
			
			//(1.1)实例化HttpClient对象
		    HttpClient client = new DefaultHttpClient();

	// (1.2)实例化HttpPost请求对象，传入访问的url
		    HttpPost request = new HttpPost(url);
		  
	// (1.3)使用NameValuePair对象封装Post请求参数的名-值对，并设置请求参数的编码格式为UTF_8
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("category",Integer.toString(category)));
			
			try {
				    request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
	//(1.4)发送请求，并返回HttpResponse对象
				    HttpResponse response = client.execute(request);
	//(1.5)获取HttpResponse对象中的返回信息，并通过Handler对象向主线程发送消息，以便更新UI
				    response = client.execute(request);
			        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				
				        String responseText = EntityUtils.toString(response.getEntity());
				        Gson gson=new Gson();
				        datalist=gson.fromJson(responseText,new TypeToken<List<Entertainment>>(){}.getType());
				        handler.sendEmptyMessage(1);
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
		}
		
	}
	public class ClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long arg3) {
			Intent intent=new Intent(SortActivity.this,MainpageActivity.class);
			
			Entertainment entertainment=datalist.get(position);
			//Gson gson=new Gson();
			//intent.putExtra("entertainment", gson.toJson(entertainment));
			Bundle bundle=new Bundle();
			bundle.putInt("itemid", entertainment.getItemid());
			bundle.putInt("likes", entertainment.getItemlikes());
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	}
	public void back(View v)
	{
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sort, menu);
		return true;
	}

}
