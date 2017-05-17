package com.etc.entity;

import java.io.Serializable;

public class MyArticleCollection implements Serializable {

	private static final long serialVersionUID = 1L;
	//private int colle_id; 
	//private int userid;
	//private int articleid;
	private String articletitle;
	private String colle_time;
	/*public int getColle_id() {
		return colle_id;
	}
	public void setColle_id(int colle_id) {
		this.colle_id = colle_id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getArticleid() {
		return articleid;
	}
	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}
	*/
	public String getColle_time() {
		return colle_time;
	}
	public void setColle_time(String colle_time) {
		this.colle_time = colle_time;
	}
	
	public String getArticletitle() {
		return articletitle;
	}
	public void setArticletitle(String articletitle) {
		this.articletitle = articletitle;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/*@Override
	public String toString() {
		return "MyArticleCollection [colle_id=" + colle_id + ", userid="
				+ userid + ", articleid=" + articleid + ", colle_time="
				+ colle_time + "]";
	}
	*/
	public String toString() {
		return "MyArticleCollection [ articletitle="+articletitle+",colle_time="
				+ colle_time + "]";
	}
}
