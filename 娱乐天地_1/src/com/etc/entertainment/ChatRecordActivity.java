package com.etc.entertainment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.etc.app.MyApp;
import com.etc.entity.ChatMessage;
import com.etc.entity.User;
import com.etc.task.AsynBitmapTask_w;
import com.etc.task.ChatAddTask;
import com.etc.task.ChatDeleteAllTask;
import com.etc.task.ChatDeleteTask;
import com.etc.task.LoadChatRecordTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatRecordActivity extends Activity {

	private PopupWindow popupDelete;
	private PopupWindow popupMenu;
	private TextView txtDelete;
	private Button btnBackground;
	private Button btnDeleteAll;
	private Button butFriendInfo;
	private Button butBacktoChat;
	private int longClickPosition;
	private int fromUserid, toUserid;
	private String photo;
	private EditText edtMessage;
	private TextView txtName;
	private ChatRecordAdapter adapter;
	private ListView lvwChatRecord;
	private List<ChatMessage> data;
	private Handler handler, updateHandler;
	private Context context;
	private Dialog dlgDeleteAll;
	private Uri imageUri;
	//private RelativeLayout layout;
	private AsynBitmapTask_w asyntask;

	String HeadUrl = "http://10.0.2.2:8080/Entertainment/image/photo/";

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		Intent intent = this.getIntent();
		photo = intent.getStringExtra("photo");
		toUserid = Integer.parseInt(intent.getStringExtra("toUserid"));
		MyApp myApp=(MyApp)getApplicationContext();
		
		fromUserid = myApp.getUser().getUserid();

		txtName = (TextView) findViewById(R.id.txtName);
		txtName.setText(intent.getStringExtra("username"));
		lvwChatRecord = (ListView) findViewById(R.id.lvChatList);
		edtMessage = (EditText) findViewById(R.id.edtMessage);
		
		SharedPreferences sp = getSharedPreferences("ChatInfo", MODE_PRIVATE);
		if(sp.contains("chatBkg")){
			System.out.println(sp.getString("chatBkg", null));
			String path = sp.getString("chatBkg", null);
			Bitmap Newbitmap = BitmapFactory.decodeFile(path);
			Drawable NewBitmapDrawable = new BitmapDrawable(getResources(),Newbitmap);
			//this.lvwChatRecord.setBackground(NewBitmapDrawable);
			this.lvwChatRecord.setBackground(NewBitmapDrawable);
		}
		
		this.asyntask = new AsynBitmapTask_w();
		context = this.getApplicationContext();
		this.data = new ArrayList<ChatMessage>();
		this.adapter = new ChatRecordAdapter(asyntask, context, photo);
		this.lvwChatRecord.setAdapter(adapter);

		this.handler = new Handler(new LoadChatRecordCallback());
		new LoadChatRecordTask(fromUserid, toUserid, handler).start();
		createDialog();
		

		MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.start();
		
		updateHandler = new Handler(){
			
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				if(msg.what==2){
					
					/*asyntask = new AsynBitmapTask();
					context = getApplicationContext();
					data = new ArrayList<ChatMessage>();
					adapter = new ChatRecordAdapter(asyntask, context, photo);
					lvwChatRecord.setAdapter(adapter);

					handler = new Handler(new LoadChatRecordCallback());
					new LoadChatRecordTask(1, 4, handler).start();
					MyThread myThread = new MyThread();
			        Thread thread = new Thread(myThread);
			        thread.start();
			        //data.clear();*/
					
			        adapter.notifyDataSetChanged();
				}
			}
		};
	}

	// 纭鍒犻櫎鎵�湁璁板綍瀵硅瘽妗�
	private void createDialog() {
		Builder builder = new Builder(this);

		builder.setTitle("删除确认框");
		builder.setMessage("是否确认删除");
		builder.setIcon(R.drawable.question);

		builder.setPositiveButton("是", new DeleteOnClickListenerImpl());
		builder.setNegativeButton("否", null);

		this.dlgDeleteAll = builder.create();
	}

	private class DeleteOnClickListenerImpl implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int id) {

			if (id == Dialog.BUTTON_POSITIVE) {
				new ChatDeleteAllTask(context).execute(fromUserid + "",
						toUserid + "");
				popupMenu.dismiss();
				Intent intent = new Intent(ChatRecordActivity.this,
						ChatFriendsActivity.class);
				startActivity(intent);
				finish();
				Toast.makeText(getApplicationContext(), "聊天记录删除成功",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	// 閫傞厤鍣�
	public class ChatRecordAdapter extends BaseAdapter {

		LayoutInflater layout;
		String HeadUrl = "http://10.0.2.2:8080/Entertainment/image/photo/";
		AsynBitmapTask_w asyntask;
		String photo;
		String fromPhoto;

		public ChatRecordAdapter(AsynBitmapTask_w asyntask2, Context context,
				String photo) {
			this.asyntask = asyntask2;
			this.layout = LayoutInflater.from(context);
			this.photo = photo;
			MyApp myApp=(MyApp)getApplicationContext();
			fromPhoto=myApp.getUser().getPhoto();
		}

		// 鑾峰彇ListView鐨勯」涓暟
		public int getCount() {
			return data.size();
		}

		// 鑾峰彇椤�
		public Object getItem(int position) {
			return data.get(position);
		}

		// 鑾峰彇椤圭殑ID
		public long getItemId(int position) {
			return position;
		}

		// 鑾峰彇View
		public View getView(int position, View view, ViewGroup parent) {
			ChatMessage chat = data.get(position);

			ViewHolder viewHolder = null;
			if (view == null) {
				if (chat.gettoUserid() == fromUserid) {
					// 濡傛灉鏄鏂瑰彂鏉ョ殑娑堟伅锛屽垯鏄剧ず鐨勬槸宸︽皵娉�
					view = View.inflate(getApplicationContext(),
							R.layout.chatting_item_msg_text_left, null);
				} else {
					// 濡傛灉鏄嚜宸卞彂鍑虹殑娑堟伅锛屽垯鏄剧ず鐨勬槸鍙虫皵娉�
					view = View.inflate(getApplicationContext(),
							R.layout.chatting_item_msg_text_right, null);
				}

				viewHolder = new ViewHolder();
				viewHolder.imgPhoto = (ImageView) view.findViewById(R.id.photo);
				viewHolder.txtTime = (TextView) view.findViewById(R.id.time);
				viewHolder.txtMessage = (TextView) view
						.findViewById(R.id.message);
				viewHolder.txtMessage.setOnLongClickListener(longClickListener);
				viewHolder.txtMessage.setTag(position);

				if (chat.getfromUserid() == fromUserid) {
					Bitmap bitmap = asyntask.loadBitmap(viewHolder.imgPhoto,
							HeadUrl + fromPhoto,
							new AsynBitmapTask_w.ImageCallback() {
								@Override
								public void Imageload(ImageView imageview,
										Bitmap bitmap) {
									imageview.setImageBitmap(bitmap);
								}
							});

					if (bitmap != null) {
						viewHolder.imgPhoto.setImageBitmap(bitmap);
					}
				} else {
					Bitmap bitmap = asyntask.loadBitmap(viewHolder.imgPhoto,
							HeadUrl + photo,
							new AsynBitmapTask_w.ImageCallback() {
								@Override
								public void Imageload(ImageView imageview,
										Bitmap bitmap) {
									imageview.setImageBitmap(bitmap);
								}
							});

					if (bitmap != null) {
						viewHolder.imgPhoto.setImageBitmap(bitmap);
					}
				}

				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.txtTime.setText(chat.getTime());
			viewHolder.txtMessage.setText(chat.getMessage());
			return view;
		}

		// 閫氳繃ViewHolder鏄剧ず椤圭殑鍐呭
		class ViewHolder {
			public ImageView imgPhoto;
			public TextView txtTime;
			public TextView txtMessage;
		}
	}

	// 闀挎寜鑱婂ぉ璁板綍锛屽脊鍑哄垹闄ゆ
	private OnLongClickListener longClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			longClickPosition = (Integer) v.getTag();
			showDeleteDialog(v);
			return true;
		}
	};

	// 鍒犻櫎璁板綍鐨勫脊妗�
	private void showDeleteDialog(View view) {
		if (null == popupDelete) {
			View popView = LayoutInflater.from(this).inflate(
					R.layout.chat_item_delete, null);
			txtDelete = (TextView) popView.findViewById(R.id.txtDelete);
			txtDelete.setOnClickListener(clickListener);
			popupDelete = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			popupDelete.setAnimationStyle(R.style.PopAnimStyle);
			popupDelete.setOutsideTouchable(true);
			// popupWindow.setFocusable(true);
			popupDelete.setBackgroundDrawable(new BitmapDrawable());
		}

		popupDelete.showAsDropDown(view, -5,-100);
	}

	// 璁剧疆娣诲姞灞忓箷鐨勮儗鏅�鏄庡害
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha;
		getWindow().setAttributes(lp);
	}

	// 鑱婂ぉ璁板綍鐣岄潰鐨勮彍鍗�
	public void showChatMenuDialog(View view) {
		backgroundAlpha(0.5f);
		if (null == popupMenu) {

			View popView = LayoutInflater.from(this).inflate(R.layout.chat_menu, null);
			btnBackground = (Button) popView.findViewById(R.id.btnBackground);
			btnDeleteAll = (Button) popView.findViewById(R.id.btnDeleteAll);
			butFriendInfo = (Button) popView.findViewById(R.id.btnFriendInfo);
			butBacktoChat = (Button) popView.findViewById(R.id.btnBacktoChat);
			btnBackground.setOnClickListener(clickListener);
			btnDeleteAll.setOnClickListener(clickListener);
			butFriendInfo.setOnClickListener(clickListener);
			butBacktoChat.setOnClickListener(clickListener);

			popupMenu = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			popupMenu.setAnimationStyle(R.style.PopAnimStyle);
			popupMenu.setOutsideTouchable(true);
			popupMenu.setBackgroundDrawable(new BitmapDrawable());
			// 娣诲姞pop绐楀彛鍏抽棴浜嬩欢
			popupMenu.setOnDismissListener(new popupDismissListener());
		}
		popupMenu.showAsDropDown(view,50,0);

	};

	// 寮规娑堝け锛屾仮澶嶉粯璁ょ殑閫忔槑搴�
	class popupDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {

			backgroundAlpha(1f);
		}

	}

	// 浜嬩欢鐩戝惉
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.txtDelete:
				ChatMessage chat = data.get(longClickPosition);
				String time = chat.getTime();
				data.remove(longClickPosition);
				adapter.notifyDataSetChanged();
				new ChatDeleteTask(context).execute(fromUserid+"", toUserid + "", time);
				popupDelete.dismiss();
				break;
			case R.id.btnDeleteAll:
				dlgDeleteAll.show();
				break;
			case R.id.btnBackground:
				Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, 1);
				popupMenu.dismiss();
				break;
			case R.id.btnFriendInfo:
				//Intent intentFriend = new Intent(ChatRecordActivity.this,ChatListActivity.class);
				//startActivity(intentFriend);
				//finish();
				popupMenu.dismiss();
				break;
			case R.id.btnBacktoChat:
				popupMenu.dismiss();
			break;
			}

		}
	};

	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// 鍒ゆ柇鏄惁浠庣敾寤婂簲鐢ㄨ繑鍥�
		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {

				// 杩斿洖鍥剧墖鐨剈ri
				imageUri = data.getData();

				// 璁剧疆鑱婂ぉ鑳屾櫙
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(imageUri,filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String pathTemp = cursor.getString(columnIndex);							

				//(1)浣跨敤宸ュ巶鏂规硶鑾峰彇SharedPreferences鎺ュ彛鐨勫疄鐜扮被锛屽苟灏嗘暟鎹繚瀛樺湪ChatInfo.xml涓�
				SharedPreferences sp = getSharedPreferences("ChatInfo", MODE_PRIVATE);
					
				//(2)鑾峰彇缂栬緫鍣ㄥ璞★紝寮�惎缂栬緫妯″紡
				Editor editor = sp.edit();

				//(3)璋冪敤缂栬緫鍣ㄧ殑putXXX()鏂规硶锛屾坊鍔犳暟鎹悕-鍊煎
				editor.putString("chatBkg", pathTemp);

				//(4)鎻愪氦鏇存柊
				editor.commit();
				
				Bitmap bitmap = BitmapFactory.decodeFile(pathTemp);
				Drawable  BitmapDrawable = new BitmapDrawable(getResources(),bitmap);
				this.lvwChatRecord.setBackground(BitmapDrawable);
			}
		}
	}

	// 娑堟伅鍥炶皟
	class LoadChatRecordCallback implements Callback {

		@Override
		public boolean handleMessage(Message msg) {
			if (msg.arg1 == 0) {
				parseGSONString(msg.obj.toString());
			} else {
				Toast.makeText(getApplicationContext(), "数据请求失败",
						Toast.LENGTH_LONG).show();
			}
			return false;
		}
	}

	// 瑙ｆ瀽json瀛楃涓�
	private void parseGSONString(String gstr) {
		Gson gson = new Gson();

		Type type = new TypeToken<List<ChatMessage>>() {}.getType();
		List<ChatMessage> chatMessage = gson.fromJson(gstr, type);
		for (ChatMessage chat : chatMessage) {
			data.add(chat);
		}
		adapter.notifyDataSetChanged();
	}

	// 鍙戦�娑堟伅
	public void send(View v) {
		String message = edtMessage.getText().toString();
		if (message.length() > 0) {
			User user = new User();
			user.setUserid(toUserid);
			user.setPhoto(photo);
			ChatMessage chat = new ChatMessage();
			chat.setfromUserid(fromUserid);
			chat.settoUserid(toUserid);
			chat.setMessage(message);
			chat.setTime(getTime());
			chat.setUser(user);

			data.add(chat);
			new ChatAddTask(this.getApplicationContext()).execute(fromUserid + "", toUserid + "", message);
			edtMessage.setText("");

			this.adapter = new ChatRecordAdapter(asyntask, context, photo);
			this.lvwChatRecord.setAdapter(adapter);
			adapter.notifyDataSetChanged();

			lvwChatRecord.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

			lvwChatRecord.setSelection(lvwChatRecord.getCount() - 1);
		}
	}

	// 鑾峰彇鏃ユ湡
	private String getTime() {
		String year, month,day,hour,mins,sec;
		Calendar c = Calendar.getInstance();
		year = String.valueOf(c.get(Calendar.YEAR));
		month = String.valueOf(c.get(Calendar.MONTH) + 1);
		day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		mins = String.valueOf(c.get(Calendar.MINUTE));
		sec = String.valueOf(c.get(Calendar.SECOND));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins + ":" + sec);
		return sbBuffer.toString();
	}

	// 杩斿洖
	public void back(View v) {

		Intent intent = new Intent(ChatRecordActivity.this,ChatFriendsActivity.class);
		startActivity(intent);
		finish();

	}
	
	public class MyThread implements Runnable{

		@Override
		public void run() {
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				updateHandler.sendEmptyMessage(2);
		
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}
}
