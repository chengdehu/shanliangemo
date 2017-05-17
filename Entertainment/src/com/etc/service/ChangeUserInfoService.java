package com.etc.service;

import java.util.ArrayList;
import java.util.List;

import com.etc.dbutil.DBManager;
import com.etc.entity.User;


public class ChangeUserInfoService {
	DBManager manager = new DBManager();
	public List<User> getUsers (String username,String password,int userid){
		//String sql = "select * from itemcomment where comment_id>? limit 0,?";
		String sql = "update user set username=?,password=? where userid =?";
		int rst = manager.execUpdate(sql, username,password,userid);
		List<User> users = new ArrayList<User>();
		while(rst>0){
			User user = new User();
			/*user.setUserid(rst.getInt(1));
			user.setUsername(rst.getString(2));
			user.setPassword(rst.getString(3));
			user.setScore(rst.getInt(4));
			user.setPhoto(rst.getString(5));
			user.setBirthday(rst.getString(6));
			*/
			users.add(user);
			
		}
		return users;
	}
}
