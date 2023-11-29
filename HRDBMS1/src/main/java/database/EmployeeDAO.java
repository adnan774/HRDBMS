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
	
	public ArrayList<Employees> getAllEmployees() {
	    ArrayList<Employees> allEmployees = new ArrayList<Employees>();
	    openConnection();
	    ResultSet rs1 = null;
	    try {
	    	String selectSQL = "SELECT e.emp_id, e.first_name, e.last_name, e.email, e.date_of_birth, e.phone_number, e.hire_date, e.address, e.city, e.town,"
	    			+ " e.post_code, d.department_id, d.department_name, d.location, s.salary_id, s.job_title, s.salary FROM employees e "
	    		    + "JOIN departments d ON e.department_id = d.department_id JOIN salaries s ON e.salary_id = s.salary_id ORDER BY e.emp_id;";
	        rs1 = stmt.executeQuery(selectSQL);
	        while (rs1.next()) {
	            Departments department = new Departments(
	                rs1.getInt("department_id"),
	                rs1.getString("department_name"),
	                rs1.getString("location")
	            );
	            Salaries salary = new Salaries(
	                rs1.getInt("salary_id"),
	                rs1.getString("job_title"),
	                rs1.getFloat("salary")              
	            );
	            Employees employee = new Employees(
	                rs1.getInt("emp_id"),
	                rs1.getString("first_name"),
	                rs1.getString("last_name"),
	                rs1.getString("email"),
	                rs1.getString("date_of_birth"),
	                rs1.getString("phone_number"),
	                rs1.getString("hire_date"),
	                rs1.getString("address"),
	                rs1.getString("city"),
	                rs1.getString("town"),
	                rs1.getString("post_code"),
	                department,
	                salary
	            );
	            System.out.println("Fetched employee: " + employee);
	            allEmployees.add(employee);
	        }
	    } catch (SQLException se) {
	        System.out.println(se);
	    } finally {
	        if (rs1 != null) {
	            try {
	                rs1.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return allEmployees;
	}

	}
	

   
   
   
