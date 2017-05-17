package com.etc.service.impl;

import java.util.List;

import com.etc.dao.ChatDAO;
import com.etc.dao.FriendsDAO;
import com.etc.dao.impl.ChatDAOImpl;
import com.etc.dao.impl.FriendsDAOImpl;
import com.etc.entity.ChatMessage;
import com.etc.entity.User;
import com.etc.service.FriendsService;
import com.etc.util.StringUtil;

public class FriendsServiceImpl implements FriendsService {
	
	private FriendsDAO friendsDAO = new FriendsDAOImpl();

	public List<User> getFriendsList(int userid) {
		List<User> list = friendsDAO.findFriendsList(userid);
		return list;
	}

}
