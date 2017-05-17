package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.entity.Article;
import com.etc.service.ArticleService;
import com.etc.service.impl.ArticleServiceImpl;
import com.google.gson.Gson;

public class ArticleWriteServlet extends HttpServlet {

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
		
		Article article = new Article();
		ArticleService articleservice = new ArticleServiceImpl();
		
		String userid = request.getParameter("userid");
		String articletitle = request.getParameter("articletitle");
		String articlecont = request.getParameter("articlecont");
		
		if(!"".equals(articletitle) && !"".equals(articlecont)){
			article.setUserid(Integer.parseInt(userid.trim()));
			article.setArticletitle(articletitle);
			article.setArticlecont(articlecont);
			
			articleservice.writeArticle(article);
			Gson gson = new Gson();		
			String userJson = gson.toJson(article);			
			out.println(userJson);
		}
	}

}
