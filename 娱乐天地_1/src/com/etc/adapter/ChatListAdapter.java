package com.etc.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etc.entertainment.R;
import com.etc.entity.ChatMessage;
import com.etc.task.AsynBitmapTask_w;

public class ChatListAdapter extends BaseAdapter {
	// 澹版槑
	Context context;
	List<ChatMessage> data;
	LayoutInflater layout;
	AsynBitmapTask_w asyntask;
	String HeadUrl = "http://10.0.2.2:8080/Entertainment/image/photo/";

	public ChatListAdapter(Context context, List<ChatMessage> data,
			AsynBitmapTask_w asyntask) {
		this.context = context;
		this.data = data;
		this.asyntask = asyntask;
		this.layout = LayoutInflater.from(context);
	}

	// 杩斿洖鏁版嵁椤�鐨勪釜鏁�
	@Override
	public int getCount() {
		return data.size();
	}

	// 杩斿洖褰撳墠鏁版嵁椤瑰搴旂殑椤�
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	// 杩斿洖褰撳墠鏁版嵁椤圭殑id
	@Override
	public long getItemId(int position) {
		return position;
	}

	// 杩斿洖褰撳墠鏁版嵁椤瑰搴旂殑瑙嗗浘
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// 鑾峰彇椤圭洰甯冨眬
		if (view == null)
			view = layout.inflate(R.layout.chat_item, null);

		// 鑾峰彇甯冨眬涓殑鍚勪釜缁勪欢
		ImageView imgHead = (ImageView) view.findViewById(R.id.imgHead);
		TextView txtUsername = (TextView) view.findViewById(R.id.txtUsername);
		TextView txtMessage = (TextView) view.findViewById(R.id.txtMessage);
		TextView txtTime = (TextView) view.findViewById(R.id.txtTime);

		// 濉厖鏁版嵁
		ChatMessage chatMessage = data.get(position);

		txtMessage.setText(chatMessage.getMessage());
		txtTime.setText(chatMessage.getTime());
		txtUsername.setText(chatMessage.getUser().getUsername());
		// new
		// HeadLoadTask(imgHead).execute(HeadUrl+chatMessage.getUser().getPhoto());
		// System.out.println(HeadUrl+chatMessage.getUser().getPhoto());

		Bitmap bitmap = this.asyntask.loadBitmap(imgHead, HeadUrl
				+ chatMessage.getUser().getPhoto(),
				new AsynBitmapTask_w.ImageCallback() {
					@Override
					public void Imageload(ImageView imageview, Bitmap bitmap) {
						imageview.setImageBitmap(bitmap);
					}
				});

		if (bitmap != null) {
			imgHead.setImageBitmap(bitmap);
		}

		return view;
	}

}
