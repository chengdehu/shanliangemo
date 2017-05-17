package com.etc.service;

import java.util.List;

import com.etc.entity.Itemcontent;

public interface ItemcontentService {
	List<Itemcontent> getcontent(int itemid);
	Itemcontent getwords(int itemid);
	Itemcontent getimages(int itemid);
	Itemcontent getvideo(int itemid);

}
