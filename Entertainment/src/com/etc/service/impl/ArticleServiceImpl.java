package com.etc.service.impl;

import java.util.List;

import com.etc.dao.ArticleDAO;
import com.etc.dao.impl.ArticleDAOimpl;
import com.etc.entity.Article;
import com.etc.service.ArticleService;

public class ArticleServiceImpl implements ArticleService {
	
	private ArticleDAO articledao = new ArticleDAOimpl();
	@Override
	public Article findArticle(int articleid) {
		// TODO Auto-generated method stub
		Article article = articledao.findArticle(articleid);
		return article;

	}

	@Override
	public boolean writeArticle(Article article) {
		// TODO Auto-generated method stub
		return articledao.writeArticle(article);

	}

	@Override
	public List<Article> findArticleList(String count) {
		// TODO Auto-generated method stub
		List<Article> list = articledao.findArticleList(count);		
		return list;
	}

	public boolean deleteArticle(int articleid){
		return articledao.deleteArticle(articleid);
	}
	
	@Override
	public List<Article> findArticleList() {
		// TODO Auto-generated method stub
		List<Article> list = articledao.findArticleList();		
		return list;
	}

	@Override
	public List<Article> findArticleList(int userid) {
		// TODO Auto-generated method stub
		List<Article> list = articledao.findArticleList(userid);		
		return list;
	}

	@Override
	public boolean UpdateArticle(int articleid, int like, int dislike) {
		// TODO Auto-generated method stub
		return articledao.UpdateArticle(articleid, like, dislike);
	}

	@Override
	public List<Article> findTopic(String topic) {
		// TODO Auto-generated method stub
		List<Article> list = articledao.findTopic(topic);	
		return list;
	}

	@Override
	public boolean collectArticle(int userid, int articleid) {
		// TODO Auto-generated method stub
		return articledao.collectArticle(userid, articleid);
	}

}
