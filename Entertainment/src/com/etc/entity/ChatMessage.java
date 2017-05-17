package com.etc.entity;

import java.io.Serializable;

public class ChatMessage implements Serializable{
    private static final String TAG = ChatMessage.class.getSimpleName();
    private int fromUserid;
    
    private int toUserid;
    //名字
    private String name;
    //日期
    private String time;
    //聊天内容
    private String message;
    
    private User user;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String date) {
        this.time = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public User getUser(){
    	return user;
    }
    
    public void setUser(User user){
    	this.user = user;
    }
}
