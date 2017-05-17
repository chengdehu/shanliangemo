package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.dao.UserDAO;
import com.etc.dao.impl.UserDAOImpl;
import com.etc.service.EntertainmentService;
import com.etc.service.impl.EntertainmentServiceImpl;


public class UserLabelServlet extends HttpServlet {

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
		String itemid = request.getParameter("itemid");
		
		EntertainmentService itemservice = new EntertainmentServiceImpl();
		UserDAO userdao = new UserDAOImpl();
		List<String> list = itemservice.findItemLabel(Integer.parseInt(itemid.trim()));
		for(int i = 0; i < list.size(); i++){
			String label = list.get(i);
			userdao.updateUserLabel(Integer.parseInt(userid.trim()), label);
		}
	}

}
