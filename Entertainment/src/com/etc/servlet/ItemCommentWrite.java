package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.entity.ItemComment;
import com.etc.service.ItemCommentService_h;
import com.etc.service.impl.ItemCommentServiceImpl_h;
import com.google.gson.Gson;


public class ItemCommentWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ItemCommentWrite() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);// TODO Auto-generated method stub
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");	
		PrintWriter out = response.getWriter();	   	
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		
		String itemid = request.getParameter("itemid");
		String userid = request.getParameter("userid");
		String commentCont = request.getParameter("comment_cont");
		
		ItemComment comment = new ItemComment();
		ItemCommentService_h commservice = new ItemCommentServiceImpl_h();
		if(!"".equals(commentCont)){
			comment.setUser_id(Integer.parseInt(userid.trim()));
			comment.setItem_id(Integer.parseInt(itemid.trim()));
			comment.setComment_cont(commentCont);
			
			commservice.writeItemComm(comment);
			Gson gson = new Gson();		
			String userJson = gson.toJson(comment);			
			out.println(userJson);// TODO Auto-generated method stub
	}
	}
}

