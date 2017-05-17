package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.Entertainment;

import com.etc.service.EntertainmentService;

import com.etc.service.impl.EntertainmentServiceImpl;

import com.google.gson.Gson;


public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CategoryServlet() {
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
		
		int category= Integer.parseInt(request.getParameter("category"));
		System.out.println(category+"+++++++++++++");
		EntertainmentService service=new EntertainmentServiceImpl();
		List<Entertainment> datalist=service.findCategory(category);
		Gson gson=new Gson();
		String entertainmentgson=gson.toJson(datalist);
		System.out.println(entertainmentgson);
		out.println(entertainmentgson);
	}

}
