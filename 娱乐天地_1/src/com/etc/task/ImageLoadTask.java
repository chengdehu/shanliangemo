package com.etc.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

	ImageView img;
	public ImageLoadTask(ImageView img)
	{
		this.img=img;
	}
	@Override
	protected Bitmap doInBackground(String... arg0) {
		
		try {
			URL url=new URL(arg0[0]);
			System.out.println("hello,imagetest"+arg0[0]);
			InputStream is=url.openStream();
			
			Bitmap bitmap=BitmapFactory.decodeStream(is);
			
			is.close();
			return bitmap;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		
		super.onPostExecute(result);
		
		img.setImageBitmap(result);
	}
	

}
