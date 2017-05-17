package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dao.ArticleCommentDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.ArticleComment;
import com.etc.entity.User;

public class ArticleCommentDAOimpl implements ArticleCommentDAO {

	DBManager dbManager = new DBManager();
	
	@Override
	public boolean writeArtiComm(ArticleComment comment) {
		// TODO Auto-generated method stub
		String sql = "insert into articlecomm values(null, ?, ?, ?)";
		return dbManager.execUpdate(sql, comment.getArticleid(), comment.getUserid(), comment.getCommentCont()) > 0;
	}

	@Override
	public List<ArticleComment> findArtiCommList(int articleid) {
		// TODO Auto-generated method stub
		String sql = "select articlecomm.*,username,photo,user.userid from user,articlecomm where user.userid = articlecomm.userid and articleid = ? order by arti_comm_id desc";
		ResultSet rs = dbManager.execQuery(sql,articleid);
		List<ArticleComment> list = new ArrayList<ArticleComment>();
		try {
			while(rs.next()){							
				ArticleComment comment = new ArticleComment();
				comment.setCommentid(rs.getInt(1));
				comment.setArticleid(rs.getInt(2));
				comment.setUserid(rs.getInt(3));
				comment.setCommentCont(rs.getString(4));
				
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
