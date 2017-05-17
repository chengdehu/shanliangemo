package com.etc.dao;

import java.util.List;

import com.etc.entity.User;

public interface FriendsDAO{
	
	//查询好友列表
	List<User> findFriendsList(int userid);

}
