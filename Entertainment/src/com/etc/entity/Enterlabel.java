package com.etc.entity;

import java.io.Serializable;

public class Enterlabel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int itemid;	
	private String label_name;
	@Override
	public String toString() {
		return "Enterlabel [itemid=" + itemid + ", label_name=" + label_name
				+ "]";
	}
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public String getLabel_name() {
		return label_name;
	}
	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}

}
