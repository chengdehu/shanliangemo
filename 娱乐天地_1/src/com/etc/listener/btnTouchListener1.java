package com.etc.listener;

import com.etc.entertainment.R;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class btnTouchListener1 implements OnTouchListener {

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		arg0.setBackgroundResource(R.drawable.btnbg2);
		new Thread(new regain(arg0)).start();
		return false;
	}

	public class regain implements Runnable
	{

		View arg0;
		public regain(View arg0)
		{
			this.arg0=arg0;
		}
		@Override
		public void run() {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			arg0.setBackgroundResource(R.drawable.btnbg3);
		}
		
		
	}
}
