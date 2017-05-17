package com.etc.entertainment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.etc.adapter.Labeladapter;
import com.etc.entity.Enterlabel;
import com.etc.task.LoadLabelTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SearchActivity extends Activity {
	
	private EditText edtwords;
	private String words;
	private String itemtype;
	private GridView grvlabel;
	private Handler handler;
	private List<Enterlabel> data;
	private Labeladapter adapter;
	private RadioGroup radioGroup1,radioGroup2,radioGroup3;
	private boolean changeedGroup = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_search);
		edtwords= (EditText) findViewById(R.id.edtwords);
		grvlabel =(GridView) findViewById(R.id.grvlabel); 
		this.radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup3);
		this.radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup4);
		this.radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup5);
		radioGroup1.clearCheck();
		radioGroup2.clearCheck();
		radioGroup3.clearCheck();
		this.data = new ArrayList<Enterlabel>();
		this.adapter = new Labeladapter(getApplicationContext(), data);
		this.handler = new Handler(new LoadTypeCallback());
	   	grvlabel.setAdapter(adapter);
	   	
	   	 radioGroup1.setOnCheckedChangeListener(new MyOnCheckedChangeListener()); 
	   	radioGroup2.setOnCheckedChangeListener(new MyOnCheckedChangeListener()); 
	   	radioGroup3.setOnCheckedChangeListener(new MyOnCheckedChangeListener()); 
		
		
	}
	
	class MyOnCheckedChangeListener implements OnCheckedChangeListener {
		public void onCheckedChanged(RadioGroup arg0, int arg1) {

			  if (!changeedGroup) {  
	                 changeedGroup = true;  
	                 if (arg0 == radioGroup1) {  
	                     radioGroup2.clearCheck();  
	                     radioGroup3.clearCheck();  
	                     switch (radioGroup1.getCheckedRadioButtonId()){
	                     case R.id.radio0:
	                    	 itemtype = "gam";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
	                 		new LoadLabelTask(itemtype, handler).start();	                 		
	                 		break;
	                     case R.id.radio1:
	                    	 itemtype = "foo";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
		                 	  new LoadLabelTask(itemtype, handler).start();
		                 	  break;
	                     case R.id.radio2:
	                    	 itemtype = "mus";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
		                 	 new LoadLabelTask(itemtype, handler).start();
		                 	 break;
		                 default:
		                 	 break;		             	                    	 
	                     }
	                 } else if (arg0 == radioGroup2) {  
	                     radioGroup1.clearCheck();  
	                     radioGroup3.clearCheck();  
	                     switch (radioGroup2.getCheckedRadioButtonId()){
	                     case R.id.radio0:
	                    	 itemtype = "mov";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
	                 		new LoadLabelTask(itemtype, handler).start();
	                 		break;
	                     case R.id.radio1:
	                    	 itemtype = "spo";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
		                 	  new LoadLabelTask(itemtype, handler).start();
		                 	  break;
	                     case R.id.radio2:
	                    	 itemtype = "sta";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
		                 	 new LoadLabelTask(itemtype, handler).start();
		                 	 break;
		                 default:
		                 	 break;		             	                    	 
	                     }
	                 } else if (arg0 == radioGroup3) {  
	                     radioGroup1.clearCheck();  
	                     radioGroup2.clearCheck();  
	                     switch (radioGroup3.getCheckedRadioButtonId()){
	                     case R.id.radio0:
	                    	 itemtype = "ani";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
	                 		new LoadLabelTask(itemtype, handler).start();
	                 		break;
	                     case R.id.radio1:
	                    	 itemtype = "nov";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
		                 	  new LoadLabelTask(itemtype, handler).start();
		                 	  break;
	                     case R.id.radio2:
	                    	 itemtype = "vid";
	                    	 data.clear();
	                    	 adapter.notifyDataSetChanged();
		                 	 new LoadLabelTask(itemtype, handler).start();
		                 	 break;
		                 default:
		                 	 break;		             	                    	 
	                     }
	                 }  
	                 changeedGroup = false;  // TODO Auto-generated method stub
			}
			  
			  
		}
			
	}
	
	public void search(View v){
		words = edtwords.getText().toString();
		//创建Intent
		Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);

		//携带参数	
		intent.putExtra("words",words);

		//启动Activity
		startActivity(intent);
	}
	
	class LoadTypeCallback implements Callback{

		@Override
		public boolean handleMessage(Message msg) {
			if(msg.arg1 == 0){
				praseGSONString(msg.obj.toString());// TODO Auto-generated method stub
			}else{
				Toast.makeText(getApplicationContext(), "网络请求失败",Toast.LENGTH_SHORT).show();
			}
			return false;// TODO Auto-generated method stub
		}
		
	}
	
	private void praseGSONString(String gstr){
   	 Gson gson = new Gson();
   	 Type type = new TypeToken<List<Enterlabel>>(){}.getType();
   	 List<Enterlabel> ente = gson.fromJson(gstr,type);
   	 for(Enterlabel ent:ente){
   		 data.add(ent);
   	 }
   	 grvlabel.setVisibility(View.VISIBLE);
   	 adapter.notifyDataSetChanged();
   	 }
		

	

	
	public void labelsearch(View v){
		if("".equals(itemtype)){
			Toast.makeText(getApplicationContext(), "尚未选择",Toast.LENGTH_SHORT).show();
		}else{
		List<String> list = adapter.getlabel();
		//创建Intent

		Intent intent = new Intent(SearchActivity.this, SearchbylabelActivity.class);

		//携带参数	
		intent.putExtra("itemtype", itemtype); 
		intent.putStringArrayListExtra("list", (ArrayList<String>) list);

		//启动Activity
		startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
