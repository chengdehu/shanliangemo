package com.etc.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.etc.dao.EntertainmentDAO;
import com.etc.dao.impl.EntertainmentDAOImpl;
import com.etc.entity.Entertainment;
import com.etc.service.EntertainmentService;

public class EntertainmentServiceImpl implements EntertainmentService {

	EntertainmentDAO entertainmentDAO=new EntertainmentDAOImpl();;
	@Override
	public List<Entertainment> findCategory(int category) {
		
		return entertainmentDAO.FindCategory(category);
	}
	public List<Entertainment> getAllNews(){

		List<Entertainment> list = entertainmentDAO.findNewsList();
		return list;
		
	}
	public List<Entertainment> getNewsList(int itemid){
		List<Entertainment> list = entertainmentDAO.findNewsList(itemid);
		return list;
		
	}
	public List<Entertainment> getNews(){
		List<Entertainment> list = entertainmentDAO.findAllNews();
		return list;
	}
	public List<Entertainment> getNewsType(String itemtype){
		List<Entertainment> list = entertainmentDAO.findNewsType(itemtype);
		return list;
	}
	@Override
	public boolean updatecount(int itemid) {
		// TODO Auto-generated method stub
		return entertainmentDAO.updateLike(itemid);
	}
	@Override
	public List<Entertainment> getfoundnews(String words) {
		List<Entertainment> list = entertainmentDAO.findnews(words);// TODO Auto-generated method stub
		return list;
	}
	@Override
	public boolean updatereadcount(int itemid) {
		// TODO Auto-generated method stub
		return entertainmentDAO.updatecount(itemid);
	}

	@Override
	public List<Entertainment> getfoundnewsbylabel(String itemtype,
			String label_name) {
		List<Entertainment> list = entertainmentDAO.findnewsbylabel(itemtype, label_name);// TODO Auto-generated method stub
		return list;
	}

	@Override
	public List<String> findItemLabel(int itemid) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list = entertainmentDAO.findItemLabel(itemid);
		return list;
	}
}
