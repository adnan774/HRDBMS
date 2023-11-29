package controllers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import database.DepartmentDAO;
import database.EmployeeDAO;
import database.SalaryDAO;
import models.Departments;
import models.Employees;
import models.Salaries;

/**
 * Servlet implementation class searchController
 */
@WebServlet("/search")
public class searchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// collects search from the form
		String searchStr = request.getParameter("search");
		
		SalaryDAO salDao = new SalaryDAO();
		DepartmentDAO depDao = new DepartmentDAO();
		EmployeeDAO dao = new EmployeeDAO();
		ArrayList<Employees> allEmployees = dao.searchEmployees(searchStr);
		ArrayList<Salaries> allSalaries = salDao.getAllSalaries();
		ArrayList<Departments> allDepartments = depDao.getAllDepartments();
		List<String> jobTitles = salDao.getAllJobTitles();
		List<String> departmentNames = depDao.getAllDepartmentNames();
		
		for(Employees em : allEmployees) {
			System.out.println(em.getFirst_name());
		}
		
		request.setAttribute("departmentNames", departmentNames);
		request.setAttribute("jobTitles", jobTitles);
		request.setAttribute("salaries", allSalaries);
		request.setAttribute("departments", allDepartments);
		request.setAttribute("employees", allEmployees);
		RequestDispatcher rd = request.getRequestDispatcher("employees.jsp");
		rd.forward(request, response);

		
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	    
	    doGet(request, response);
	}

}
