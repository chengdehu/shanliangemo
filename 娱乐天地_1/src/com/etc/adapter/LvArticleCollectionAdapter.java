package com.etc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etc.entertainment.R;
import com.etc.entity.ArticleCollection;

public class LvArticleCollectionAdapter extends BaseAdapter {

	//声明
	Context context;
	List<ArticleCollection> data;
	LayoutInflater layout;
	
	public LvArticleCollectionAdapter(Context context,List<ArticleCollection> data){
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
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		//获取项目布局
		View view = arg1;
		if(view==null)
			view = layout.inflate(R.layout.listview_lvatriclecollection, null);
		//获取布局中的各组�?
		TextView aname = (TextView) view.findViewById(R.id.lvCollAname);
		TextView description = (TextView) view.findViewById(R.id.lvCollDsecription);
		
		//填充数据
		ArticleCollection articlecollection = data.get(arg0);
		aname.setText(articlecollection.getAname()+"");
		description.setText(articlecollection.getDescription()+"");
		
		return view;
	}

}
