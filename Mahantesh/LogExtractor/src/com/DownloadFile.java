package com;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DownloadFile
 */
@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		HttpSession session=request.getSession();
	    File f = (File) session.getAttribute("fileName");
	    System.out.println(f.getName());
		
		//String filename = "C:\\logs\\logExtractor\\logs_2015083118922.txt";
		//File f=file;
		
		response.setContentType("application/octet-stream");
		//String disHeader = "Attachment; Filename=\"" + filename + "\"";
		String disHeader = "Attachment; Filename=\"" + f.getName() + "\"";
		response.setHeader("Content-Disposition", disHeader);
		//File fileToDownload = new File(filename);

		InputStream in = null;
		ServletOutputStream outs = response.getOutputStream();

		try {
		in = new BufferedInputStream
		(new FileInputStream(f));
		int ch;
		
		while ((ch = in.read()) != -1) {
		outs.print((char) ch);
		}
		}
		finally {
		if (in != null) in.close(); // very important
		}

		outs.flush();
		outs.close();
		in.close();
		
		

	}
	}


