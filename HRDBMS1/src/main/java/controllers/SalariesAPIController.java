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

import com.google.gson.Gson;

import database.EmployeeDAO;
import database.SalaryDAO;
import models.Departments;
import models.Employees;
import models.Salaries;



@WebServlet("/api/salaries")
public class SalariesAPIController extends HttpServlet {

    // Handle GET requests for salaries
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        SalaryDAO dao = new SalaryDAO();
        try {
            ArrayList<Salaries> allSalaries = dao.getAllSalaries();
            String json = new Gson().toJson(allSalaries);
            response.getWriter().write(json);
        } catch (Exception e) { // Catching general exceptions
            e.printStackTrace();
            response.getWriter().write("{\"message\": \"Error retrieving salaries: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    
    // Handle POST requests to add a new salary
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        SalaryDAO dao = new SalaryDAO();

        try {
            Salaries salary = gson.fromJson(request.getReader(), Salaries.class);
            dao.insertSalary(salary);
            response.getWriter().write(gson.toJson(salary));
            response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created status
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"message\": \"Error adding salary: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.getWriter().write("{\"message\": \"Invalid salary data: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
        }
    }
    
    
    // Handle PUT requests to update an existing salary
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        SalaryDAO dao = new SalaryDAO();

        try {
            // Parse the request body to a Salaries object
            Salaries salary = gson.fromJson(request.getReader(), Salaries.class);

            // Update the salary
            boolean isUpdated = dao.updateSalary(salary);
            if (isUpdated) {
                response.getWriter().write(gson.toJson(salary));
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK status
            } else {
                // Salary not found or unable to update
                response.getWriter().write("{\"message\": \"Salary not updated. Please try again.\"}");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found status
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"message\": \"Error updating salary: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        } catch (Exception e) {
            response.getWriter().write("{\"message\": \"Invalid salary data: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
        }
    }
    
    
    // Handle DELETE requests to delete a salary
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SalaryDAO dao = new SalaryDAO();
        Gson gson = new Gson();
        
        // Extract salary ID from the request
        String salaryIdParam = request.getParameter("salary_id");
        if (salaryIdParam != null) {
            try {
                int salary_id = Integer.parseInt(salaryIdParam);
                boolean isDeleted = dao.deleteSalary(salary_id);

                if (isDeleted) {
                	response.getWriter().write(gson.toJson("Salary deleted successfully."));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content status
                } else {
                    // Salary not found or unable to delete
                	response.getWriter().write(gson.toJson("Salary not found."));
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found status
                }
            } catch (NumberFormatException e) {
                // Invalid salary ID format
            	response.getWriter().write(gson.toJson("Invalid salary ID format."));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write(gson.toJson("Internal server error: " + e.getMessage()));
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            }
        } else {
            // Salary ID not provided
        	response.getWriter().write(gson.toJson("Salary ID not provided."));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
        }
    }

}
