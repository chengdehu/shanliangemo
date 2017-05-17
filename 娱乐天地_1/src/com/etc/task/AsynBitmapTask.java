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
	//���췽���д���ͼƬ��ʾ���
	public AsynBitmapTask(ImageView imgView){
		this.imgView = imgView;
	}
	
	//�첽���������ִ�д��룬�÷�����װ��Thread��run()���������в���arg0��Ӧ��1�����Ͳ�������ʾ�첽�����������Ҫ��URL
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
	
	//�첽�������ִ���꣬�����ĸ���UI�������÷�����װ��Handler��handleMessage()���������в���result��Ӧ��3�����Ͳ�������ʾִ���첽����õ��Ľ��
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		
		if(result != null){
			imgView.setImageBitmap(result);
		}
	}

}
