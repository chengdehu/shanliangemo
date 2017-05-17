package com.etc.service;

import java.util.List;

import com.etc.entity.ItemComment;

public interface ItemCommentService_h {
    boolean writeItemComm(ItemComment comment);
	
	List<ItemComment> findItemCommList(int itemid);
}
