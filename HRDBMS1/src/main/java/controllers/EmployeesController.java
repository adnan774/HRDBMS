package controllers;
import java.util.Date;
import java.util.List;
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
import database.SalaryDAO;
import models.Departments;
import models.Employees;
import models.Salaries;



@WebServlet("/employee")
public class EmployeesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EmployeeDAO dao = new EmployeeDAO();
		SalaryDAO salDao = new SalaryDAO();
		DepartmentDAO depDao = new DepartmentDAO();
		ArrayList<Employees> allEmployees = dao.getAllEmployees();
		ArrayList<Salaries> allSalaries = salDao.getAllSalaries();
		ArrayList<Departments> allDepartments = depDao.getAllDepartments();
		List<String> jobTitles = salDao.getAllJobTitles();
		List<String> departmentNames = depDao.getAllDepartmentNames();

		Employees em = new Employees();
		String firstName = request.getParameter("first_name");
		//System.out.println("First name: " + firstName);
		

		
		
		for(Employees employee : allEmployees) {
			System.out.println(employee.getFirst_name());
		}
		
		
		request.setAttribute("departmentNames", departmentNames);
		request.setAttribute("jobTitles", jobTitles);
		request.setAttribute("salaries", allSalaries);
		request.setAttribute("departments", allDepartments);
		request.setAttribute("employees", allEmployees);
		RequestDispatcher rd = request.getRequestDispatcher("employees.jsp");
		rd.include(request, response);
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        EmployeeDAO employeeDAO = new EmployeeDAO();
	        DepartmentDAO departmentDAO = new DepartmentDAO();
	        SalaryDAO salaryDAO = new SalaryDAO();
	        
	        // Gets the user's selected department_name
	        String selectedDepartmentName = request.getParameter("department_name");
	        
	        // Gets the user's selected salary job_title
	        String selectedSalary = request.getParameter("job_title");

	        // Gets the department_id based on the user's selected department_name
	        int departmentId = departmentDAO.getDepartmentIdByName(selectedDepartmentName);

	        int salaryId = salaryDAO.getSalaryIdByJobTitle(selectedSalary);
	        		

	        Employees e = new Employees();
	        e.setFirst_name(request.getParameter("first_name"));
	        e.setLast_name(request.getParameter("last_name"));
	        e.setEmail(request.getParameter("email"));
	        e.setDob(request.getParameter("date_of_birth"));
	        e.setPhone_number(request.getParameter("phone_number"));
	        e.setHire_date(request.getParameter("hire_date"));
	        e.setAddress(request.getParameter("address"));
	        e.setCity(request.getParameter("city"));
	        e.setTown(request.getParameter("town"));
	        e.setPost_code(request.getParameter("post_code"));
	        
	        System.out.println("Department ID: " + departmentId);
	        System.out.println("Salary ID: " + salaryId);


	        if (employeeDAO.insertEmployee(e, departmentId, salaryId)) {
	            response.sendRedirect("employee");
	        } else {
	            request.setAttribute("message", "Employee not added. Please try again.");
	            doGet(request, response);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("message", "Error: " + e.getMessage());
	        doGet(request, response);
	    }
	}

}