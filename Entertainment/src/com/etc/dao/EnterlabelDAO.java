package com.etc.dao;

import java.util.List;

import com.etc.entity.Enterlabel;

public interface EnterlabelDAO {
	List<Enterlabel> findalllabel();
	List<Enterlabel> findtypelabel(String type);

}
