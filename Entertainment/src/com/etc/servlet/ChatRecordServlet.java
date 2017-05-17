package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.entity.ChatMessage;
import com.etc.service.ChatService;
import com.etc.service.impl.ChatServiceImpl;
import com.google.gson.Gson;

public class ChatRecordServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//设置输出内容类型
		response.setContentType("text/html;charset=utf-8");	

		//获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();	   	

		//获取session对象---需要session对象时加此句
	    HttpSession session = request.getSession();	

		//设置字符编码---需要字符转码时加此句
		request.setCharacterEncoding("utf-8");
		
		//接收数据
		
		int fromUserid = Integer.parseInt(request.getParameter("fromUserid"));
		int toUserid = Integer.parseInt(request.getParameter("toUserid"));
		
		System.out.println("fromUserid="+fromUserid);
		System.out.println("toUserid="+toUserid);
		
		//组合业务对象
		ChatService chatService = new ChatServiceImpl();
		
		//调用业务方法
		//System.out.println(fromUserid+toUserid+"");
		List<ChatMessage> list = chatService.getChatRecord(fromUserid, toUserid);
		Gson gson = new Gson();
		
		String chatInfo = gson.toJson(list);
		
		out.println(chatInfo);
		System.out.println(chatInfo);
		out.flush();
	}

}
