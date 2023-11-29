package controllers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/UpdateController")
public class UpdateController2 extends HttpServlet {
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

        if (request.getParameter("emp_id") != null) {
            int emp_id = Integer.valueOf(request.getParameter("emp_id"));
            Employees em = dao.getEmployeeByID(emp_id);
            request.setAttribute("departmentNames", departmentNames);
    		request.setAttribute("jobTitles", jobTitles);
    		request.setAttribute("salaries", allSalaries);
    		request.setAttribute("departments", allDepartments);
    		request.setAttribute("employees", allEmployees);
    		request.setAttribute("employee", em);
            request.getRequestDispatcher("update.jsp").include(request, response);
        
        } else if (request.getParameter("salary_id") != null) {
            int salary_id = Integer.valueOf(request.getParameter("salary_id"));
            Salaries sal = salDao.getSalaryByID(salary_id);
            request.setAttribute("salaries", allSalaries);
            request.setAttribute("salary", sal);
            request.getRequestDispatcher("update_salary.jsp").include(request, response);
        
        } else if (request.getParameter("department_id") != null) {
            int department_id = Integer.valueOf(request.getParameter("department_id"));
            Departments dep = depDao.getDepartmentByID(department_id);
            request.setAttribute("departments", allDepartments);
            request.setAttribute("department", dep);
            request.getRequestDispatcher("update_department.jsp").include(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("emp_id") != null) {
                // Update employee
                EmployeeDAO dao = new EmployeeDAO();
                DepartmentDAO departmentDAO = new DepartmentDAO();
        	    SalaryDAO salaryDAO = new SalaryDAO();
                // Get the user's selected department_name
                String selectedDepartmentName = request.getParameter("department_name");
                
                // Get the user's selected salary job_title
                String selectedSalary = request.getParameter("job_title");

                // Get the department_id based on the user's selected department_name
                int departmentId = departmentDAO.getDepartmentIdByName(selectedDepartmentName);

                int salaryId = salaryDAO.getSalaryIdByJobTitle(selectedSalary);
        	    
        	    // Set the updated employee details
                Employees em = new Employees();
        	    int emp_id = Integer.parseInt(request.getParameter("emp_id"));
        	    // ... Retrieve and set updated employee details ...
        	    em = dao.getEmployeeByID(emp_id);
        	    em.setFirst_name(request.getParameter("first_name"));
        	    em.setLast_name(request.getParameter("last_name"));
        	    em.setEmail(request.getParameter("email"));
        	    em.setDob(request.getParameter("date_of_birth"));
        	    em.setHire_date(request.getParameter("hire_date"));
        	    em.setPhone_number(request.getParameter("phone_number"));
        	    em.setAddress(request.getParameter("address"));
        	    em.setCity(request.getParameter("city"));
        	    em.setTown(request.getParameter("town"));
        	    em.setPost_code(request.getParameter("post_code"));
        	    
        	    System.out.println("Department ID: " + departmentId);
                System.out.println("Salary ID: " + salaryId);
                
                if (dao.updateEmployee(em, departmentId, salaryId)) {
                    response.sendRedirect("employee");
                } else {
                    request.setAttribute("message", "Employee not updated. Please try again.");
                    doGet(request, response);
                }
            
            } else if (request.getParameter("salary_id") != null) {
                // Update salary
                SalaryDAO salDao = new SalaryDAO();
                Salaries sal = new Salaries();
                int salary_id = Integer.parseInt(request.getParameter("salary_id"));
                // Retrieves and sets updated salary details
                sal = salDao.getSalaryByID(salary_id);
                sal.setJob_title(request.getParameter("job_title"));
                sal.setSalary(Float.valueOf(request.getParameter("salary")));
                if (salDao.updateSalary(sal)) {
                	response.sendRedirect("salary");
                } else {
                    request.setAttribute("message", "Salary not updated. Please try again.");
                    doGet(request, response);
                }
            } else if (request.getParameter("department_id") != null) {
                // Update department
                DepartmentDAO depDao = new DepartmentDAO();
                Departments dep = new Departments();
                int department_id = Integer.parseInt(request.getParameter("department_id"));
                // Retrieves and set updated department details
                dep = depDao.getDepartmentByID(department_id);
                dep.setDepartment_name(request.getParameter("department_name"));
                dep.setLocation(request.getParameter("location"));
                if (depDao.updateDepartment(dep)) {
                    response.sendRedirect("department");
                } else {
                    request.setAttribute("message", "Department not updated. Please try again.");
                    doGet(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error: " + e.getMessage());
            doGet(request, response);
        }
    }
}
