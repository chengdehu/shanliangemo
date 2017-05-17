package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dao.ChatDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.ChatMessage;
import com.etc.entity.User;

public class ChatDAOImpl implements ChatDAO {
	
	private DBManager dbManager = new DBManager();

	public List<ChatMessage> findChatList(int fromUserid) {
		//String sql = "select m.userid,m.username,m.photo,d.time,d.message from chatrecord d, user m where d.Use_userid = m.userid and d.time = (select max(time) from chatrecord c, user u where c.userid=? and c.Use_userid = d.Use_userid and c.Use_userid=u.userid ) order by d.time desc";
		String sql = "select a,user.username,user.photo,max(t),message from ((select d.Use_userid as a,d.time as t,d.message from chatrecord d where d.time = (select max(time) from chatrecord c where c.userid=? and c.Use_userid = d.Use_userid)) "
				+ "union(select x.userid as a,x.time as t,x.message from chatrecord x where x.time = (select max(time) from chatrecord y where y.Use_userid=? and y.userid = x.userid))) as s,user where s.a=user.userid group by a order by max(t) desc";
		
		ResultSet rs = dbManager.execQuery(sql, fromUserid, fromUserid);
		
		//准备存放user对象的容器
		List<ChatMessage> list = new ArrayList<ChatMessage>();
        try {
			while(rs.next()){
				//创建并填充实体bean
				ChatMessage chat = new ChatMessage();
				User user = new User();
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPhoto(rs.getString(3));
				chat.setTime(rs.getString(4));
				chat.setMessage(rs.getString(5));
				chat.setUser(user);
				
				list.add(chat);
				
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

	public List<ChatMessage> findChatRecord(int fromUserid, int toUserid) {
		//String sql = "select u.userid,u.username,u.photo,c.time,c.message,c.isComMeg from user u,chatrecord c where c.userid=? and c.Use_userid=?  and c.Use_userid=u.userid order by c.time";
		String sql = "select c.userid,c.Use_userid,c.time,c.message from user u,chatrecord c where c.Use_userid=u.userid and ((c.userid=? and c.Use_userid=?) or (c.userid=? and c.Use_userid=?)) order by c.time";
		//String sql ="select * from chatrecord where userid=? and Use_userid=?";
		ResultSet rs = dbManager.execQuery(sql,fromUserid,toUserid,toUserid,fromUserid);
		//ResultSet rs = dbManager.execQuery(sql,fromUserid,toUserid,toUserid,fromUserid);
		
		//准备存放user对象的容器
		List<ChatMessage> list = new ArrayList<ChatMessage>();
        try {
			while(rs.next()){
				//创建并填充实体bean
				ChatMessage chat = new ChatMessage();
				User user = new User();
				chat.setfromUserid(rs.getInt(1));
				chat.settoUserid(rs.getInt(2));
				chat.setTime(rs.getString(3));
				chat.setMessage(rs.getString(4));
				
				list.add(chat);
				
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

	public boolean addChat(ChatMessage chat) { 
		String sql = "insert into chatrecord values(?, ? ,now(),?)";
	
		return dbManager.execUpdate(sql, chat.getfromUserid(), chat.gettoUserid(), chat.getMessage()) > 0;

	}

	public boolean deleteChat(ChatMessage chat) {
		
		String sql = "delete from chatrecord where userid = ? and Use_userid = ? and time = ?";
		
		return dbManager.execUpdate(sql, chat.getfromUserid(), chat.gettoUserid(), chat.getTime()) > 0;
		
	}
	
	public boolean deleteAllChat(ChatMessage chat) {
		
		String sql = "delete from chatrecord where (userid = ? and Use_userid = ?) or (userid = ? and Use_userid = ?)";
		
		return dbManager.execUpdate(sql, chat.getfromUserid(), chat.gettoUserid(), chat.gettoUserid(), chat.getfromUserid()) > 0;
		
	}

}
