package com.etc.service;

import java.util.List;

import com.etc.entity.User;

public interface FriendsService {

	//获取聊天列表
	List<User> getFriendsList(int userid);
}
