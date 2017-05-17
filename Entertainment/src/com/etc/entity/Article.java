package com.etc.entity;

import java.io.Serializable;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int articleid;
	private int userid;
	private User user;
	private String articletitle;
	private String articlecont;
	private String publishtime;
	private int likecount;
	private int dislikecount;
	
	public int getArticleid() {
		return articleid;
	}
	
	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getArticletitle() {
		return articletitle;
	}
	
	public void setArticletitle(String articletitle) {
		this.articletitle = articletitle;
	}
	
	public String getArticlecont() {
		return articlecont;
	}
	
	public void setArticlecont(String articlecont) {
		this.articlecont = articlecont;
	}
	
	public String getPublishtime() {
		return publishtime;
	}
	
	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}
	
	public int getLikecount() {
		return likecount;
	}
	
	public void setLikecount(int likecount) {
		this.likecount = likecount;
	}
	
	public int getDislikecount() {
		return dislikecount;
	}
	
	public void setDislikecount(int dislikecount) {
		this.dislikecount = dislikecount;
	}

}
