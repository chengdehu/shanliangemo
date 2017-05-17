package com.etc.entity;

import java.io.Serializable;

public class ArticleCollection implements Serializable {

	private static final long serialVersionUID = 1L;
	private int articleid;
	private String aname;
	private String description;
	public int getArticleid() {
		return articleid;
	}
	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ArticalCollection [articleid=" + articleid + ", aname=" + aname
				+ ", description=" + description + "]";
	}
	
}
