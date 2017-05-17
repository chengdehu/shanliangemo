package com.etc.service;

import java.util.List;

import com.etc.entity.Entertainment;

public interface EntertainmentService {
	List<Entertainment> findCategory(int category);

	List<Entertainment> getAllNews();
	List<Entertainment> getNewsList(int itemid);
	List<Entertainment> getNews();
	List<Entertainment> getNewsType(String itemtype);
	boolean updatecount(int itemid);
	List<Entertainment> getfoundnews(String words);
	boolean updatereadcount(int itemid);
	
	List<Entertainment> getfoundnewsbylabel(String itemtype,String label_name);

	List<String> findItemLabel(int itemid);
}
