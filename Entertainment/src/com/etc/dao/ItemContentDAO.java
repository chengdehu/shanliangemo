package com.etc.dao;


import java.util.List;

import com.etc.entity.Itemcontent;

public interface ItemContentDAO {
	List<Itemcontent> findcontent(int itemid);
	Itemcontent findwords(int itemid);
	Itemcontent findimage(int itemid);
	Itemcontent findvideo(int itemid);
	

}
