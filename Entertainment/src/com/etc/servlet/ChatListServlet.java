package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.dbutil.DBManager;
import com.etc.entity.ChatMessage;
import com.etc.entity.User;
import com.etc.service.ChatService;
import com.etc.service.impl.ChatServiceImpl;
import com.google.gson.Gson;

public class ChatListServlet extends HttpServlet {
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
		//组合业务对象
		ChatService chatService = new ChatServiceImpl();
		//调用业务方法
		List<ChatMessage> list = chatService.getChatList(fromUserid);
		Gson gson = new Gson();
		
		String chatInfo = gson.toJson(list);
		
		out.println(chatInfo);
		System.out.println(chatInfo);
		
		out.flush();
		
		/*//保存用户列表
		session.setAttribute("list", list);
		
		//保存输入的数据
		session.setAttribute("fromUserid", fromUserid);
		session.setAttribute("toUserid", toUserid);
		
		//跳转到查询页面
		response.sendRedirect("chat_record_query.jsp");*/
	}

}
