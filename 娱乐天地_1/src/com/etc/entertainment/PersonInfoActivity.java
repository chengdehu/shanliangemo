package com.etc.entertainment;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.etc.app.MyApp;
import com.etc.entity.Entertainment;
import com.etc.entity.User;
import com.etc.task.ImageLoadTask;

public class PersonInfoActivity extends Activity {
	
	
	List<Entertainment> data;
	//声明组件
	private TextView txtUserInfo;
	private ImageView imgUserPhoto;
	private String imageURL = "http://10.0.2.2:8080/EntertainmentNetwork/image/photo/1.png";
	private Handler handler;	
	private String responseText;
	
 
	
	//用户头像对应信息
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		setContentView(R.layout.activity_person_info);		
		
		//获取组件
		txtUserInfo = (TextView) findViewById(R.id.txtUserInfo);
		imgUserPhoto = (ImageView) findViewById(R.id.imgUserPhoto);
		MyApp myApp = (MyApp) this.getApplicationContext();  
		//new ImageLoadTask(img).execute("http://10.0.2.2:8080/Entertainment/image/photo/movietopic.jpg");
		new ImageLoadTask(imgUserPhoto).execute("http://10.0.2.2:8080/Entertainment/image/photo/"+myApp.getUser().getPhoto());
		//new Thread(new ImageLoadRunner(myApp.getUser())).start();

		/*handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				if(msg.arg1==1){
					
				//显示用户头像
					imgUserPhoto.setImageBitmap((Bitmap) msg.obj);
					
				}
			}
		};*/
		
					
	}
 
	private class ImageLoadRunner implements Runnable{
		User user;
		public ImageLoadRunner(User user){
			this.user=user;
		}
		
		@Override
		public void run() {
			try {
				Message msg = new Message();
				imageURL =  "http://10.0.2.2:8080/Entertainment/image/photo/"+user.getPhoto();
				URL url = new URL(imageURL);
				
				InputStream is = url.openStream();
			
				Bitmap bitmap = BitmapFactory.decodeStream(is);
					
				is.close();
				msg.arg1=1;	
				msg.obj=bitmap;
					handler.sendMessage(msg);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException  e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
public void collect(View v){
		
		Intent intent = new Intent(PersonInfoActivity.this, MyArticleCollectionActivity.class);
		
		startActivityForResult(intent, 1);
		
	}

public void comment(View v){
	
	Intent intent = new Intent(PersonInfoActivity.this, CommentActivity.class);
	
	startActivityForResult(intent, 1);
	
}

public void radio(View v){
	
	Intent intent = new Intent(PersonInfoActivity.this, RadioActivity.class);
	
	startActivityForResult(intent, 1);
	
}
public void change(View v){
	
	Intent intent = new Intent(PersonInfoActivity.this, ChangeUserInfoActivity.class);
	
	startActivityForResult(intent, 1);
	
}
public void recommend(View v)
{
	Intent intent=new Intent(PersonInfoActivity.this,MainActivity.class);
	startActivity(intent);
}
public void find(View v)
{
	Intent intent=new Intent(PersonInfoActivity.this,DiscoverActivity.class);
	startActivity(intent);
}

public void message(View v)
{
	Intent intent=new Intent(PersonInfoActivity.this,ChatFriendsActivity.class);
	startActivity(intent);
}
}
