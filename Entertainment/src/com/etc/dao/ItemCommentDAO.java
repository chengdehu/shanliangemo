package com.etc.dao;

import java.util.List;

import com.etc.entity.ItemComment;

public interface ItemCommentDAO {
     boolean writeItemComm(ItemComment comment);
	
	List<ItemComment> findItemCommList(int itemid);

}
