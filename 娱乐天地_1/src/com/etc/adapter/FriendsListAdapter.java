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
import com.etc.entity.User;
import com.etc.task.AsynBitmapTask_w;

public class FriendsListAdapter extends BaseAdapter {
	// 澹版槑
	Context context;
	List<User> data;
	LayoutInflater layout;
	AsynBitmapTask_w asyntask;
	String HeadUrl = "http://10.0.2.2:8080/Entertainment/image/photo/";

	public FriendsListAdapter(Context context, List<User> data,
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
			view = layout.inflate(R.layout.friends_item, null);

		// 鑾峰彇甯冨眬涓殑鍚勪釜缁勪欢
		ImageView imgFriend = (ImageView) view.findViewById(R.id.imgFriend);
		TextView txtFriendName = (TextView) view
				.findViewById(R.id.txtFriendName);

		// 濉厖鏁版嵁
		User user = data.get(position);
		// new HeadLoadTask(imgFriend).execute(HeadUrl+user.getPhoto());

		txtFriendName.setText(user.getUsername());

		Bitmap bitmap = this.asyntask.loadBitmap(imgFriend,
				HeadUrl + user.getPhoto(), new AsynBitmapTask_w.ImageCallback() {
					@Override
					public void Imageload(ImageView imageview, Bitmap bitmap) {
						imageview.setImageBitmap(bitmap);
					}
				});

		if (bitmap != null) {
			imgFriend.setImageBitmap(bitmap);
		}
		return view;
	}

}
