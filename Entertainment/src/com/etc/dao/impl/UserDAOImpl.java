package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dao.UserDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.User;

public class UserDAOImpl implements UserDAO {

	DBManager dbManager=new DBManager();
	@Override
	public User findUser(String username, String password) {
		String sql="select * from user where username = ? and password= ?";
		DBManager db=new DBManager();
		ResultSet rs=db.execQuery(sql, username,password);
		try {
			if(rs.next())
			{
				User user=new User();
				user.setUserid(rs.getInt(1));
				user.setUsername(username);
				user.setScore(rs.getInt(4));
				user.setPhoto(rs.getString(5));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeConnection();
			}
		return null;
		
	}

	@Override
	public User findUsername(String username) {
		String sql="select * from user where username = ?";
		DBManager db=new DBManager();
		ResultSet rs=db.execQuery(sql, username);
		try {
			if(rs.next())
			{
				User user=new User();
				user.setUsername(username);
				user.setScore(rs.getInt(4));
				user.setPhoto(rs.getString(5));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeConnection();
			}
		return null;
	}

	@Override
	public User Register(String username, String password) {
		System.out.println("+++++++");
		String sql="insert into user values(null,?,?,0,null,null)";
		DBManager db=new DBManager();
		System.out.println(username+password+"+++++++++++");
		db.execUpdate(sql, username,password);
		
		return findUser(username,password);
	}
	
	public User findUser(int userid) {
		// TODO Auto-generated method stub
		String sql = "select * from user where userid = ?" ;
		ResultSet rs = dbManager.execQuery(sql, userid);		
		try {
			if(rs.next()){
				User user = new User();
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setScore(rs.getInt(4));
				user.setPhoto(rs.getString(5));
				user.setBirthday(rs.getString(6));
				return user;
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean addFriend(int userid, int friend_id) {
		// TODO Auto-generated method stub
		String sql = "insert into friends values (?, ?)";
		return dbManager.execUpdate(sql, userid, friend_id) > 0;
	}
	
	@Override
	public List<Integer> findFriends_id(int userid) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select * from friends where userid = ?";
		ResultSet rs = dbManager.execQuery(sql, userid);
		try {
			while(rs.next()){
				list.add(rs.getInt(2));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean updateUserLabel(int userid, String label) {
		// TODO Auto-generated method stub
		String sql1 = "select * from userlabel where userid = ? and label_name = ?;";
		String sql2 = "update userlabel set count = count + 1 where userid = ? and label_name = ?;";
		String sql3 = "insert into userlabel values(?, ?, 0)";
		ResultSet rs = dbManager.execQuery(sql1, userid, label);
		try {
			if(rs.next()){
				return dbManager.execUpdate(sql2, userid, label) > 0;
			}else
				return dbManager.execUpdate(sql3, userid, label) > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<Integer> recommendToUser(int userid, String itemtype) {
		// TODO Auto-generated method stub
		String label = "";
		String sql = "select label_name from userlabel where userid = ? order by count desc limit 0, 2";
		String sql1 = "select distinct e1.itemid from entertainmentitem as e1, enterlabel as e2 "
				+ "where e1.itemid = e2.itemid and e1.itemtype = ? and e2.label_name = ?";
		String sql2 = "select distinct itemid from entertainmentitem where itemtype = ? order by itemcount desc limit 0, 5";
		ResultSet rs = dbManager.execQuery(sql, userid);
		ResultSet rs2 = dbManager.execQuery(sql2, itemtype);
		List<Integer> list = new ArrayList<Integer>();
		try {
			while(rs.next()){							
				label = rs.getString(1);
				ResultSet rs1 = dbManager.execQuery(sql1, itemtype, label);
				while(rs1.next()){
					int itemid = rs1.getInt(1);
					for(int i=0;i<list.size()+1;i++)
					{
						if(!list.contains(itemid))
							list.add(itemid);
					}
					
				}
			}
			if(list.size() == 0){
				while(rs2.next()){
					int itemid = rs2.getInt(1);
					list.add(itemid);
				}
			}

			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
