package com.etc.dao;

import java.util.List;

import com.etc.entity.ChatMessage;
import com.etc.entity.User;

public interface ChatDAO {
	
	//查询聊天列表
	List<ChatMessage> findChatList(int fromUserid);
	
	//查询聊天记录
	List<ChatMessage> findChatRecord(int fromUserid, int toUserid);
	
	//新增聊天记录 
	boolean addChat(ChatMessage chat);
	
	//删除聊天记录 
	boolean deleteChat(ChatMessage chat);
	
	//删除所有聊天记录
	boolean deleteAllChat(ChatMessage chat);
}
