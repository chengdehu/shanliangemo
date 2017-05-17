package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.service.EntertainmentService;
import com.etc.service.impl.EntertainmentServiceImpl;
import com.google.gson.Gson;

public class Updatelike extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EntertainmentService entertainmentservice = new EntertainmentServiceImpl();
	public Updatelike(){
		super();
	}
@Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	     this.doPost(request, response);
       }
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	response.setContentType("text/html;charset=utf-8");	
 		request.setCharacterEncoding("utf-8"); 
 		response.setCharacterEncoding("utf-8"); 
 		PrintWriter out = response.getWriter();
 		int itemid = Integer.parseInt(request.getParameter("itemid"));
 		System.out.println(itemid);
 		boolean update = entertainmentservice.updatecount(itemid);
 		Gson gson = new Gson();
		String gstr = gson.toJson(update);
		out.print(gstr);
		out.flush();
		out.close();
    }
}
