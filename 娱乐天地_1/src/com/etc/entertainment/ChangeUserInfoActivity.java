package com.etc.entertainment;
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.etc.app.MyApp;
import com.etc.entity.User;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeUserInfoActivity extends Activity {
	
	//澹版槑鎺т欢
	private EditText edtUsername;
	private EditText edtPassword;
	private EditText edtPassword2;
	private EditText edtUserid;	
	private String url = "http://10.0.2.2:8080/Entertainment/ChangeUserInfoServlet";
	private Handler handler;
	private String responseText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_user_info);
		//鑾峰彇缁勪欢
	edtUsername = (EditText) findViewById(R.id.edtUsername);
	edtPassword = (EditText) findViewById(R.id.edtPassword);
	edtPassword2= (EditText) findViewById(R.id.edtPassword2);
	edtUserid = (EditText) findViewById(R.id.edtUserid);
	
	handler = new Handler() {
	
		public void handleMessage(Message msg) {
			
			super.handleMessage(msg);

			
			if (msg.what == 1) {    
				
				//瑙ｆ瀽鏁版嵁
				Gson gson = new Gson();
				User user = gson.fromJson(responseText, User.class);
				
				if(user!=null){   //淇敼鎴愬姛					
					//灏唘ser瀵硅薄淇濆瓨鍒板叏灞�彉閲忎腑
					MyApp myApp = (MyApp)getApplication();
					myApp.setUser(user);									
				}				
			}else {
				Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
			}
		}
	};
	}
	
	//鐢ㄦ埛淇℃伅淇敼
	public void ok(View v) {		
		//鍚姩缃戠粶璁块棶绾跨▼
		new Thread(new LoginRunner()).start();
		Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
		//璺宠浆鍒颁富鐣岄潰
		Intent intent = new Intent(ChangeUserInfoActivity.this, MainActivity.class);
		startActivity(intent);
		
		//鍏抽棴褰撳墠椤甸潰
		finish();
	}
	
	
	private class LoginRunner implements Runnable {
		@Override
		public void run() {

			//瀹炰緥鍖朒ttpClient瀵硅薄
			HttpClient client = new DefaultHttpClient();

			//瀹炰緥鍖杙ost璇锋眰瀵硅薄
			HttpPost request = new HttpPost(url);

			//鍙戝嚭post璇锋眰
			try {

				//灏佽璇锋眰鍙傛暟
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("username", edtUsername.getText().toString()));
				params.add(new BasicNameValuePair("password", edtPassword.getText().toString()));
				params.add(new BasicNameValuePair("password2", edtPassword2.getText().toString()));
				params.add(new BasicNameValuePair("userid", edtUserid.getText().toString()));
				//璁剧疆璇锋眰鍙傛暟
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

				//鍙戝嚭post璇锋眰
				HttpResponse response = client.execute(request);

				//鎺ユ敹杩斿洖鏁版嵁
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					responseText = EntityUtils.toString(response.getEntity());
						
					//鍚戜富绾跨▼鍙戦�娑堟伅
					handler.sendEmptyMessage(1);
				}

			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
				handler.sendEmptyMessage(-1);
			} catch (IOException e) {
				
				e.printStackTrace();
				handler.sendEmptyMessage(-1);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_user_info, menu);
		return true;
	}
	
	/*public void ok(View v){
	String username = edtUsername.getText().toString();
	String password = edtUsername.getText().toString();
	String password2 = edtUsername.getText().toString();
	String userid = edtUsername.getText().toString();
			
	}
	*/
	public void back4(View v){
		
		
		Intent intent = new Intent(ChangeUserInfoActivity.this, MainActivity.class);
		
		startActivityForResult(intent, 1);
		
	}

}