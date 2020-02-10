/*
 * FileName: LoginAction.java
 * Author  : sasi1901
 * 
 * Using JRE 1.8.0_212
 *  
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Feb 10, 2020       Sasi   Initial Code  
 */
package com.sasi.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.JsonObject;
import com.sasi.User;

/**
 * The Class LoginAction.
 */
public class LoginAction extends CommonDispatchAction {

    /**
     * Do login.
     *
     * @param actionMapping the action mapping
     * @param actionForm the action form
     * @param request the request
     * @param response the response
     * @return the action forward
     * @throws IOException
     * @throws Exception the exception
     */
	public ActionForward doLogin(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
        HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		User user = new User();
		JsonObject errorObj = new JsonObject();
		String message = "";
		String userName = null,passWord = null;
		long userId = 0;
		userName = request.getParameter("phone");
		passWord = request.getParameter("password");
		if (userName.equals("") || passWord.equals("")) {
			message = "User name and password required!";
			errorObj.addProperty("msg", message);
			out.append(errorObj.toString());
			out.close();
		} else if ((userId = user.doLogin(userName, passWord)) > 0) {
			session.setAttribute("isUserLogin", true);
			session.setAttribute("userId", String.valueOf(userId));
			session.setAttribute("name", String.valueOf(user.name));
			user.SetUserFromId(Long.toString(userId));
			user.SetUserSession(session);
		} else {
			message = "User id or password not found!";
			errorObj.addProperty("msg", message);
			out.append(errorObj.toString());
			out.close();
			session.invalidate();
		}
		return null;
	}

}
