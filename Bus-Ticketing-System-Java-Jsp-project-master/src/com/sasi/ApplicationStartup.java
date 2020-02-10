/*
 * FileName: ApplicationStartup.java
 * Author  : sasi1901
 * 
 * Using JRE 1.8.0_212
 *  
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Feb 10, 2020       Sasi   Initial Code  
 */
package com.sasi;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

/**
 * The Class ApplicationStartup.
 */
public class ApplicationStartup extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3375815647477996292L;

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
	@Override
	public void init(ServletConfig config) {
		try {
			Class.forName("com.sasi.Database");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
