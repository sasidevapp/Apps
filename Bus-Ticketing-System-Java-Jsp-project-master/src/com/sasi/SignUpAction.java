/*
 * FileName: SignUpAction.java
 * Author  : sasi1901
 * 
 * Using JRE 1.8.0_212
 *  
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Feb 10, 2020       Sasi   Initial Code  
 */
package com.sasi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The Class SignUpAction.
 */
@WebServlet("/SignUpAction")
public class SignUpAction extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** The form data. */
	ArrayList<String> formData;

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		formData = new ArrayList<String>();
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password =  request.getParameter("password");
		
		
		out.println("<br>Name" + name);
		out.println("<br>Email" + email);
		out.println("<br>Passord" + password);
		
		HttpSession session = request.getSession();
		
		formData.add(name);
		formData.add(email);
		formData.add(password);
		
		session.setAttribute("formData", formData );
		
		out.println("<br> <a href='SessionTest  '>View Session Data</a>");
	}

}
