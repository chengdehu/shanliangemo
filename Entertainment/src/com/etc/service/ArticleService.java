package com.etc.service;

import java.util.List;

import com.etc.entity.Article;

public interface ArticleService {
	Article findArticle(int articleid);
	
	boolean writeArticle(Article article);
	
	boolean deleteArticle(int articleid);
	
	List<Article> findArticleList();
	
	List<Article> findArticleList(int userid);
	
	List<Article> findTopic(String topic);

	List<Article> findArticleList(String count);
	
	boolean UpdateArticle(int articleid, int like, int dislike);
	
	boolean collectArticle(int userid, int articleid);
}
