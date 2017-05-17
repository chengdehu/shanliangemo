package com.etc.adapter;

import java.util.List;

import com.etc.app.MyApp;
import com.etc.entity.Entertainment;
import com.etc.task.AsynBitmapTask;
import com.etc.entertainment.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Entertainmentadapter extends BaseAdapter {
	
	Context context;
	List<Entertainment> data;
	LayoutInflater layout;
	String ImageBaseUrl = "http://10.0.2.2:8080/Entertainment/image/photo/";
	AsynBitmapTask asyntask;
	
	public Entertainmentadapter(Context context,List<Entertainment> data){
		this.context = context;
		this.data = data;
		this.layout = LayoutInflater.from(context);
		
		
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view =arg1;
		if(view ==null)
			view = layout.inflate(R.layout.array, null);
			ImageView imgPhoto = (ImageView) view.findViewById(R.id.imgimage);
			TextView txtcontent = (TextView) view.findViewById(R.id.txtcontent);
			
			Entertainment entertainment = data.get(arg0);
			txtcontent.setText(entertainment.getIteminfor());			
			//启动加载图片的异步任务
			new AsynBitmapTask(imgPhoto).execute(ImageBaseUrl + data.get(arg0).getItemimage());
		return view;
	}

}