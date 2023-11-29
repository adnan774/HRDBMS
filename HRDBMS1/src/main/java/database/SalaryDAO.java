package database;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.Departments;
import models.Employees;
import models.Salaries;

import java.sql.*;


public class SalaryDAO {
	
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
	
	// Salaries Queries
	
		private Salaries getNextSalary(ResultSet rs){
			Salaries thisSalary=null;
			try {
				thisSalary = new Salaries(
						rs.getInt("salary_id"),
						rs.getString("job_title"),
						rs.getFloat("salary"));	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return thisSalary;		
		}
		
		public ArrayList<Salaries> getAllSalaries(){
			   
			ArrayList<Salaries> allSalaries= new ArrayList<Salaries>();
			openConnection();
			ResultSet rs1 = null;

		   
			try{
			    String selectSQL = "SELECT salary_id, job_title, salary FROM salaries;";
			    rs1 = stmt.executeQuery(selectSQL);
		    
			    while (rs1.next()) {
			        Salaries salary = new Salaries();
			        salary.setSalary_id(rs1.getInt("salary_id"));
			        salary.setJob_title(rs1.getString("job_title"));
			        salary.setSalary(rs1.getFloat("salary"));
			        allSalaries.add(salary);
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

	        return allSalaries;
	    }
			
		
	}
	

   
   
   
