package controllers;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.EmployeeDAO;
import database.SalaryDAO;
import models.Departments;
import models.Employees;
import models.Salaries;



@WebServlet("/salary")
public class SalariesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SalaryDAO dao = new SalaryDAO();
		ArrayList<Salaries> allSalaries = dao.getAllSalaries();
		Salaries sal = new Salaries();
		String jobTitle = request.getParameter("job_title");
		System.out.println("job_title: " + jobTitle);
		

		
		
		for(Salaries salary : allSalaries) {
			System.out.println(salary.getJob_title());
		}
		
		
		request.setAttribute("salaries", allSalaries);
		RequestDispatcher rd = request.getRequestDispatcher("salaries.jsp");
		rd.include(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    SalaryDAO dao = new SalaryDAO();
	    Salaries s = new Salaries();
	    
	    
	    s.setJob_title(request.getParameter("job_title"));
	    s.setSalary(Float.valueOf(request.getParameter("salary")));
	    

	    try {
	        dao.insertSalary(s);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    doGet(request, response);
	}

}