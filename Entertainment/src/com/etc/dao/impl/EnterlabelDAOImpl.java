package com.etc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etc.dao.EnterlabelDAO;
import com.etc.dbutil.DBManager;
import com.etc.entity.Enterlabel;

public class EnterlabelDAOImpl implements EnterlabelDAO{
	private DBManager dbManager = new DBManager();

	@Override
	public List<Enterlabel> findalllabel() {
		String sql = "select * from enterlabel";
		ResultSet rs = dbManager.execQuery(sql);
		List<Enterlabel> list = new ArrayList<Enterlabel>();
		try {
			while(rs.next()){
				Enterlabel enterlabel = new Enterlabel();
				enterlabel.setItemid(rs.getInt(1));
				enterlabel.setLabel_name(rs.getString(2));
				list.add(enterlabel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			dbManager.closeConnection();
		}
		return list;
	}

	@Override
	public List<Enterlabel> findtypelabel(String type) {
		String sql = "select distinct label_name from enterlabel,entertainmentitem where itemtype = ? and enterlabel.itemid = entertainmentitem.itemid ";
		ResultSet rs = dbManager.execQuery(sql,type);// TODO Auto-generated method stub
		List<Enterlabel> list = new ArrayList<Enterlabel>();
		try {
			while(rs.next()){
				Enterlabel enterlabel = new Enterlabel();
				enterlabel.setLabel_name(rs.getString(1));
				list.add(enterlabel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			dbManager.closeConnection();
		}
		return list;
	}

}
