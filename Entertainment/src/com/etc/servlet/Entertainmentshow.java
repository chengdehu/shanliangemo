package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.Entertainment;
import com.etc.service.EntertainmentService;
import com.etc.service.impl.EntertainmentServiceImpl;
import com.google.gson.Gson;
public class Entertainmentshow extends HttpServlet {
	EntertainmentService entertainmentservice = new  EntertainmentServiceImpl();
	private static final long serialVersionUID = 1L;
       
    public Entertainmentshow() {
        super();
        // TODO Auto-generated constructor stub
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
		List<Entertainment> list = entertainmentservice.getAllNews();
		Gson gson = new Gson();
		String gstr = gson.toJson(list);
		out.print(gstr);
		out.flush();
		out.close();// TODO Auto-gen erated method stub
	}

}
