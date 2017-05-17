package com.etc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dbutil.DBManager;
import com.etc.entity.MyArticleCollection;


public class MyArticleCollectionService {
	DBManager manager = new DBManager();
	public List<MyArticleCollection> getMyArticleCollection (){
		String sql = "SELECT a.articletitle ,b.colle_time from article a , collection b where a.articleid = b.articleid";
		ResultSet rst = manager.execQuery(sql);
		List<MyArticleCollection> colls = new ArrayList<MyArticleCollection>();
		try{
			while(rst.next()){
				MyArticleCollection coll = new MyArticleCollection();
				//coll.setColle_id(rst.getInt(1));
				//coll.setUserid(rst.getInt(2));
				//coll.setArticleid(rst.getInt(3));
				//coll.setColle_time(rst.getString(4));
				coll.setArticletitle(rst.getString(1));
				coll.setColle_time(rst.getString(2));
				
				colls.add(coll);
			}
			return colls;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
