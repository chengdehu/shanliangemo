package com.etc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etc.entertainment.R;
import com.etc.entity.MyArticleCollection;

public class LvMyArticleCollectionAdapter extends BaseAdapter {

	//声明
	Context context;
	List<MyArticleCollection> data;
	LayoutInflater layout;
	
	public LvMyArticleCollectionAdapter(Context context,List<MyArticleCollection> data){
		this.context = context;
		this.data = data;
		this.layout = LayoutInflater.from(context);
		System.out.println(data);
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
			view = layout.inflate(R.layout.listview_lvmyatriclecollection, null);
		//获取布局中的各组�?
		//TextView colle_id = (TextView) view.findViewById(R.id.lvColle_id);
		//TextView articleid = (TextView) view.findViewById(R.id.lvArticleid);
		TextView articletitle = (TextView) view.findViewById(R.id.lvArticletitle);
		TextView colle_time = (TextView) view.findViewById(R.id.lvColle_time);
		//TextView userid = (TextView) view.findViewById(R.id.lvUserid);
		
		//填充数据
		MyArticleCollection myarticlecollection = data.get(arg0);
		//aname.setText(articlecollection.getAname()+"");
		//description.setText(articlecollection.getDescription()+"");
		//colle_id.setText(myarticlecollection.getColle_id()+"");
		//articleid.setText(myarticlecollection.getArticleid()+"");
		articletitle.setText(myarticlecollection.getArticletitle()+"");
		colle_time.setText(myarticlecollection.getColle_time()+"");
		//userid.setText(myarticlecollection.getUserid()+"");
		return view;
	}

}
