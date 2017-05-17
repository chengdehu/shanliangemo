package com.etc.service;

import java.util.List;

import com.etc.entity.ChatMessage;

public interface ChatService {
	//获取聊天列表
	List<ChatMessage> getChatList(int fromUserid);
	
	//获取聊天记录
	List<ChatMessage> getChatRecord(int fromUserid, int toUserid);
	
	//添加聊天记录
	ChatMessage addChat(ChatMessage chat);
	
	//删除聊天记录
	ChatMessage deleteChat(ChatMessage chat);
	
	//删除所有聊天记录
	ChatMessage deleteAllChat(ChatMessage chat);
}
