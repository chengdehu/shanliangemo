package com.etc.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.etc.adapter.ChatListAdapter;
import com.etc.app.MyApp;
import com.etc.entertainment.ChatFriendsActivity;
import com.etc.entertainment.ChatRecordActivity;
import com.etc.entertainment.R;
import com.etc.entity.ChatMessage;
import com.etc.task.AsynBitmapTask_w;
import com.etc.task.LoadChatListTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MessageFragment extends Fragment {
	
	private int delete_position; // 鍒犻櫎鑱婂ぉ璁板綍
	private Dialog dlgDelete;
	List<ChatMessage> data;
	Handler handler;
	ListView lvwChat;
	ProgressBar pbLoad;
	AsynBitmapTask_w asyntask;
	ChatListAdapter adapter;
	ChatFriendsActivity activity;
	int fromUserid;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat_list, container, false);
        
        activity  = (ChatFriendsActivity) getActivity();
        
        lvwChat = (ListView) view.findViewById(R.id.lvChatList);
		pbLoad = (ProgressBar) view.findViewById(R.id.pbLoad);
		
	    MyApp myApp=(MyApp)activity.getApplicationContext();
	    fromUserid=myApp.getUser().getUserid();
	    
		this.data = new ArrayList<ChatMessage>();
		this.asyntask = new AsynBitmapTask_w();
		this.adapter = new ChatListAdapter(activity, data, asyntask);
		this.lvwChat.setAdapter(adapter);

		this.handler = new Handler(new LoadChatListCallback());

		new LoadChatListTask(fromUserid, handler).start();

		lvwChat.setOnItemClickListener(new OnItemClickListenerImpl());
		lvwChat.setOnItemLongClickListener(new OnItemLongClickListenerImpl());

		createDialog();
        
		return view;
    }
    

	private void createDialog() {
		Builder builder = new Builder(activity);

		builder.setTitle("删除确认框");
		builder.setMessage("操作只会使该聊天不在列表中显示，聊天记录不会被删除，是否继续操作");
		builder.setIcon(R.drawable.question);

		builder.setPositiveButton("是", new DeleteOnClickListenerImpl());
		builder.setNegativeButton("否", null);

		this.dlgDelete = builder.create();
	}

	private class DeleteOnClickListenerImpl implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int id) {

			if (id == Dialog.BUTTON_POSITIVE) {
				if (dialog == dlgDelete) {
					data.remove(delete_position);
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

	// 娑堟伅鍥炶皟
	class LoadChatListCallback implements Callback {

		@Override
		public boolean handleMessage(Message msg) {
			pbLoad.setVisibility(View.GONE);
			if (msg.arg1 == 0) {
				parseGSONString(msg.obj.toString());
			} else {
				Toast.makeText(activity, "数据请求失败",Toast.LENGTH_LONG).show();
			}
			return false;
		}
	}

	// 瑙ｆ瀽json瀛楃涓�
	private void parseGSONString(String gstr) {
		Gson gson = new Gson();

		Type type = new TypeToken<List<ChatMessage>>() {
		}.getType();
		List<ChatMessage> chatMessage = gson.fromJson(gstr, type);
		for (ChatMessage chat : chatMessage) {
			data.add(chat);
		}
		lvwChat.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
	}

	public class OnItemClickListenerImpl implements
			AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			ChatMessage chatMessage = data.get(position);

			int toUserid = chatMessage.getUser().getUserid();
			String photo = chatMessage.getUser().getPhoto();
			String username = chatMessage.getUser().getUsername();

			Intent intent = new Intent(activity,ChatRecordActivity.class);
			intent.putExtra("toUserid", toUserid + "");
			intent.putExtra("photo", photo);
			intent.putExtra("username", username);
			startActivity(intent);
			activity.finish();
		}
	}

	// 闀挎寜鍒犻櫎鑱婂ぉ璁板綍
	public class OnItemLongClickListenerImpl implements AdapterView.OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {

			delete_position = position;

			dlgDelete.show();

			return false;
		}

	}
}