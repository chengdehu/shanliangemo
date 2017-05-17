package com.etc.entity;

import java.io.Serializable;


public class Entertainment implements Serializable{
	private static final long serialVersionUID = 1L;
	private int itemid;
	private String itemtype;
	private String iteminfor;
	private int itemcount;
	private int itemlikes;
	private String datetime;
	private String itemimage;
	private String itemtitle;
	
	
	
	@Override
	public String toString() {
		return "Entertainment [itemid=" + itemid + ", itemtype=" + itemtype
				+ ", iteminfor=" + iteminfor + ", itemcount=" + itemcount
				+ ", itemlikes=" + itemlikes + ", datetime=" + datetime
				+ ", itemimage=" + itemimage + ", itemtitle=" + itemtitle + "]";
	}
	public String getItemtitle() {
		return itemtitle;
	}
	public void setItemtitle(String itemtitle) {
		this.itemtitle = itemtitle;
	}
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public String getItemtype() {
		return itemtype;
	}
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}
	public String getIteminfor() {
		return iteminfor;
	}
	public void setIteminfor(String iteminfor) {
		this.iteminfor = iteminfor;
	}
	public int getItemcount() {
		return itemcount;
	}
	public void setItemcount(int itemcount) {
		this.itemcount = itemcount;
	}
	public int getItemlikes() {
		return itemlikes;
	}
	public void setItemlikes(int itemlikes) {
		this.itemlikes = itemlikes;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getItemimage() {
		return itemimage;
	}
	public void setItemimage(String itemimage) {
		this.itemimage = itemimage;
	}


}
