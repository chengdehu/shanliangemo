package com.etc.entertainment;

import java.util.List;

import com.etc.adapter.ChatListAdapter;
import com.etc.app.MyApp;
import com.etc.entity.ChatMessage;
import com.etc.fragment.FriendsFragment;
import com.etc.fragment.MessageFragment;
import com.etc.task.AsynBitmapTask;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ChatFriendsActivity extends FragmentActivity implements OnClickListener  {

	List<ChatMessage> data;
	Handler handler;
	ListView lvwChat;
	ProgressBar pbLoad;
	AsynBitmapTask asyntask;
	ChatListAdapter adapter;
	int fromUserid ;
	
	// 搴曢儴鑿滃崟2涓彍鍗曟爣棰�
    private TextView bt_message;
    private TextView bt_friends;

    // 2涓狥ragment
    private Fragment messageFragment;
    private Fragment friendsFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 鍘绘帀鏍囬鏍�
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_friends);
		
		MyApp myApp=(MyApp)getApplicationContext();
		fromUserid=myApp.getUser().getUserid();
		
		// 鍒濆鍖栨帶浠�
        initView();
        // 鍒濆鍖栧簳閮ㄦ寜閽簨浠�
        initEvent();
        // 鍒濆鍖栧苟璁剧疆褰撳墠Fragment
        initFragment(0);
	}
	
	private void initFragment(int index) {
		
		// 鐢变簬鏄紩鐢ㄤ簡V4鍖呬笅鐨凢ragment锛屾墍浠ヨ繖閲岀殑绠＄悊鍣ㄨ鐢╣etSupportFragmentManager鑾峰彇
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 寮�惎浜嬪姟
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 闅愯棌鎵�湁Fragment
        hideFragment(transaction);
        switch (index) {
        case 0:
            if (messageFragment == null) {
            	messageFragment = new MessageFragment();
                transaction.add(R.id.chat_friends_content, messageFragment);
            } else {
                transaction.show(messageFragment);
            }
            break;
        case 1:
            if (friendsFragment == null) {
            	friendsFragment = new FriendsFragment();
                transaction.add(R.id.chat_friends_content, friendsFragment);
            } else {
                transaction.show(friendsFragment);
            }
            default:
            break;
        }

        // 鎻愪氦浜嬪姟
        transaction.commit();

    }

	private void hideFragment(FragmentTransaction transaction) {
		
		if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (friendsFragment != null) {
            transaction.hide(friendsFragment);
        }
		
	}

	private void initEvent() {
		
		// 璁剧疆鎸夐挳鐩戝惉
        bt_message.setOnClickListener(this);
        bt_friends.setOnClickListener(this);
		
	}

	private void initView() {

		bt_message = (TextView) findViewById(R.id.bt_message);
		bt_friends = (TextView) findViewById(R.id.bt_friends);
	}
	@Override
	public void onClick(View v) {
		
		// 鍦ㄦ瘡娆＄偣鍑诲悗灏嗘墍鏈夌殑搴曢儴鎸夐挳(ImageView,TextView)棰滆壊鏀逛负鐏拌壊锛岀劧鍚庢牴鎹偣鍑荤潃鑹�
        restartBotton();
        // ImageView鍜孴etxView缃负缁胯壊锛岄〉闈㈤殢涔嬭烦杞�
        switch (v.getId()) {
        case R.id.bt_message:
            bt_message.setTextColor(0xff1B940A);
            initFragment(0);
            break;
        case R.id.bt_friends:
            bt_friends.setTextColor(0xff1B940A);
            initFragment(1);
            break;
        default:
            break;
        }
	}
	
	private void restartBotton() {
        // Button缃负鐧借壊
		bt_message.setTextColor(0xffffffff);
		bt_friends.setTextColor(0xffffffff);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

}