package controllers;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DepartmentDAO;
import database.EmployeeDAO;
import database.SalaryDAO;
import models.Employees;

@WebServlet("/DeleteController")
public class DeleteController extends HttpServlet {

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    EmployeeDAO dao = new EmployeeDAO();
    Employees em = new Employees();
    SalaryDAO salDao = new SalaryDAO();
    DepartmentDAO depDao = new DepartmentDAO();
    
    String redirectURL = "";
    
    if (request.getParameter("emp_id") != null) {
        int emp_id = Integer.valueOf(request.getParameter("emp_id"));
        
        try {
            dao.deleteEmployee(emp_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redirectURL = "/employee";
    } else if (request.getParameter("salary_id") != null) {
        int salary_id = Integer.valueOf(request.getParameter("salary_id"));
        
        try {
            salDao.deleteSalary(salary_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redirectURL = "/salary";
    } else if (request.getParameter("department_id") != null) {
        int department_id = Integer.valueOf(request.getParameter("department_id"));
        
        try {
            depDao.deleteDepartment(department_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redirectURL = "/department";
    }

    response.sendRedirect(request.getContextPath() + redirectURL);
	}
}


