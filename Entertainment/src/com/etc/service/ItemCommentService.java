package com.etc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dbutil.DBManager;
import com.etc.entity.ItemComment;

public class ItemCommentService {
	DBManager manager = new DBManager();
	public List<ItemComment> getItemComment (){
		String sql = "SELECT itemcomment.comment_cont , item_cont.cont_infor from itemcomment , item_cont where itemcomment.itemid = item_cont.itemid";
		ResultSet rst = manager.execQuery(sql);
		List<ItemComment> coms = new ArrayList<ItemComment>();
		try{
			while(rst.next()){
				ItemComment com = new ItemComment();
				/*
				com.setComment_id(rst.getInt(1));
				com.setUser_id(rst.getInt(2));
				com.setItem_id(rst.getInt(3));
				com.setComment_cont(rst.getString(4));			
				*/
				com.setComment_cont(rst.getString(1));
				com.setCont_info(rst.getString(2));
				coms.add(com);
				
			}
			return coms;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}

