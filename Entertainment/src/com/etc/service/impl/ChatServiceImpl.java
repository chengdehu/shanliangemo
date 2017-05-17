package com.etc.service.impl;

import java.util.List;

import com.etc.dao.ChatDAO;
import com.etc.dao.impl.ChatDAOImpl;
import com.etc.entity.ChatMessage;
import com.etc.entity.User;
import com.etc.service.ChatService;
import com.etc.util.StringUtil;

public class ChatServiceImpl implements ChatService {
	
	private ChatDAO chatDAO = new ChatDAOImpl();

	public List<ChatMessage> getChatList(int fromUserid) {
		List<ChatMessage> list = chatDAO.findChatList(fromUserid);
		
		//业务层中负责对DAO层返回的原始数据进行加工处理	
		for(ChatMessage chat : list){
			chat.setTime(StringUtil.convertDatetime(chat.getTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		return list;
	}

	public List<ChatMessage> getChatRecord(int fromUserid, int toUserid) {
		List<ChatMessage> list = chatDAO.findChatRecord(fromUserid,toUserid);
		//业务层中负责对DAO层返回的原始数据进行加工处理	
		for(ChatMessage chat : list){
			chat.setTime(StringUtil.convertDatetime(chat.getTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		return list;
	}
	
	//添加聊天记录
	public ChatMessage addChat(ChatMessage chat){
		if(chatDAO.addChat(chat)){
			return chat;
		}
		return null;
	}

	//删除聊天记录
	public ChatMessage deleteChat(ChatMessage chat) {
		if(chatDAO.deleteChat(chat)){
			//chat = chatDAO.findChatList(fromUserid);
			return chat;
		}
		return null;
	}

	public ChatMessage deleteAllChat(ChatMessage chat) {
		if(chatDAO.deleteAllChat(chat)){
			return chat;
		}
		return null;
	}
}
