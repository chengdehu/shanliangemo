package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.etc.entity.MyArticleCollection;
import com.etc.service.MyArticleCollectionService;
import com.google.gson.Gson;


public class MyArticleCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MyArticleCollectionService collservice = new MyArticleCollectionService();
	
	public MyArticleCollectionServlet(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost (request,response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				System.out.println(">>>MyArticleCollection被请求！");
				
				request.setCharacterEncoding("utf-8"); 
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");	
				PrintWriter out = response.getWriter();	   	


				
				List<MyArticleCollection> colls = collservice.getMyArticleCollection();
				
							
				Gson gson = new Gson();
				String gstr = gson.toJson(colls);
				
				out.print(gstr);
				out.flush();
				out.close();
		
	}

}
