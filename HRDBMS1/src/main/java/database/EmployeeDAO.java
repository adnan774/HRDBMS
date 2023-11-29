package database;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.Departments;
import models.Employees;
import models.Salaries;

import java.sql.*;


public class EmployeeDAO {
	
	Employees oneEmployee = null;
	Salaries oneSalary = null;
	Departments oneDepartment = null;
	Connection conn = null;
    Statement stmt = null;
    String user = "root1";
    String password = "password1";
    String url = "jdbc:mysql://localhost:3306/hrdbms";

    private void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Error loading the JDBC driver: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            System.out.println("Database connection successfully established.");
        } catch (SQLException se) {
            System.out.println("Error while connecting to the database: " + se.getMessage());
            se.printStackTrace();
        }
    }

	private void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Employees getNextEmployee(ResultSet rs) {
	    Employees thisEmployee = null;
	    try {
	        Departments department = new Departments(
	            rs.getInt("department_id"),
	            rs.getString("department_name"),
	            rs.getString("location")
	        );
	        
	        Salaries salary = new Salaries(
	            rs.getInt("salary_id"),
	            rs.getString("job_title"),
	            rs.getFloat("salary")
	        );
	        
	        System.out.println("department_id: " + rs.getInt("department_id"));
	        System.out.println("department_name: " + rs.getString("department_name"));
	        System.out.println("location: " + rs.getString("location"));
	        System.out.println("salary_id: " + rs.getInt("salary_id"));
	        System.out.println("job_title: " + rs.getString("job_title"));
	        System.out.println("salary: " + rs.getFloat("salary"));

	        thisEmployee = new Employees(
	        	rs.getInt("emp_id"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getString("email"),
				rs.getString("date_of_birth"),
				rs.getString("phone_number"),
				rs.getString("hire_date"),
				rs.getString("address"),
				rs.getString("city"),
				rs.getString("town"),
				rs.getString("post_code"),
	            department,
	            salary
	        );

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return thisEmployee;
	}

	}
	

   
   
   
