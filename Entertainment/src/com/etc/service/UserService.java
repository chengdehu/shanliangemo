package com.etc.service;

import java.util.List;

import com.etc.entity.User;

public interface UserService {
	User Login(String username,String password);
	User FindUsername(String username);
	User UserRegister(String username,String password);

	User findUser(int userid);
	boolean addFriend(int userid, int friend_id);
	List<Integer> findFriends_id(int userid);
	boolean updateUserLabel(int userid, String label);
	
	List<Integer> recommendToUser(int userid, String itemtype);
}
