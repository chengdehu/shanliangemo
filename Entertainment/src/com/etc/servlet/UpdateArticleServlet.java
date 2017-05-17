package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.service.ArticleService;
import com.etc.service.impl.ArticleServiceImpl;

public class UpdateArticleServlet extends HttpServlet {

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
		
		ArticleService articleservice = new ArticleServiceImpl();

		String like = request.getParameter("like");
		String dislike = request.getParameter("dislike");
		String articleid = request.getParameter("articleid");
		
		articleservice.UpdateArticle(Integer.parseInt(articleid.trim()), Integer.parseInt(like.trim()), Integer.parseInt(dislike.trim()));
	}

}
