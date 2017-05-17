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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private ImageView Cmterphoto;
	private TextView Cmtername,Cmtcont;
	private String path = "http://10.0.2.2:8080/Entertainment/image/photo/";
	private LayoutInflater layoutInflater;   
	private Context context;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	
	public CommentAdapter(Context context,
			List<Map<String, Object>> dataList) {
		super();
		this.layoutInflater = LayoutInflater.from(context);
		this.context = context;
		this.dataList = dataList;
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
		view = layoutInflater.inflate(R.layout.layout_comment, null);	
			
		Map<String, Object> map = dataList.get(position);
		Cmterphoto = (ImageView) view.findViewById(R.id.Cmterphoto);
		Cmtername = (TextView) view.findViewById(R.id.Cmtername);
		Cmtcont = (TextView) view.findViewById(R.id.Cmtcont);
		new ImageLoadTask(Cmterphoto).execute(path + dataList.get(position).get("photo"));
		Cmtername.setText((String)dataList.get(position).get("name"));
		Cmtcont.setText((String)dataList.get(position).get("content"));
		
		Cmterphoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, IndexActivity.class);
				intent.putExtra("userid", "" + dataList.get(a).get("userid"));
				context.startActivity(intent);
			}
		});
		return view;
	}

}
