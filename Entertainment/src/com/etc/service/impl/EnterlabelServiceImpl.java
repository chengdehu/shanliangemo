package com.etc.service.impl;

import java.util.List;

import com.etc.dao.EnterlabelDAO;
import com.etc.dao.impl.EnterlabelDAOImpl;
import com.etc.entity.Enterlabel;
import com.etc.service.EnterlabelService;

public class EnterlabelServiceImpl implements EnterlabelService {
	private EnterlabelDAO enterlabelDAO = new EnterlabelDAOImpl(); 

	public List<Enterlabel> getlabel(String itemtype) {
		List<Enterlabel> list = enterlabelDAO.findtypelabel(itemtype);// TODO Auto-generated method stub
		return list;
	}

}
