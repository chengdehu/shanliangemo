package com.etc.dao;

import java.util.List;

import com.etc.entity.ArticleComment;

public interface ArticleCommentDAO {
	
	boolean writeArtiComm(ArticleComment comment);
	
	List<ArticleComment> findArtiCommList(int articleid);
}
