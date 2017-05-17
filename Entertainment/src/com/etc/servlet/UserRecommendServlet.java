package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.dao.EntertainmentDAO;
import com.etc.dao.impl.EntertainmentDAOImpl;
import com.etc.entity.Entertainment;
import com.etc.service.UserService;
import com.etc.service.impl.UserServiceImpl;
import com.google.gson.Gson;

public class UserRecommendServlet extends HttpServlet {

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
		
		String userid = request.getParameter("userid");
		String itemtype = request.getParameter("itemtype");
		
		UserService userservice = new UserServiceImpl();
		EntertainmentDAO itemservice = new EntertainmentDAOImpl();
		List<Integer> idList = userservice.recommendToUser(Integer.parseInt(userid.trim()), itemtype);
		List<Entertainment> itemList = new ArrayList<Entertainment>();
		
		for(int i = 0; i < idList.size(); i++){
			Entertainment enter = itemservice.findnews(idList.get(i));
			itemList.add(enter);
		}
		
		Gson gson = new Gson();		
		String listJson = gson.toJson(itemList);	
		out.println(listJson);
	}

}
