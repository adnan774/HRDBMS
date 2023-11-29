package database;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.Departments;
import models.Employees;
import models.Salaries;

import java.sql.*;


public class DepartmentDAO {
	
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
	
	//Departments Queries
	
	private Departments getNextDepartment(ResultSet rs){
		Departments thisDepartment=null;
		try {
			thisDepartment = new Departments(
					rs.getInt("department_id"),
					rs.getString("department_name"),
					rs.getString("location"));	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return thisDepartment;		
	}	
	
	public ArrayList<Departments> getAllDepartments(){
		   
		ArrayList<Departments> allDepartments= new ArrayList<Departments>();
		openConnection();
		ResultSet rs1 = null;

	   
		try{
		    String selectSQL = "SELECT department_id, department_name, location FROM departments;";
		    rs1 = stmt.executeQuery(selectSQL);
	    
		    while (rs1.next()) {
		    	Departments department = new Departments();
		        department.setDepartment_id(rs1.getInt("department_id"));
		        department.setDepartment_name(rs1.getString("department_name"));
		        department.setLocation(rs1.getString("location"));
		        allDepartments.add(department);
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

        return allDepartments;
    }
	
	public Departments getDepartmentByID(int department_id) {

	       openConnection();
	       oneDepartment = null;
	       ResultSet rs1 = null;
	       // Creates select statement and executes it
	       try {
	           String selectSQL = "select * from departments where department_id=?";
	           PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
	           preparedStatement.setInt(1, department_id);
	           rs1 = preparedStatement.executeQuery();
	           // Retrieves the results
	           while (rs1.next()) {
	        	   oneDepartment = getNextDepartment(rs1);
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

	       return oneDepartment;
	   }
	
	public List<String> getAllDepartmentNames() {

	       openConnection();
	       List<String> departmentNames = new ArrayList<>();
	       ResultSet rs1 = null;
	       // Creates select statement and executes it
	       try {
	    	   String selectSQL = "SELECT DISTINCT department_name FROM departments ORDER BY department_name";
	           PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
	           rs1 = preparedStatement.executeQuery();
	           // Retrieves the results
	           while (rs1.next()) {
	        	   String departmentName = rs1.getString("department_name");
	        	   departmentNames.add(departmentName);
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

	       return departmentNames;
	   } 
	
	public int getDepartmentIdByName(String department_name) throws SQLException {
	    openConnection();
	    String sql = "SELECT department_id FROM departments WHERE department_name = ?";
	    PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    preparedStatement.setString(1, department_name);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    int departmentId = 0;
	    if (resultSet.next()) {
	        departmentId = resultSet.getInt("department_id");
	    }
	    closeConnection();
	    return departmentId;
	}
	
	
		
				
	}
	

   
   
   
