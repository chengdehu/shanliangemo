package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.Itemcontent;
import com.etc.service.ItemcontentService;
import com.etc.service.impl.ItemcontentServiceImpl;
import com.google.gson.Gson;

public class Itemcontentshow extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ItemcontentService itemcontentservice = new ItemcontentServiceImpl();
	public Itemcontentshow(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");	
		request.setCharacterEncoding("utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		int itemid = Integer.parseInt(request.getParameter("itemid"));
		List<Itemcontent> list = itemcontentservice.getcontent(itemid);
		Gson gson = new Gson();
		String gstr = gson.toJson(list);
		out.print(gstr);
		out.flush();
		out.close();
	}

}
