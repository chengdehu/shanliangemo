package com.etc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dbutil.DBManager;
import com.etc.entity.ArticleCollection;


public class ArticleCollectionService {
	DBManager manager = new DBManager();
	public List<ArticleCollection> getArticleCollection (int lastid,int pagesize){
		String sql = "select * from articlecollection where articleid>? limit 0,?";
		ResultSet rst = manager.execQuery(sql, lastid,pagesize);
		List<ArticleCollection> colls = new ArrayList<ArticleCollection>();
		try{
			while(rst.next()){
				ArticleCollection coll = new ArticleCollection();
				coll.setArticleid(rst.getInt(1));
				coll.setAname(rst.getString(2));
				coll.setDescription(rst.getString(3));
				colls.add(coll);
			}
			return colls;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
