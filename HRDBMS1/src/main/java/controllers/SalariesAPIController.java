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

}
