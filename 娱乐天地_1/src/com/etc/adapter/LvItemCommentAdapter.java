package com.etc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etc.entertainment.R;
import com.etc.entity.ItemComment;

public class LvItemCommentAdapter extends BaseAdapter {
	Context context;
	List<ItemComment> data;
	LayoutInflater layout;

	public LvItemCommentAdapter(Context context,List<ItemComment> data){
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
		//鑾峰彇椤圭洰甯冨眬
		View view = arg1;
		if(view==null)
			view = layout.inflate(R.layout.listview_lvitemcomment, null);
		//鑾峰彇甯冨眬涓殑鍚勭粍浠�
		//TextView commentid = (TextView) view.findViewById(R.id.lvComComment_id);
		//TextView userid = (TextView) view.findViewById(R.id.lvComUser_id);
		//TextView itemid = (TextView) view.findViewById(R.id.lvComItem_id);
		TextView cont_info = (TextView) view.findViewById(R.id.lvComCont_info);
		TextView commentcont = (TextView) view.findViewById(R.id.lvComComment_cont);

		//濉厖鏁版嵁
		ItemComment itemcomment = data.get(arg0);
		
		//System.out.println(itemcomment);
		//System.out.println(itemcomment.getComment_id());
		//commentid.setText(itemcomment.getComment_id()+"");
		//userid.setText(itemcomment.getUser_id()+"");
		//itemid.setText(itemcomment.getItem_id()+"");
		cont_info.setText(itemcomment.getCont_info()+"");
		commentcont.setText(itemcomment.getComment_cont()+"");
		
		
		return view;
	}

}
