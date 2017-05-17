package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.User;
import com.etc.service.ChangeUserInfoService;
import com.google.gson.Gson;


public class ChangeUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ChangeUserInfoService changeuserinfoservice = new ChangeUserInfoService();
	
	public ChangeUserInfoServlet(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost (request,response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				System.out.println(">>>ChangeUserInfoServlet被请求！");
				//设置输出内容类型
				response.setContentType("text/html;charset=utf-8");	

				//获取out输出对象---需要输出内容时加此句
				PrintWriter out = response.getWriter();	   	


				//设置字符编码---需要字符转码时加此句
				request.setCharacterEncoding("utf-8"); 
				
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				int userid = Integer.parseInt(request.getParameter("userid"));
				
				//List<User> users = comservice.getItemComment(username, password);
				List<User> users = changeuserinfoservice.getUsers(username, password,userid);
				
			
				Gson gson = new Gson();
				String gstr = gson.toJson(users);
				
				out.print(gstr);
				out.flush();
				out.close();
		
	}

}
