package com.etc.dao;

import java.util.List;

import com.etc.entity.Entertainment;

public interface EntertainmentDAO {
	List<Entertainment> FindCategory(int category);

	
	Entertainment findnews(int itemid);
	List<Entertainment> findNewsList();
	List<Entertainment> findAllNews();
	List<Entertainment> findNewsList(int itemid);
	List<Entertainment> findNewsType(String itemtype);
	boolean updateLike(int itemid);
	List<Entertainment> findnews(String words);
	boolean updatecount(int itemid);
	List<Entertainment> findnewsbylabel(String itemtype,String label_name);
	
	List<String> findItemLabel(int itemid);
	
}
