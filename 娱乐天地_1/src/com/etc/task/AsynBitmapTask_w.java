package com.etc.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsynBitmapTask_w {

	// 鍐呭瓨缂撳瓨
	private HashMap<String, SoftReference<Bitmap>> imageCache;
	// SD鍗＄紦瀛�
	private String sdcardCacheDir;

	// 鏋勯�鏂规硶
	public AsynBitmapTask_w() {
		this.imageCache = new HashMap<String, SoftReference<Bitmap>>();
		this.sdcardCacheDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/Entertainment/images";
	}

	// 鍔犺浇鍥剧墖鏂规硶
	public Bitmap loadBitmap(final ImageView imageview, final String imageurl,
			final ImageCallback imagecallback) {
		// 妫�祴鍐呭瓨缂撳瓨
		if (imageCache.containsKey(imageurl)) {
			SoftReference<Bitmap> reference = imageCache.get(imageurl);
			Bitmap bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}

		// 妫�祴SD鍗�
		String bitmapName = imageurl.substring(imageurl.lastIndexOf("/") + 1);
		File cacheDir = new File(sdcardCacheDir);
		if (cacheDir.exists()) {
			// 鑾峰彇缂撳瓨涓殑鎵�湁鏂囦欢
			File[] cacheFiles = cacheDir.listFiles();
			int i = 0;
			for (; i < cacheFiles.length; i++) {
				if (bitmapName.equals(cacheFiles[i].getName()))
					break;
			}
			if (i < cacheFiles.length) {
				Bitmap bitmap = BitmapFactory.decodeFile(sdcardCacheDir + "/"
						+ bitmapName);
				imageCache.put(imageurl, new SoftReference<Bitmap>(bitmap));
				return bitmap;
			}
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 璋冪敤鍥炶皟鍑芥暟
				imagecallback.Imageload(imageview, (Bitmap) msg.obj);
			}
		};
		// 杩滅▼鍔犺浇
		new Thread() {
			public void run() {
				try {
					URL url = new URL(imageurl);
					InputStream input = url.openStream();
					Bitmap bitmap = BitmapFactory.decodeStream(input);
					input.close();

					// 閫氱煡Handler
					Message msg = handler.obtainMessage();
					msg.obj = bitmap;
					handler.handleMessage(msg);

					// 鍔犲叆鍐呭瓨缂撳瓨
					imageCache.put(imageurl, new SoftReference<Bitmap>(bitmap));

					// 缂撳瓨鍒皊d鍗�
					File dir = new File(sdcardCacheDir);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File bitmatFile = new File(sdcardCacheDir + "/"
							+ imageurl.substring(imageurl.lastIndexOf("/") + 1));
					FileOutputStream fos = new FileOutputStream(bitmatFile);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					fos.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};

		}.start();
		return null;
	}

	// 瀹氫箟鍥炶皟鎺ュ彛
	public interface ImageCallback {
		public void Imageload(ImageView imageview, Bitmap bitmap);
	}
}
