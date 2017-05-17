package com.etc.entity;

import java.io.Serializable;

public class ChatMessage implements Serializable {
	private User user;

	private int fromUserid;

	private int toUserid;
	// 日期
	private String time;
	// 聊天内容
	private String message;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getfromUserid() {
		return fromUserid;
	}

	public void setfromUserid(int fromUserid) {
		this.fromUserid = fromUserid;
	}

	public int gettoUserid() {
		return toUserid;
	}

	public void settoUserid(int toUserid) {
		this.toUserid = toUserid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
