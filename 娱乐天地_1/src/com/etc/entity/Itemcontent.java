package com.etc.entity;

import java.io.Serializable;

public class Itemcontent implements Serializable{
	private static final long serialVersionUID = 1L;
	int contid;
	int itemid;
	int count_type;
	String count_infor;
	public int getContid() {
		return contid;
	}
	public void setContid(int contid) {
		this.contid = contid;
	}
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public int getCount_type() {
		return count_type;
	}
	public void setCount_type(int count_type) {
		this.count_type = count_type;
	}
	public String getCount_infor() {
		return count_infor;
	}
	public void setCount_infor(String count_infor) {
		this.count_infor = count_infor;
	}
	@Override
	public String toString() {
		return "Itemcontent [contid=" + contid + ", itemid=" + itemid
				+ ", count_type=" + count_type + ", count_infor=" + count_infor
				+ "]";
	}

}
