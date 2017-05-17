package com.etc.service.impl;

import java.util.List;

import com.etc.dao.ArticleCommentDAO;
import com.etc.dao.impl.ArticleCommentDAOimpl;
import com.etc.entity.ArticleComment;
import com.etc.service.ArticleCommentService;

public class ArticleCommentServiceImpl implements ArticleCommentService {

	private ArticleCommentDAO commentdao = new ArticleCommentDAOimpl();
	@Override
	public boolean writeArtiComm(ArticleComment comment) {
		// TODO Auto-generated method stub
		return commentdao.writeArtiComm(comment);
	}
	
	@Override
	public List<ArticleComment> findArtiCommList(int articleid) {
		// TODO Auto-generated method stub
		return commentdao.findArtiCommList(articleid);
	}

}
