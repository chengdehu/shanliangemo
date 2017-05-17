package com.etc.service.impl;


import java.util.List;

import com.etc.dao.ItemContentDAO;
import com.etc.dao.impl.ItemContentDAOImpl;
import com.etc.entity.Itemcontent;
import com.etc.service.ItemcontentService;

public class ItemcontentServiceImpl implements ItemcontentService{
	private ItemContentDAO itemcontentDAO = new ItemContentDAOImpl();
	public Itemcontent getwords(int itemid){
		Itemcontent itemcontent = itemcontentDAO.findwords(itemid);
		return itemcontent;
	}
	public Itemcontent getimages(int itemid){
		Itemcontent itemcontent = itemcontentDAO.findimage(itemid);
		return itemcontent;
	}
	public Itemcontent getvideo(int itemid){
		Itemcontent itemcontent = itemcontentDAO.findvideo(itemid);
		return itemcontent;
	}
	@Override
	public List<Itemcontent> getcontent(int itemid) {
		List<Itemcontent> list = itemcontentDAO.findcontent(itemid);// TODO Auto-generated method stub
		return list;
	}
}
