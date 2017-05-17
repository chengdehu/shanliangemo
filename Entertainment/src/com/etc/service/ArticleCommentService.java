package com.etc.service;

import java.util.List;

import com.etc.entity.ArticleComment;

public interface ArticleCommentService {
	
	boolean writeArtiComm(ArticleComment comment);
	
	List<ArticleComment> findArtiCommList(int articleid);
}
