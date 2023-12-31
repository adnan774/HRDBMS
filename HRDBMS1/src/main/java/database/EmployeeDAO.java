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
	
	public Employees getEmployeeByID(int emp_id) {

	       openConnection();
	       oneEmployee = null;
	       ResultSet rs1 = null;
	       // Create select statement and executes it
	       try {
	    	   System.out.println("emp_id: " + emp_id);
	    	   String selectSQL = "SELECT employees.*, departments.department_name, departments.location, salaries.job_title, salaries.salary " +
	                   "FROM employees " +
	                   "JOIN departments ON employees.department_id = departments.department_id " +
	                   "JOIN salaries ON employees.salary_id = salaries.salary_id " +
	                   "WHERE employees.emp_id = ?";

	           PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
	           preparedStatement.setInt(1, emp_id);
	           rs1 = preparedStatement.executeQuery();
	           System.out.println("SQL query executed");
	           // Retrieves the results
	           while (rs1.next()) {
	               oneEmployee = getNextEmployee(rs1);
	               System.out.println("Fetched oneEmployee: " + oneEmployee);
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

	       return oneEmployee;
	   }   
	
	 public boolean insertEmployee(Employees e, int departmentId, int salaryId) throws SQLException {
		    openConnection();
		    boolean b = false;
		    try {
		        String sql = "INSERT INTO employees (first_name, last_name, email, date_of_birth, phone_number, hire_date, address,"
		        		+ " city, town, post_code, department_id, salary_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		        PreparedStatement preparedStatement = conn.prepareStatement(sql);
		        preparedStatement.setString(1, e.getFirst_name());
		        preparedStatement.setString(2, e.getLast_name());
		        preparedStatement.setString(3, e.getEmail());
		        preparedStatement.setString(4, e.getDob());
		        preparedStatement.setString(5, e.getPhone_number());
		        preparedStatement.setString(6, e.getHire_date());
		        preparedStatement.setString(7, e.getAddress());
		        preparedStatement.setString(8, e.getCity());
		        preparedStatement.setString(9, e.getTown());
		        preparedStatement.setString(10, e.getPost_code());
		        preparedStatement.setInt(11, departmentId);
		        preparedStatement.setInt(12, salaryId);
		        preparedStatement.executeUpdate();
		        b = true;
		    } catch (SQLException s) {
		    	s.printStackTrace(); 
		        throw new SQLException("Employee Not Added");
		    } finally {
		        closeConnection();
		    }
		    return b;
		}
	 
	 public boolean updateEmployee(Employees e, int departmentId, int salaryId) throws SQLException {
		    openConnection();
		    boolean b = false;
		    try {
		        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, date_of_birth = ?, phone_number = ?, hire_date = ?, address = ?, "
		        		+ "city = ?, town = ?, post_code = ?, department_id = ?, salary_id = ? WHERE emp_id = ?";
		        PreparedStatement preparedStatement = conn.prepareStatement(sql);
		        preparedStatement.setString(1, e.getFirst_name());
		        preparedStatement.setString(2, e.getLast_name());
		        preparedStatement.setString(3, e.getEmail());
		        preparedStatement.setString(4, e.getDob());
		        preparedStatement.setString(5, e.getPhone_number());
		        preparedStatement.setString(6, e.getHire_date());
		        preparedStatement.setString(7, e.getAddress());
		        preparedStatement.setString(8, e.getCity());
		        preparedStatement.setString(9, e.getTown());
		        preparedStatement.setString(10, e.getPost_code());
		        preparedStatement.setInt(11, departmentId);
		        preparedStatement.setInt(12, salaryId);
		        preparedStatement.setInt(13, e.getEmp_id());
		        preparedStatement.executeUpdate();
		        b = true;
		    } catch (SQLException s) {
		        s.printStackTrace(); 
		        throw new SQLException("Employee Not Updated");
		    } finally {
		        closeConnection();
		    }
		    return b;
		}
	 
	 public boolean deleteEmployee(int emp_id) throws SQLException {
			
			openConnection();
			boolean b = false;
			try {	
				String sql = "delete from employees WHERE emp_id = "+ emp_id;
				System.out.println(sql);
			
				stmt.executeUpdate(sql);
		
				closeConnection();
				b = true;
			} catch (SQLException s) {
				throw new SQLException("Employee Not Deleted");
			}
			return b;
		}
	 
	//Main Search 
		public ArrayList<Employees> searchEmployees(String searchStr) {
		    ArrayList<Employees> allEmployees = new ArrayList<Employees>();
		    openConnection();
		    ResultSet rs1 = null;

		    try {
		        String sql = "SELECT * FROM employees e "
		            + "JOIN departments d ON e.department_id = d.department_id "
		            + "JOIN salaries s ON e.salary_id = s.salary_id "
		            + "WHERE e.first_name LIKE ? OR e.last_name LIKE ? OR e.email LIKE ? "
		            + "OR e.date_of_birth LIKE ? OR e.phone_number LIKE ? OR e.hire_date LIKE ? "
		            + "OR e.address LIKE ? OR e.city LIKE ? OR e.town LIKE ? OR e.post_code LIKE ? "
		            + "OR d.department_name LIKE ? OR s.job_title LIKE ? OR s.salary LIKE ? "
		            + "ORDER BY e.emp_id";
		        PreparedStatement preparedStatement = conn.prepareStatement(sql);
		        for (int i = 1; i <= 13; i++) {
		            preparedStatement.setString(i, "%" + searchStr + "%");
		        }
		        rs1 = preparedStatement.executeQuery();

		        while (rs1.next()) {
		            Employees oneEmployee = getNextEmployee(rs1);
		            allEmployees.add(oneEmployee);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
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
	

   
   
   
