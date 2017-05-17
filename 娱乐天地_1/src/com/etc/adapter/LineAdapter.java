package com.etc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etc.entertainment.R;
import com.etc.entity.Entertainment;
import com.etc.task.ImageLoadTask;

public class LineAdapter extends BaseAdapter {

	//������ض���
	Context context;
	List<Entertainment> datalist;
	LayoutInflater layout;
	
	String url="http://10.0.2.2:8080/Entertainment/image/photo/";
	
	public LineAdapter(Context context,List<Entertainment> datalist)
	{
		this.context=context;
		this.datalist=datalist;
		this.layout=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {

		return datalist.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return datalist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view =arg1;
		
		if(view==null)
		{
			view =layout.inflate(R.layout.line, null);
		}
		//��ȡ���
		ImageView imgEntertainment=(ImageView) view.findViewById(R.id.imgEntertainment);
		TextView txtSummary=(TextView) view.findViewById(R.id.txtSummary);
		
		Entertainment entertainment=datalist.get(arg0);
		txtSummary.setText(entertainment.getIteminfor());
		
		String imgurl=url+entertainment.getItemimage();
		
		new ImageLoadTask(imgEntertainment).execute(imgurl);
		
		return view;
	}

}
