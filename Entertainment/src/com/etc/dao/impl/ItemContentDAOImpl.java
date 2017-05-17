package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dao.ItemContentDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.Itemcontent;

public class ItemContentDAOImpl implements ItemContentDAO{
	
	private DBManager dbManager = new DBManager();
	public List<Itemcontent> findcontent(int itemid){
        String sql = "select * from item_cont where itemid= ?";
		
		ResultSet rs = dbManager.execQuery(sql, itemid);
		List<Itemcontent> list = new ArrayList<Itemcontent>();
		try {
			while(rs.next()){   //找到
				
				Itemcontent itemcontent = new Itemcontent();//创建并填充实体bean
				itemcontent.setItemid(itemid);
				itemcontent.setContid(rs.getInt(1));
				itemcontent.setCount_type(rs.getInt(3));
				itemcontent.setCount_infor(rs.getString(4));
				list.add(itemcontent);				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally{
			dbManager.closeConnection();
		}
		return list;

	}
		public Itemcontent findwords(int itemid){
			String sql = "select * from item_cont where itemid= ? and cont_type = 1";
			
			ResultSet rs = dbManager.execQuery(sql, itemid);
			try {
				if(rs.next()){   //找到
					
					Itemcontent itemcontent = new Itemcontent();//创建并填充实体bean
					itemcontent.setItemid(itemid);
					itemcontent.setContid(rs.getInt(1));
					itemcontent.setCount_type(rs.getInt(3));
					itemcontent.setCount_infor(rs.getString(4));
					
					return itemcontent;
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				
			} finally{
				dbManager.closeConnection();
			}
			
			return null;
		}
		public Itemcontent findimage(int itemid){
			String sql = "select * from item_cont where itemid= ? and cont_type = 2";
			
			ResultSet rs = dbManager.execQuery(sql, itemid);
			try {
				if(rs.next()){   //找到
					
					Itemcontent itemcontent = new Itemcontent();//创建并填充实体bean
					itemcontent.setItemid(itemid);
					itemcontent.setContid(rs.getInt(1));
					itemcontent.setCount_type(rs.getInt(3));
					itemcontent.setCount_infor(rs.getString(4));
					
					return itemcontent;
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				
			} finally{
				dbManager.closeConnection();
			}
			
			return null;
		}
		public Itemcontent findvideo(int itemid){
			String sql = "select * from item_cont where itemid= ? and cont_type = 3";
			
			ResultSet rs = dbManager.execQuery(sql, itemid);
			try {
				if(rs.next()){   //找到
					
					Itemcontent itemcontent = new Itemcontent();//创建并填充实体bean
					itemcontent.setItemid(itemid);
					itemcontent.setContid(rs.getInt(1));
					itemcontent.setCount_type(rs.getInt(3));
					itemcontent.setCount_infor(rs.getString(4));
					
					return itemcontent;
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				
			} finally{
				dbManager.closeConnection();
			}
			
			return null;
		}

		
	}
	

