package com.etc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.entity.ItemComment;
import com.etc.service.ItemCommentService_h;
import com.etc.service.impl.ItemCommentServiceImpl_h;
import com.google.gson.Gson;

/**
 * Servlet implementation class ItemCommentShow
 */
public class ItemCommentShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   ItemCommentService_h itemcommentservice = new ItemCommentServiceImpl_h();
    public ItemCommentShow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");	
		request.setCharacterEncoding("utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		int itemid = Integer.parseInt(request.getParameter("itemid"));
		List<ItemComment> list = itemcommentservice.findItemCommList(itemid);
		Gson gson = new Gson();
		String gstr = gson.toJson(list);
		out.print(gstr);
		out.flush();
		out.close();
		// TODO Auto-generated method stub
	}

}
