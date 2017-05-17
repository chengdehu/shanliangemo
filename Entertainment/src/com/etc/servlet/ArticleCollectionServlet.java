package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.ArticleCollection;
import com.etc.service.ArticleCollectionService;
import com.google.gson.Gson;


public class ArticleCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArticleCollectionService collservice = new ArticleCollectionService();
	
	public ArticleCollectionServlet(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost (request,response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				System.out.println(">>>ArticleCollection被请求！");
				
				request.setCharacterEncoding("utf-8"); 
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");	
				PrintWriter out = response.getWriter();	   	

				
				int lastid = Integer.parseInt(request.getParameter("lastid"));
				int pagesize = Integer.parseInt(request.getParameter("pagesize"));
				
				List<ArticleCollection> colls = collservice.getArticleCollection(lastid, pagesize);
				
							
				Gson gson = new Gson();
				String gstr = gson.toJson(colls);
				
				out.print(gstr);
				out.flush();
				out.close();
		
	}

}
