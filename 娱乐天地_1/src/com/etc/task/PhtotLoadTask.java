package com.etc.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class PhtotLoadTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView imageView;
	
	public PhtotLoadTask(ImageView imageView){
		this.imageView = imageView;
	}
	
	//任务的主体执行代�?
	@Override
	protected Bitmap doInBackground(String... arg0) {
		try {
			
			URL url = new URL(arg0[0]);
			
			InputStream is = url.openStream();
		
			Bitmap bitmap = BitmapFactory.decodeStream(is);
				
			is.close();

			return bitmap;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException  e) {
			e.printStackTrace();
		}
		
	
		return null;
	}
	//后续

	@Override
	protected void onPostExecute(Bitmap result) {
	
		super.onPostExecute(result);
		//显示用户头像
		imageView.setImageBitmap(result);
	}
}
