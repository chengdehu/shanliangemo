package com.etc.entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CollectActivity extends Activity {

private ListView lvwMyCollection;
	
	private int[] starImages = {R.drawable.star1, R.drawable.star2, R.drawable.star3, R.drawable.star4, 
			  R.drawable.star5, R.drawable.star6, R.drawable.star7, R.drawable.star8,
			  R.drawable.star9, R.drawable.star10, R.drawable.star11, R.drawable.star12};

	private String[] starNames = {"xxxx.avi", "yyy.avi"};

	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collect);
		
		lvwMyCollection = (ListView) findViewById(R.id.lvwMyCollection);
		
		//锟斤拷锟斤拷锟斤拷锟叫憋拷
		fillDataList();
		
		//锟斤拷锟斤拷1---锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		
		//from锟斤拷锟斤拷---Map锟叫碉拷key锟斤拷值
		//int锟斤拷锟斤拷	---小锟斤拷锟斤拷锟叫碉拷锟斤拷锟斤拷锟絠d
		SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.item_star,
												new String[]{"starImage", "starName"},
												new int[]{R.id.imgStar, R.id.txtStar});
		
		//锟斤拷锟斤拷2---锟斤拷锟斤拷锟斤拷锟斤拷
		lvwMyCollection.setAdapter(adapter);
		
		//锟斤拷锟铰硷拷锟斤拷锟斤拷锟斤拷
		lvwMyCollection.setOnItemClickListener(new OnItemClickListenerImpl());
		
	}

	//锟斤拷锟斤拷锟斤拷锟叫憋拷
	private void fillDataList(){
		
		for(int i=0; i<starNames.length; i++){
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("starImage", starImages[i]);
			map.put("starName", starNames[i]);
			
			dataList.add(map);
		}
	}

	//锟斤拷锟斤拷锟铰硷拷锟斤拷锟斤拷锟斤拷
	private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
			
			String starName = (String) map.get("starName");
			
			Toast.makeText(getApplicationContext(), starName, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collect, menu);
		return true;
	}
	
public void back2(View v){
		
		Intent intent = new Intent(CollectActivity.this, PersonInfoActivity.class);
		
		startActivityForResult(intent, 1);
		
	}

}
