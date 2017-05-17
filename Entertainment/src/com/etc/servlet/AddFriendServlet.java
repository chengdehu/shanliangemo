package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.service.UserService;
import com.etc.service.impl.UserServiceImpl;


public class AddFriendServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");	
		PrintWriter out = response.getWriter();	   	
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8"); 
		
		String userid = request.getParameter("userid");
		String friend_id = request.getParameter("friend_id");
		UserService userservice = new UserServiceImpl();
		
		if(userservice.addFriend(Integer.parseInt(userid.trim()), Integer.parseInt(friend_id.trim())))
			out.print("添加好友成功！");
		else
			out.print("添加好友失败！");
	}

}
