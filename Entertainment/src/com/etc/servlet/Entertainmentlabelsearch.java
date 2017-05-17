package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.Enterlabel;
import com.etc.entity.Entertainment;
import com.etc.service.EntertainmentService;
import com.etc.service.impl.EntertainmentServiceImpl;
import com.google.gson.Gson;

public class Entertainmentlabelsearch extends HttpServlet {
	EntertainmentService entertainmentservice = new  EntertainmentServiceImpl();
	private static final long serialVersionUID = 1L;
       
    public Entertainmentlabelsearch() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);// TODO Auto-generated method stub
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");	
		request.setCharacterEncoding("utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		String itemtype = request.getParameter("itemtype");
		String label_name = request.getParameter("label_name");
		System.out.println(label_name);
		List<Entertainment> list = entertainmentservice.getfoundnewsbylabel(itemtype, label_name);
		Gson gson = new Gson();
		String gstr = gson.toJson(list);
		System.out.print(gstr);		
		out.print(gstr);
		out.flush();
		out.close();// TODO Auto-generated method stub
	}

}
