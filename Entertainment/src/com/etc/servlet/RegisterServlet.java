package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.User;
import com.etc.service.UserService;
import com.etc.service.impl.UserServiceImpl;
import com.google.gson.Gson;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");	

		//获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();	   	

		//设置字符编码---需要字符转码时加此句
		request.setCharacterEncoding("utf-8"); 
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		UserService userservice=new UserServiceImpl();
		User user=userservice.FindUsername(username);
		
		if(user==null)
		{
			user=userservice.UserRegister(username, password);
			Gson gson=new Gson();
			out.println(gson.toJson(user));
			System.out.println(gson.toJson(user)+"gson");
		}
		else
		{
			out.println("false");
			System.out.println("test重复");
		}
	}

}
