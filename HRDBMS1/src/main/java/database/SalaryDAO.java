package database;
import java.util.ArrayList;
import java.util.List;
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
			
		public Salaries getSalaryByID(int salary_id) {

		       openConnection();
		       oneSalary = null;
		       ResultSet rs1 = null;
		       // Creates select statement and executes it
		       try {
		           String selectSQL = "select * from salaries where salary_id=?";
		           PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
		           preparedStatement.setInt(1, salary_id);
		           rs1 = preparedStatement.executeQuery();
		           // Retrieves the results
		           while (rs1.next()) {
		        	   oneSalary = getNextSalary(rs1);
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

		       return oneSalary;
		   }   
		
		public int getSalaryIdByJobTitle(String job_title) throws SQLException {
		    openConnection();
		    String sql = "SELECT salary_id FROM salaries WHERE job_title = ?";
		    PreparedStatement preparedStatement = conn.prepareStatement(sql);
		    preparedStatement.setString(1, job_title);
		    ResultSet resultSet = preparedStatement.executeQuery();
		    int salaryId = 0;
		    if (resultSet.next()) {
		        salaryId = resultSet.getInt("salary_id");
		    }
		    closeConnection();
		    return salaryId;
		}
		
		public List<String> getAllJobTitles() {

		       openConnection();
		       List<String> jobTitles = new ArrayList<>();
		       ResultSet rs1 = null;
		       // Creates select statement and executes it
		       try {
		    	   String selectSQL = "SELECT DISTINCT job_title FROM salaries ORDER BY job_title";
		           PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
		           rs1 = preparedStatement.executeQuery();
		           // Retrieve the results
		           while (rs1.next()) {
		        	   String jobTitle = rs1.getString("job_title");
		               jobTitles.add(jobTitle);
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

		       return jobTitles;
		   }   
		
		public boolean insertSalary(Salaries sal) throws SQLException {

			openConnection();
			boolean b = false;
			try {
				String sql = "insert into salaries (job_title, salary) "
						+ "values ('" + sal.getJob_title() + "','" + sal.getSalary() + "');";
				System.out.println(sql);
				b = stmt.execute(sql);
				closeConnection();
				b = true;
			} catch (SQLException s) {
				throw new SQLException("Salary Not Added");
			}
			return b;
		}
		
		public boolean updateSalary(Salaries sal) throws SQLException {
		    openConnection();
		    boolean isSuccess = false;
		    
		    try {
		        String sql = "UPDATE salaries SET job_title = ?, salary = ? WHERE salary_id = ?";
		        PreparedStatement pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, sal.getJob_title());
		        pstmt.setFloat(2, sal.getSalary());
		        pstmt.setInt(3, sal.getSalary_id());

		        int affectedRows = pstmt.executeUpdate();
		        isSuccess = affectedRows > 0;
		        closeConnection();
		    } catch (SQLException s) {
		        throw new SQLException("Salary Not Updated");
		    }
		    return isSuccess;
		}
		
		
	}
	

   
   
   
