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
import com.etc.entity.User;
import com.etc.service.ChatService;
import com.etc.service.FriendsService;
import com.etc.service.impl.ChatServiceImpl;
import com.etc.service.impl.FriendsServiceImpl;
import com.google.gson.Gson;

public class FriendsListServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");	
		PrintWriter out = response.getWriter();	
	    HttpSession session = request.getSession();	
		request.setCharacterEncoding("utf-8");
		
		//接收数据
		int userid = Integer.parseInt(request.getParameter("userid"));
		
		//组合业务对象
		FriendsService friendsService = new FriendsServiceImpl();
		//调用业务方法
		List<User> list = friendsService.getFriendsList(userid);
		Gson gson = new Gson();
		
		String friendsInfo = gson.toJson(list);
		
		out.println(friendsInfo);
		System.out.println(friendsInfo);
		
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
