package com.etc.service.impl;

import java.util.List;

import com.etc.dao.ItemCommentDAO;
import com.etc.dao.impl.ItemCommentDAOImpl;
import com.etc.entity.ItemComment;
import com.etc.service.ItemCommentService_h;

public class ItemCommentServiceImpl_h implements ItemCommentService_h {
	private ItemCommentDAO commentdao = new ItemCommentDAOImpl();
	@Override
	public boolean writeItemComm(ItemComment comment) {
		// TODO Auto-generated method stub
		return commentdao.writeItemComm(comment);
	}

	@Override
	public List<ItemComment> findItemCommList(int itemid) {
		// TODO Auto-generated method stub
		return commentdao.findItemCommList(itemid);
	}

}
