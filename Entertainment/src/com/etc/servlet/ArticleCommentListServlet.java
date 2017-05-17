package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.entity.ArticleComment;
import com.etc.service.ArticleCommentService;
import com.etc.service.impl.ArticleCommentServiceImpl;
import com.google.gson.Gson;

public class ArticleCommentListServlet extends HttpServlet {

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
		
		ArticleCommentService articommservice = new ArticleCommentServiceImpl();
		
		List<ArticleComment> list = (List<ArticleComment>) articommservice.findArtiCommList(Integer.parseInt(articleid.trim()));
		
		Gson gson = new Gson();		
		String listJson = gson.toJson(list);
		out.println(listJson);	
	}

}
