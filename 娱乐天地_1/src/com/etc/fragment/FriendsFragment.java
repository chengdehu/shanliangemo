package com.etc.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etc.adapter.FriendsListAdapter;
import com.etc.app.MyApp;
import com.etc.entertainment.ChatFriendsActivity;
import com.etc.entertainment.ChatRecordActivity;
import com.etc.entertainment.R;
import com.etc.entity.User;
import com.etc.task.AsynBitmapTask_w;
import com.etc.task.LoadFriendsListTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FriendsFragment extends Fragment {
	
	List<User> data;
	Handler handler;
	ListView lvwFriends;
	AsynBitmapTask_w asyntask;
	FriendsListAdapter adapter;
	ProgressBar pbFriendsLoad;
	ChatFriendsActivity activity;
	int fromUserid;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.activity_friends_list, container, false);
        
        activity  = (ChatFriendsActivity) getActivity();
        
        MyApp myApp=(MyApp)activity.getApplicationContext();
        fromUserid=myApp.getUser().getUserid();
        
        lvwFriends = (ListView)view.findViewById(R.id.lvwFriendsList);
        pbFriendsLoad = (ProgressBar) view.findViewById(R.id.pbFriendsLoad);

		this.data = new ArrayList<User>();
		this.asyntask = new AsynBitmapTask_w();
		this.adapter = new FriendsListAdapter(activity, data,asyntask);
		this.lvwFriends.setAdapter(adapter);
		
		lvwFriends.setOnItemClickListener(new OnItemClickListenerImpl());

		this.handler = new Handler(new LoadFriendsListCallback());

		new LoadFriendsListTask(fromUserid, handler).start();
		return view;
    }
    
    
 	class LoadFriendsListCallback implements Callback {

 		@Override
 		public boolean handleMessage(Message msg) {
 			pbFriendsLoad.setVisibility(View.GONE);
 			if (msg.arg1 == 0) {
 				System.out.println(msg.obj.toString());
 				parseGSONString(msg.obj.toString());
 			} else {
 				ChatFriendsActivity activity  = (ChatFriendsActivity) getActivity();
 				Toast.makeText(activity, "Êý¾ÝÇëÇóÊ§°Ü",
 						Toast.LENGTH_LONG).show();
 			}
 			return false;
 		}

 		private void parseGSONString(String gstr) {
 			Gson gson = new Gson();

 			Type type = new TypeToken<List<User>>() {
 			}.getType();
 			List<User> userList = gson.fromJson(gstr, type);
 			for (User user : userList) {
 				data.add(user);
 			}
 			
 			lvwFriends.setVisibility(View.VISIBLE);
 			adapter.notifyDataSetChanged();

 		}
 	}


 	public class OnItemClickListenerImpl implements
	AdapterView.OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	
		User user = data.get(position);
	
		int userid = user.getUserid();
		String photo = user.getPhoto();
		String username = user.getUsername();
	
		System.out.println("userid="+userid);
		System.out.println("photo="+photo);
		System.out.println("username="+username);
		
		Intent intent = new Intent(activity,ChatRecordActivity.class);
		intent.putExtra("toUserid", userid + "");
		intent.putExtra("photo", photo);
		intent.putExtra("username", username);
		startActivity(intent);
	}
}
}