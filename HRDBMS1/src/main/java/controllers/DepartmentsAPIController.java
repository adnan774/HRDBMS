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

import database.DepartmentDAO;
import database.EmployeeDAO;
import models.Departments;
import models.Employees;
import models.Salaries;



@WebServlet("/api/departments")
public class DepartmentsAPIController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET requests for departments
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        DepartmentDAO dao = new DepartmentDAO();
        try {
            ArrayList<Departments> allDepartments = dao.getAllDepartments();
            String json = new Gson().toJson(allDepartments);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"message\": \"Error retrieving departments: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    
    // Handle POST requests to add a new department
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Gson gson = new Gson();
        DepartmentDAO dao = new DepartmentDAO();

        try {
            Departments department = gson.fromJson(request.getReader(), Departments.class);
            dao.insertDepartment(department);
            response.getWriter().write(gson.toJson(department));
            response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created status
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"message\": \"Error adding department: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.getWriter().write("{\"message\": \"Invalid department data: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
        }
    }
    
    
    // Handle PUT requests to update an existing department
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        DepartmentDAO dao = new DepartmentDAO();

        try {
            // Parse the request body to a Departments object
            Departments department = gson.fromJson(request.getReader(), Departments.class);

            // Update the department
            boolean isUpdated = dao.updateDepartment(department);
            if (isUpdated) {
                response.getWriter().write(gson.toJson(department));
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK status
            } else {
                // Department not found or unable to update
                response.getWriter().write("{\"message\": \"Department not updated. Please try again.\"}");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found status
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"message\": \"Error updating department: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        } catch (Exception e) {
            response.getWriter().write("{\"message\": \"Invalid department data: " + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
        }
    }
    
    
    // Handle DELETE requests to delete a department
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        DepartmentDAO dao = new DepartmentDAO();
        Gson gson = new Gson();

        // Extract department ID from the request
        String departmentIdParam = request.getParameter("department_id");
        if (departmentIdParam != null) {
            try {
                int department_id = Integer.parseInt(departmentIdParam);
                boolean isDeleted = dao.deleteDepartment(department_id);

                if (isDeleted) {
                	response.getWriter().write(gson.toJson("Department deleted successfully."));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content status
                } else {
                    // Department not found or unable to delete
                	response.getWriter().write(gson.toJson("Department not found."));
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found status
                }
            } catch (NumberFormatException e) {
                // Invalid department ID format
            	response.getWriter().write(gson.toJson("Invalid department ID format."));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write(gson.toJson("Internal server error: " + e.getMessage()));
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            }
        } else {
            // Department ID not provided
        	response.getWriter().write(gson.toJson("Department ID not provided."));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request status
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
   
}