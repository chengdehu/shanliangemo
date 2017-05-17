package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.ItemComment;
import com.etc.service.ItemCommentService;
import com.google.gson.Gson;


public class EntertainmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ItemCommentService comservice = new ItemCommentService();
	
	public EntertainmentServlet(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost (request,response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(">>>ItemCommentServlet被请求！");
		//设置输出内容类型
		response.setContentType("text/html;charset=utf-8");	

		//获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();	   	


		//设置字符编码---需要字符转码时加此句
		request.setCharacterEncoding("utf-8"); 
		

		
		List<ItemComment> coms = comservice.getItemComment();
		
		
	
		Gson gson = new Gson();
		String gstr = gson.toJson(coms);
		
		out.print(gstr);
		out.flush();
		out.close();
		
	}

}
