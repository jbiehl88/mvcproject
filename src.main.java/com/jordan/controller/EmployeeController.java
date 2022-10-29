/**
 * 
 */
/**
 * @author jordan.biehl
 *
 */
package com.jordan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jordan.model.Employee;

//Controller -> navigate between model and view
@WebServlet("/emp")
public class EmployeeController extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// set the MIME-> Mulitpurpose Internet Mail Extension
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		//int id = Integer.parseInt(req.getParameter("id"));
		String username = req.getParameter("uname");
		String password = req.getParameter("pwd");

		Employee e1 = new Employee(0, username, password);
		//e1.setEmpId(id);
		//e1.setEmpName(username);
		//e1.setPassword(password);
		req.setAttribute("abc", e1);

		boolean status = e1.validate();

		if (status) {
			RequestDispatcher rd = req.getRequestDispatcher("success.jsp");
			rd.forward(req, res);
		} else {
			RequestDispatcher rd = req.getRequestDispatcher("failure.jsp");
			rd.forward(req, res);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

}