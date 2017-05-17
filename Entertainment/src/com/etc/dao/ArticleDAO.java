package com.etc.dao;

import java.util.List;

import com.etc.entity.Article;

public interface ArticleDAO {
	Article findArticle(int articleid);
	
	//д����
	boolean writeArticle(Article article);
	
	boolean deleteArticle(int articleid);
	
	List<Article> findArticleList(String count);
	
	List<Article> findArticleList();
	
	List<Article> findArticleList(int userid);
	
	List<Article> findTopic(String topic);
	
	boolean collectArticle(int userid,int articleid);
	
	boolean UpdateArticle(int articleid, int like, int dislike);
	
}
