package com.etc.adapter;

import java.util.ArrayList;
import java.util.List;
import com.etc.entity.Enterlabel;
import com.etc.entertainment.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class Labeladapter extends BaseAdapter {
	Context context;
	LayoutInflater layout;
	List<Enterlabel> data;
	List<String> lname = new ArrayList<String>();
	
	public Labeladapter(Context context,List<Enterlabel> data){
		this.context = context;
		this.data = data;
		this.layout = LayoutInflater.from(context);	
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
			view = layout.inflate(R.layout.label1, null);
		CheckBox chblabel = (CheckBox) view.findViewById(R.id.chblabel);
		TextView txtlabelname = (TextView) view.findViewById(R.id.txtlabelname);
		
		Enterlabel enterlabel = data.get(arg0);
		txtlabelname.setText(enterlabel.getLabel_name());
		chblabel.setTag(arg0);
		chblabel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				int p = (Integer) arg0.getTag();
				Enterlabel  label=data.get(p);
				String labelname = label.getLabel_name();// TODO Auto-generated method stub
				//System.out.println(labelname);
				lname.add(labelname);
				
			}
		});
		// TODO Auto-generated method stub
		return view;
	}
	
	public List<String> getlabel(){
		return lname;
		
	}
}
