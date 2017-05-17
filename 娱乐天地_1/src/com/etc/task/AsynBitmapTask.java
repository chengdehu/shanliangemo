package com.etc.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsynBitmapTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView imgView;
	//构造方法中传入图片显示组件
	public AsynBitmapTask(ImageView imgView){
		this.imgView = imgView;
	}
	
	//异步任务的主体执行代码，该方法封装了Thread的run()方法，其中参数arg0对应第1个泛型参数，表示异步任务操作所需要的URL
	@Override
	protected Bitmap doInBackground(String... arg0) {

		String urlString = arg0[0];

		try {
			System.out.println("1");
			URL url = new URL(urlString);
			InputStream is = url.openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();

			return bitmap;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	//异步任务代码执行完，后续的更新UI操作，该方法封装了Handler的handleMessage()方法，其中参数result对应第3个泛型参数，表示执行异步任务得到的结果
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		
		if(result != null){
			imgView.setImageBitmap(result);
		}
	}

}
