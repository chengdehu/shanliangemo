package com.etc.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.etc.asynctask.ImageLoadTask;
import com.etc.entertainment.IndexActivity;
import com.etc.entertainment.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleAdapter extends BaseAdapter {

	public int count = 5;
	private ImageView photoView;
	private TextView nameView,contentView,dateView;
	private String path = "http://10.0.2.2:8080/Entertainment/image/photo/";
	private LayoutInflater layoutInflater;   
	private Context context;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	
	public ArticleAdapter(Context context,
			List<Map<String, Object>> dataList) {
		super();
		this.layoutInflater = LayoutInflater.from(context);
		this.context = context;
		this.dataList = dataList;
	}
	

	public void setCount(int count){
		this.count = count;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int a = position;
		view = layoutInflater.inflate(R.layout.layout_article, null);	
			
		Map<String, Object> map = dataList.get(position);
		photoView = (ImageView) view.findViewById(R.id.photoIndex);
		nameView = (TextView) view.findViewById(R.id.nameView);
		contentView = (TextView) view.findViewById(R.id.contentView);
		dateView = (TextView) view.findViewById(R.id.dateView);
		new ImageLoadTask(photoView).execute(path + dataList.get(position).get("photo"));
		nameView.setText((String)dataList.get(position).get("name"));
		contentView.setText((String)dataList.get(position).get("content"));
		dateView.setText((String)dataList.get(position).get("date"));
		
		photoView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, IndexActivity.class);
				intent.putExtra("userid", (String)dataList.get(a).get("userid"));
				context.startActivity(intent);
			}
		});
		if(position < count)
			view.setVisibility(View.VISIBLE);
		if(position >= count)
			view.setVisibility(View.GONE);
		return view;
	}

}
