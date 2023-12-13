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

   
}