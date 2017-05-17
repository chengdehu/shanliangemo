package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dao.FriendsDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.ChatMessage;
import com.etc.entity.User;

public class FriendsDAOImpl implements FriendsDAO {
	
	private DBManager dbManager = new DBManager();

	public List<User> findFriendsList(int userid) {
		String sql = "(SELECT user.userid,user.username,user.photo FROM friends,user where friends.Use_userid = ? and friends.userid = user.userid) union (SELECT user.userid,user.username,user.photo FROM friends,user where friends.userid = ? and friends.Use_userid = user.userid)  order by username";
		ResultSet rs = dbManager.execQuery(sql, userid, userid);
		//准备存放user对象的容器
		List<User> list = new ArrayList<User>();
        try {
			while(rs.next()){
				//创建并填充实体bean
				User user = new User();
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPhoto(rs.getString(3));
				
				list.add(user);
				
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
        finally{
        	dbManager.closeConnection();
        }
		return null;
	}

}
