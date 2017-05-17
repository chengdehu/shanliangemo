package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.entity.Article;
import com.etc.service.ArticleCommentService;
import com.etc.service.ArticleService;
import com.etc.service.impl.ArticleCommentServiceImpl;
import com.etc.service.impl.ArticleServiceImpl;
import com.google.gson.Gson;


public class ArticleIndexServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");	
		PrintWriter out = response.getWriter();	   	
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8"); 
		
		String articleid = request.getParameter("articleid");
		ArticleService articleservice = new ArticleServiceImpl();	
		ArticleCommentService articommservice = new ArticleCommentServiceImpl();

		Article article = articleservice.findArticle(Integer.parseInt(articleid.trim()));
		
		Gson gson = new Gson();		
		String artiJson = gson.toJson(article);	
		out.println(artiJson);	
	}

}
