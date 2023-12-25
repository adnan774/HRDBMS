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

import com.google.gson.Gson;

import database.DepartmentDAO;
import database.EmployeeDAO;
import database.SalaryDAO;
import models.Departments;
import models.Employees;
import models.Salaries;

@WebServlet("/api/employee")
public class EmployeesAPIController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    // Handle GET requests for all employees
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            EmployeeDAO dao = new EmployeeDAO();
            ArrayList<Employees> allEmployees = dao.getAllEmployees();
            String json = new Gson().toJson(allEmployees);
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal server error: " + e.getMessage());
        }
    }
    
    
    // Handle POST requests to add a new employee
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        SalaryDAO salaryDAO = new SalaryDAO();

        try {
            // Parse the request body to an Employees object
            Employees employee = gson.fromJson(request.getReader(), Employees.class);

            // Extract department name and job title from the employee object
            String departmentName = employee.getDepartments().getDepartment_name();
            String jobTitle = employee.getSalaries().getJob_title();

            // Look up the department ID and salary ID
            int departmentId = departmentDAO.getDepartmentIdByName(departmentName);
            int salaryId = salaryDAO.getSalaryIdByJobTitle(jobTitle);
            
            System.out.println("Resolved Department ID: " + departmentId);
            System.out.println("Resolved Salary ID: " + salaryId);
            
            // Set the department ID and salary ID in the employee object
            employee.getDepartments().setDepartment_id(departmentId);
            employee.getSalaries().setSalary_id(salaryId);

            // Save the employee
            boolean isInserted = employeeDAO.insertEmployee(employee, departmentId, salaryId);
            if (isInserted) {
                response.getWriter().write(gson.toJson(employee));
                response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created status
            } else {
                response.getWriter().write("{\"message\": \"Employee not added. Please try again.\"}");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"message\": \"Error: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
    
    
    // Handle PUT requests to update an employee
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        SalaryDAO salaryDAO = new SalaryDAO();

        try {
            // Parse the request body to an Employees object
            Employees employee = gson.fromJson(request.getReader(), Employees.class);

            // Extract department name and job title from the employee object
            String departmentName = employee.getDepartments().getDepartment_name();
            String jobTitle = employee.getSalaries().getJob_title();

            // Look up the department ID and salary ID
            int departmentId = departmentDAO.getDepartmentIdByName(departmentName);
            int salaryId = salaryDAO.getSalaryIdByJobTitle(jobTitle);

            // Update the employee
            boolean isUpdated = employeeDAO.updateEmployee(employee, departmentId, salaryId);
            if (isUpdated) {
                response.getWriter().write(gson.toJson(employee));
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK status
            } else {
                response.getWriter().write("{\"message\": \"Employee not updated. Please try again.\"}");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"message\": \"Error: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
    
    
    // Handle DELETE requests to delete an employee
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        EmployeeDAO dao = new EmployeeDAO();
        String empIdParam = request.getParameter("emp_id");

        if (empIdParam != null) {
            try {
                int emp_id = Integer.parseInt(empIdParam);
                boolean isDeleted = dao.deleteEmployee(emp_id);

                if (isDeleted) {
                    response.getWriter().write(gson.toJson("Employee deleted successfully."));
                    response.setStatus(HttpServletResponse.SC_OK); // 200 OK status
                } else {
                    response.getWriter().write(gson.toJson("Employee not found."));
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found status
                }
            } catch (NumberFormatException e) {
                response.getWriter().write(gson.toJson("Invalid employee ID format."));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write(gson.toJson("Internal server error: " + e.getMessage()));
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            }
        } else {
            response.getWriter().write(gson.toJson("Employee ID not provided."));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
        }
    }

}