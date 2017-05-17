package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





import com.etc.dao.EntertainmentDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.Entertainment;


public class EntertainmentDAOImpl implements EntertainmentDAO {

	DBManager dbManager=new DBManager();
	@Override
	public List<Entertainment> FindCategory(int category) {
		
		List<Entertainment> datalist = new ArrayList<Entertainment>();
		String sql="select * from entertainmentitem where itemtype= ?"; 
		String type = null;
		System.out.println(category+"aaaaaaaaaaaaaaaa");
		switch(category)
		{
		case 1:
			type="ani";
			break;
		case 2:
			type="mov";
			break;
		case 3:
			type="gam";
			break;
		case 4:
			type="nov";
			break;
		case 5:
			type="mus";
			break;
		case 6:
			type="sta";
			break;
		case 7:
			type="spo";
			break;
		case 8:
			type="foo";
			break;
		case 9:
			type="vid";
			break;
		}
		
		ResultSet rs=dbManager.execQuery(sql,type);
		try {
			while(rs.next())
			{
				Entertainment entertainment=new Entertainment();
				entertainment.setItemid(rs.getInt(1));
				
				entertainment.setItemtype(rs.getString(2));
				entertainment.setIteminfor(rs.getString(3));
				entertainment.setItemcount(rs.getInt(4));
				entertainment.setItemlikes(rs.getInt(5));
				entertainment.setItemimage(rs.getString(7));
				datalist.add(entertainment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dbManager.closeConnection();
			}
		return datalist;
	}
	
public Entertainment findnews(int itemid) {
		
		String sql = "select * from entertainmentitem where itemid= ?";
		
		ResultSet rs = dbManager.execQuery(sql, itemid);
		
		try {
			while(rs.next()){   //找到
				
				Entertainment entertainment = new Entertainment();//创建并填充实体bean
				entertainment.setItemid(itemid);
				entertainment.setItemtype(rs.getString(2));
				entertainment.setIteminfor(rs.getString(3));
				entertainment.setItemcount(rs.getInt(4));
				entertainment.setItemlikes(rs.getInt(5));
				entertainment.setDatetime(rs.getString(6));
				entertainment.setItemimage(rs.getString(7));
				entertainment.setItemtitle(rs.getString(8));
				
				
				return entertainment;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			
		} finally{
			dbManager.closeConnection();
		}
		
		return null;
		
	}
	
	public List<Entertainment> findNewsList(){
       String sql = "SELECT * FROM entertainmentitem";
		
		ResultSet rs = dbManager.execQuery(sql);
		List<Entertainment> list = new ArrayList<Entertainment>();
		
		try {
			while(rs.next()){   //找到
				Entertainment entertainment = new Entertainment();//创建并填充实体bean
				entertainment.setItemid(rs.getInt(1));
				entertainment.setItemtype(rs.getString(2));
				entertainment.setIteminfor(rs.getString(3));
				entertainment.setItemcount(rs.getInt(4));
				entertainment.setItemlikes(rs.getInt(5));
				entertainment.setDatetime(rs.getString(6));
				entertainment.setItemimage(rs.getString(7));
				entertainment.setItemtitle(rs.getString(8));
				
				list.add(entertainment);
					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally{
			dbManager.closeConnection();
		}
		return list;
		
		
		
	}
	public List<Entertainment> findAllNews(){
	       String sql = "SELECT * FROM entertainmentitem order by itemcount";
			
			ResultSet rs = dbManager.execQuery(sql);
			List<Entertainment> list = new ArrayList<Entertainment>();
			
			try {
				while(rs.next()){   //找到
					Entertainment entertainment = new Entertainment();//创建并填充实体bean
					entertainment.setItemid(rs.getInt(1));
					entertainment.setItemtype(rs.getString(2));
					entertainment.setIteminfor(rs.getString(3));
					entertainment.setItemcount(rs.getInt(4));
					entertainment.setItemlikes(rs.getInt(5));
					entertainment.setDatetime(rs.getString(6));
					entertainment.setItemimage(rs.getString(7));
					entertainment.setItemtitle(rs.getString(8));
					
					list.add(entertainment);
						
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
				
			} finally{
				dbManager.closeConnection();
			}
			return list;
		
	}
	
	public List<Entertainment> findNewsList(int itemid){
        String sql = "select * from entertainmentitem where itemid= ?";
		
		ResultSet rs = dbManager.execQuery(sql, itemid);
         List<Entertainment> list = new ArrayList<Entertainment>();
		
		try {
			if(rs.next()){   //找到
				
				Entertainment entertainment = new Entertainment();//创建并填充实体bean
				entertainment.setItemid(rs.getInt(1));
				entertainment.setItemtype(rs.getString(2));
				entertainment.setIteminfor(rs.getString(3));
				entertainment.setItemcount(rs.getInt(4));
				entertainment.setItemlikes(rs.getInt(5));
				entertainment.setDatetime(rs.getString(6));
				entertainment.setItemimage(rs.getString(7));
				entertainment.setItemtitle(rs.getString(8));
				
				list.add(entertainment);
				
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally{
			dbManager.closeConnection();
		}
		
		return list;
		
	}

	@Override
	public List<Entertainment> findNewsType(String itemtype) {
		  String sql = "SELECT * FROM entertainmentitem where itemtype = ?";
			
			ResultSet rs = dbManager.execQuery(sql,itemtype);
			List<Entertainment> list = new ArrayList<Entertainment>();
			
			try {
				while(rs.next()){   //找到
					Entertainment entertainment = new Entertainment();//创建并填充实体bean
					entertainment.setItemid(rs.getInt(1));
					entertainment.setItemtype(rs.getString(2));
					entertainment.setIteminfor(rs.getString(3));
					entertainment.setItemcount(rs.getInt(4));
					entertainment.setItemlikes(rs.getInt(5));
					entertainment.setDatetime(rs.getString(6));
					entertainment.setItemimage(rs.getString(7));
					entertainment.setItemtitle(rs.getString(8));
					
					list.add(entertainment);
						
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
				
			} finally{
				dbManager.closeConnection();
			}
			return list;
	}

	@Override
	public boolean updateLike(int itemid) {
		String sql = "update entertainmentitem set itemlikes = itemlikes +1 where itemid = ?";
		int rs = dbManager.execUpdate(sql,itemid);
		if(rs>0){
		return true;
		}else{
		return false;
		}
	}

	@Override
	public List<Entertainment> findnews(String words) {
		 String sql = "SELECT * FROM entertainmentitem where iteminfor like ? or itemtitle like ?";
		ResultSet rs = dbManager.execQuery(sql, "%"+words+"%","%"+words+"%"); 
		List<Entertainment> list = new ArrayList<Entertainment>();
		
		try {
			while(rs.next()){   //找到
				Entertainment entertainment = new Entertainment();//创建并填充实体bean
				entertainment.setItemid(rs.getInt(1));
				entertainment.setItemtype(rs.getString(2));
				entertainment.setIteminfor(rs.getString(3));
				entertainment.setItemcount(rs.getInt(4));
				entertainment.setItemlikes(rs.getInt(5));
				entertainment.setDatetime(rs.getString(6));
				entertainment.setItemimage(rs.getString(7));
				entertainment.setItemtitle(rs.getString(8));
				
				list.add(entertainment);
					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally{
			dbManager.closeConnection();
		}
		return list;// TODO Auto-generated method stub
	}

	@Override
	public boolean updatecount(int itemid) {
		String sql = "update entertainmentitem set itemcount = itemcount +1 where itemid = ?";
		int rs = dbManager.execUpdate(sql,itemid);
		if(rs>0){
		return true;
		}else{
		return false;
		}
	}
	@Override
	public List<Entertainment> findnewsbylabel(String itemtype,
			String label_name) {
		String sql = "SELECT * FROM entertainmentitem,enterlabel where itemtype = ? and label_name = ? and enterlabel.itemid = entertainmentitem.itemid";
		ResultSet rs = dbManager.execQuery(sql, itemtype,label_name); 
		List<Entertainment> list = new ArrayList<Entertainment>();
		
		try {
			while(rs.next()){   //找到
				Entertainment entertainment = new Entertainment();//创建并填充实体bean
				entertainment.setItemid(rs.getInt(1));
				entertainment.setItemtype(rs.getString(2));
				entertainment.setIteminfor(rs.getString(3));
				entertainment.setItemcount(rs.getInt(4));
				entertainment.setItemlikes(rs.getInt(5));
				entertainment.setDatetime(rs.getString(6));
				entertainment.setItemimage(rs.getString(7));
				entertainment.setItemtitle(rs.getString(8));
				
				list.add(entertainment);
					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally{
			dbManager.closeConnection();
		}
		return list;// TODO Auto-generated method stub
	}

	@Override
	public List<String> findItemLabel(int itemid) {
		// TODO Auto-generated method stub
		String sql = "select label_name from enterlabel where itemid = ?;";
		ResultSet rs = dbManager.execQuery(sql,itemid);
		List<String> list = new ArrayList<String>();
		try {
			while(rs.next()){							
				String label = rs.getString(1);
				list.add(label);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
