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

import database.DepartmentDAO;
import database.EmployeeDAO;
import models.Departments;
import models.Employees;
import models.Salaries;



@WebServlet("/department")
public class DepartmentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DepartmentDAO dao = new DepartmentDAO();
		ArrayList<Departments> allDepartments = dao.getAllDepartments();
		Departments d = new Departments();
		String departmentName = request.getParameter("department_name");
		System.out.println("Department name: " + departmentName);
		

		
		
		for(Departments department : allDepartments) {
			System.out.println(department.getDepartment_name());
		}
		
		
		request.setAttribute("departments", allDepartments);
		RequestDispatcher rd = request.getRequestDispatcher("departments.jsp");
		rd.include(request, response);
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    DepartmentDAO dao = new DepartmentDAO();
	    Departments d = new Departments();
	    
	    
	    d.setDepartment_name(request.getParameter("department_name"));
	    d.setLocation(request.getParameter("location"));
	    

	    
	    try {
	        dao.insertDepartment(d);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    doGet(request, response);
	}
}