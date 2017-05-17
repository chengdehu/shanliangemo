package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dao.ArticleDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.Article;
import com.etc.entity.User;

public class ArticleDAOimpl implements ArticleDAO {

	DBManager dbManager = new DBManager();
	@Override
	public Article findArticle(int articleid) {
		// TODO Auto-generated method stub
		String sql = "select article.*,username,photo,user.userid from user,article where user.userid = article.userid and articleid = ?" ;
		ResultSet rs = dbManager.execQuery(sql, articleid);		
		try {
			if(rs.next()){
				Article article = new Article();
				article.setArticleid(rs.getInt(1));
				article.setUserid(rs.getInt(2));
				article.setArticletitle(rs.getString(3));
				article.setArticlecont(rs.getString(4));
				article.setPublishtime(rs.getString(5));
				article.setLikecount(rs.getInt(6));
				article.setDislikecount(rs.getInt(7));
				
				User user = new User();
				user.setUsername(rs.getString(8));
				user.setPhoto(rs.getString(9));
				user.setUserid(rs.getInt(10));
				article.setUser(user);
				return article;
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Article> findArticleList(String count) {
		int i = 0;
		int start = 0;
		// TODO Auto-generated method stub
		String sql = "select article.*,username,photo,user.userid from user,article where user.userid = article.userid order by publishTime desc";
		String sql1 = "select article.*,username,photo,user.userid from user,article where user.userid = article.userid order by publishTime desc limit ?, " + count;
		ResultSet rs = dbManager.execQuery(sql);
		List<Article> list = new ArrayList<Article>();
		
		try {
			while(rs.next()){							
				i += 1;
			}		
			start = i-Integer.parseInt(count.trim());
			if(start < 0)
				rs = dbManager.execQuery(sql1, 0);
			else
				rs = dbManager.execQuery(sql1, start);
			while(rs.next()){
				Article article = new Article();
				article.setArticleid(rs.getInt(1));
				article.setUserid(rs.getInt(2));
				article.setArticletitle(rs.getString(3));
				article.setArticlecont(rs.getString(4));
				article.setPublishtime(rs.getString(5));
				article.setLikecount(rs.getInt(6));
				article.setDislikecount(rs.getInt(7));
				
				User user = new User();
				user.setUsername(rs.getString(8));
				user.setPhoto(rs.getString(9));
				user.setUserid(rs.getInt(10));
				article.setUser(user);
				list.add(article);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean writeArticle(Article article) {
		// TODO Auto-generated method stub
		String sql = "insert into article values(null, ? ,? ,? ,now() ,0 ,0 )";		
		return dbManager.execUpdate(sql, article.getUserid(), article.getArticletitle(), article.getArticlecont())  > 0;		
	}
	
	@Override
	public boolean deleteArticle(int articleid) {
		// TODO Auto-generated method stub
		String sql1 = "delete from articlecomm where articleid = ?";
		dbManager.execUpdate(sql1, articleid);
		String sql2 = "delete from collection where articleid = ?";
		dbManager.execUpdate(sql2, articleid);
		String sql3 = "delete from article where articleid = ?";
		return dbManager.execUpdate(sql3, articleid) > 0;
	}

	@Override
	public List<Article> findArticleList() {
		// TODO Auto-generated method stub
		String sql = "select article.*,username,photo,user.userid from user,article where user.userid = article.userid order by publishTime desc";
		ResultSet rs = dbManager.execQuery(sql);
		List<Article> list = new ArrayList<Article>();
		try {
			while(rs.next()){							
				Article article = new Article();
				article.setArticleid(rs.getInt(1));
				article.setUserid(rs.getInt(2));
				article.setArticletitle(rs.getString(3));
				article.setArticlecont(rs.getString(4));
				article.setPublishtime(rs.getString(5));
				article.setLikecount(rs.getInt(6));
				article.setDislikecount(rs.getInt(7));
				
				User user = new User();
				user.setUsername(rs.getString(8));
				user.setPhoto(rs.getString(9));
				user.setUserid(rs.getInt(10));
				article.setUser(user);
				list.add(article);
			}			
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Article> findArticleList(int userid) {
		// TODO Auto-generated method stub
		String sql = "select article.*,username,photo,user.userid from user,article where user.userid = article.userid and user.userid = ? order by publishTime desc";
		ResultSet rs = dbManager.execQuery(sql, userid);
		List<Article> list = new ArrayList<Article>();
		try {
			while(rs.next()){							
				Article article = new Article();
				article.setArticleid(rs.getInt(1));
				article.setUserid(rs.getInt(2));
				article.setArticletitle(rs.getString(3));
				article.setArticlecont(rs.getString(4));
				article.setPublishtime(rs.getString(5));
				article.setLikecount(rs.getInt(6));
				article.setDislikecount(rs.getInt(7));
				
				User user = new User();
				user.setUsername(rs.getString(8));
				user.setPhoto(rs.getString(9));
				user.setUserid(rs.getInt(10));
				article.setUser(user);
				list.add(article);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean UpdateArticle(int articleid, int like, int dislike) {
		// TODO Auto-generated method stub
		String sql = "update article set likecount = likecount + ?, dislikecount = dislikecount + ? where articleid = ?";
		return dbManager.execUpdate(sql, like, dislike, articleid) > 0;
	}

	@Override
	public List<Article> findTopic(String topic) {
		// TODO Auto-generated method stub
		String sql = "select article.*,username,photo,user.userid from user,article where user.userid = article.userid and articlecont like ? "
				         +  "order by publishTime desc";
		
		ResultSet rs = dbManager.execQuery(sql, "%" + topic + "%");
		List<Article> list = new ArrayList<Article>();
		try {
			while(rs.next()){							
				Article article = new Article();
				article.setArticleid(rs.getInt(1));
				article.setUserid(rs.getInt(2));
				article.setArticletitle(rs.getString(3));
				article.setArticlecont(rs.getString(4));
				article.setPublishtime(rs.getString(5));
				article.setLikecount(rs.getInt(6));
				article.setDislikecount(rs.getInt(7));
				
				User user = new User();
				user.setUsername(rs.getString(8));
				user.setPhoto(rs.getString(9));
				user.setUserid(rs.getInt(10));
				article.setUser(user);
				list.add(article);
			}			
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean collectArticle(int userid, int articleid) {
		// TODO Auto-generated method stub
		String sql1 = "select * from collection where userid = ? and articleid = ?";
		ResultSet rs = dbManager.execQuery(sql1, userid, articleid);
		try {
			if(!rs.next()){
				String sql2 = "insert into collection values(null, ?, ?, now())";
				return dbManager.execUpdate(sql2, userid, articleid) > 0;
			}else
				return false;
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
