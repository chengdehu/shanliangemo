package com.etc.service.impl;

import java.util.List;

import com.etc.dao.UserDAO;
import com.etc.dao.impl.UserDAOImpl;
import com.etc.entity.User;
import com.etc.service.UserService;

public class UserServiceImpl implements UserService {

	UserDAO dao=new UserDAOImpl();
	@Override
	public User Login(String username, String password) {
		return dao.findUser(username, password);
		
	}
	@Override
	public User FindUsername(String username) {
		
		return dao.findUsername(username);
	}
	@Override
	public User UserRegister(String username, String password) {
		
		return dao.Register(username, password);
	}
	@Override
	public User findUser(int userid) {
		// TODO Auto-generated method stub
		User user = dao.findUser(userid);
		return user;
	}
	
	@Override
	public boolean addFriend(int userid, int friend_id) {
		// TODO Auto-generated method stub
		return dao.addFriend(userid, friend_id);
	}
	
	@Override
	public List<Integer> findFriends_id(int userid) {
		// TODO Auto-generated method stub
		List<Integer> list = dao.findFriends_id(userid);
		return list;
	}
	
	@Override
	public boolean updateUserLabel(int userid, String label) {
		// TODO Auto-generated method stub
		return dao.updateUserLabel(userid, label);
	}

	@Override
	public List<Integer> recommendToUser(int userid, String itemtype) {
		// TODO Auto-generated method stub
		List<Integer> list = dao.recommendToUser(userid, itemtype);
		return list;
	}

}
