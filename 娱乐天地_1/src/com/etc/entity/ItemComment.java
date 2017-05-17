package com.etc.entity;

import java.io.Serializable;

public class ItemComment implements Serializable {
	private static final long serialVersionUID = 1L;
	//å±æ?ç§æœ‰åŒ?
	private int comment_id;
	private int user_id;
	private int item_id;
	private String comment_cont;
    private String Cont_info;
	public String getCont_info() {
		return Cont_info;
	}
	public void setCont_info(String cont_info) {
		Cont_info = cont_info;
	}
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	//getter/setter
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getComment_cont() {
		return comment_cont;
	}
	public void setComment_cont(String comment_cont) {
		this.comment_cont = comment_cont;
	}
	@Override
	public String toString() {
		return "ItemComment [comment_id=" + comment_id + ", user_id=" + user_id
				+ ", item_id=" + item_id + ", comment_cont=" + comment_cont
				+ ", user=" + user + "]";
	}

	
	
}
