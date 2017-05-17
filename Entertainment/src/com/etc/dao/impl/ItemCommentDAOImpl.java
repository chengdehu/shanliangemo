package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dao.ItemCommentDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.ItemComment;
import com.etc.entity.User;

public class ItemCommentDAOImpl implements ItemCommentDAO {
DBManager dbManager = new DBManager();
	
	@Override
	public boolean writeItemComm(ItemComment comment) {
		// TODO Auto-generated method stub
		String sql = "insert into itemcomment values(null, ?, ?, ?)";
		return dbManager.execUpdate(sql, comment.getItem_id(), comment.getUser_id(), comment.getComment_cont()) > 0;
	}

	@Override
	public List<ItemComment> findItemCommList(int itemid) {
		// TODO Auto-generated method stub
		String sql = "select itemcomment.*,username,photo,user.userid from user,itemcomment where user.userid = itemcomment.userid and itemid = ? order by comment_id desc";
		ResultSet rs = dbManager.execQuery(sql,itemid);
		List<ItemComment> list = new ArrayList<ItemComment>();
		try {
			while(rs.next()){							
				ItemComment comment = new ItemComment();
				comment.setComment_id(rs.getInt(1));
				comment.setItem_id(rs.getInt(2));
				comment.setUser_id(rs.getInt(3));
				comment.setComment_cont(rs.getString(4));
				
				User user = new User();
				user.setUsername(rs.getString(5));
				user.setPhoto(rs.getString(6));
				user.setUserid(rs.getInt(7));
				comment.setUser(user);
				list.add(comment);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
