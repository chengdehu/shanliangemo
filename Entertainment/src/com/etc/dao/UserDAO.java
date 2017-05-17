package com.etc.dao;

import java.util.List;

import com.etc.entity.User;

public interface UserDAO {
	User findUser(String username,String password);
    User findUsername(String username);
    User Register(String username,String password);
	User findUser(int userid);
	boolean addFriend(int userid, int friend_id);
	List<Integer> findFriends_id(int userid);
	
    boolean updateUserLabel(int userid, String label);
	
	List<Integer> recommendToUser(int userid, String itemtype);

}
