package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import database.EmployeeDAO;
import models.Employees;
import models.Departments;
import models.Salaries;

public class EmployeeDAOTest {

    @Mock
    private Connection conn;
    @Mock
    private Statement stmt;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private EmployeeDAO employeeDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Mocking database and statement behaviour
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);

        // Mocking ResultSet behaviour for multiple employees
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true) // ... add more if needed
                               .thenReturn(false); // End of ResultSet
        // Mocking data for first employee
        when(resultSet.getInt("emp_id")).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4).thenReturn(5).thenReturn(6).thenReturn(55); 
        when(resultSet.getString("first_name")).thenReturn("Johne").thenReturn("Jane").thenReturn("Janett").thenReturn("Barry").thenReturn("Andrew").thenReturn("Rickkkk").thenReturn("John");  
        when(resultSet.getString("last_name")).thenReturn("Johne").thenReturn("Jane").thenReturn("Janett").thenReturn("Barry").thenReturn("Andrew").thenReturn("Rickkkk").thenReturn("John");
        when(resultSet.getString("email")).thenReturn("john.doee@example.co.uk").thenReturn("jane.smith@example.co.uk").thenReturn("Janett.Sam@example.co.uk").thenReturn("Barry.Ross@example.co.uk").thenReturn("Andrew.Huberman@example.co.uk").thenReturn("Rick.Thompson@example.co.uk").thenReturn("jp6@gmail.com");
        when(resultSet.getString("date_of_birth")).thenReturn("2021-08-16").thenReturn("1985-03-15").thenReturn("1985-03-15").thenReturn("1985-03-15").thenReturn("1985-03-15").thenReturn("1985-03-15").thenReturn("1990-01-02");
        when(resultSet.getString("phone_number")).thenReturn("07123 456786").thenReturn("07234 567890").thenReturn("07234 567890").thenReturn("07234 567890").thenReturn("07234 567890").thenReturn("07234 567890").thenReturn("07595642858");
        when(resultSet.getString("hire_date")).thenReturn("2021-08-06").thenReturn("2021-08-15").thenReturn("2021-08-15").thenReturn("2021-08-15").thenReturn("2021-08-15").thenReturn("2021-08-15").thenReturn("2022-01-01");
        when(resultSet.getString("address")).thenReturn("123 Highh St").thenReturn("456 Elm St").thenReturn("488 Elm St").thenReturn("422 Elm St").thenReturn("472 Elm St").thenReturn("416 Elm St").thenReturn("8 Chase");
        when(resultSet.getString("city")).thenReturn("London").thenReturn("Manchester").thenReturn("Manchester").thenReturn("Manchester").thenReturn("Manchester").thenReturn("Manchester").thenReturn("Manchester");
        when(resultSet.getString("town")).thenReturn("Islington").thenReturn("Didsbury").thenReturn("Didsbury").thenReturn("Didsbury").thenReturn("Didsbury").thenReturn("Didsbury").thenReturn("Bolton");
        when(resultSet.getString("postcode")).thenReturn("N1 1AA").thenReturn("M20 6RA").thenReturn("M20 6RA").thenReturn("M20 6RA").thenReturn("M20 6RA").thenReturn("M20 6RA").thenReturn("BL1 3UU");

        
    }
    
    @Test
    public void testGetAllEmployees() throws Exception {
        ArrayList<Employees> result = employeeDAO.getAllEmployees();

        // Assertions
        assertNotNull(result);
        assertEquals(7, result.size()); // Assuming you're mocking two employees
        assertEquals("Johne", result.get(0).getFirst_name());
        assertEquals("Jane", result.get(1).getFirst_name());
        assertEquals("Janett", result.get(2).getFirst_name());
        assertEquals("Barry", result.get(3).getFirst_name());
        assertEquals("Andrew", result.get(4).getFirst_name());
        assertEquals("Rickkkk", result.get(5).getFirst_name());
        assertEquals("John", result.get(6).getFirst_name());
        // ... other assertions based on mocked data
    }
    
    
    // Test for Exception Handling 
    @Test(expected = SQLException.class)
    public void testGetAllEmployeesSQLException() throws Exception {
    	// Arrange
        when(conn.createStatement()).thenThrow(new SQLException());

        // Act
        employeeDAO.getAllEmployees();
    }
    
    
    // Test for Empty ResultSet
    @Test
    public void testGetAllEmployeesEmptyResultSet() throws Exception {
        when(resultSet.next()).thenReturn(false); // ResultSet is empty
        ArrayList<Employees> result = employeeDAO.getAllEmployees();
        assertTrue(result.isEmpty());
    }
    
    // Test for Multiple Records
    @Test
    public void testGetAllEmployeesMultipleRecords() throws Exception {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false); // Two records in ResultSet

        // Mock the first employee
        when(resultSet.getInt("emp_id")).thenReturn(1);
        when(resultSet.getString("first_name")).thenReturn("Johne");
        //... other fields

        // Setup for the second employee
        when(resultSet.getInt("emp_id")).thenReturn(2);
        when(resultSet.getString("first_name")).thenReturn("Jane");
        //... other fields

        ArrayList<Employees> result = employeeDAO.getAllEmployees();
        
        assertEquals(7, result.size());
        assertEquals("Johne", result.get(0).getFirst_name());
        assertEquals("Jane", result.get(1).getFirst_name());
        assertEquals("Janett", result.get(2).getFirst_name());
        assertEquals("Barry", result.get(3).getFirst_name());
        assertEquals("Andrew", result.get(4).getFirst_name());
        assertEquals("Rickkkk", result.get(5).getFirst_name());
        assertEquals("John", result.get(6).getFirst_name());
        //... other assertions
    }
    
    @Test
    public void testGetEmployeeByID() throws SQLException {
        int empId = 1;
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        
        // Mock expected data
        when(resultSet.getInt("emp_id")).thenReturn(empId);
        // mock other fields ...

        Employees result = employeeDAO.getEmployeeByID(empId);

        assertNotNull(result);
        assertEquals(empId, result.getEmp_id());
        // more assertions ...
    }

    
    @Test
    public void testInsertEmployee() throws SQLException {
        // Creating a new employee object using no-argument constructor
        Employees newEmployee = new Employees();

        // Setting properties using setters
        newEmployee.setFirst_name("Johny");
        newEmployee.setLast_name("Doey");
        newEmployee.setEmail("johny.doe@example.com");
        newEmployee.setDob("1980-01-01");
        newEmployee.setPhone_number("07345678901");
        newEmployee.setHire_date("2020-01-01");
        newEmployee.setAddress("123 Main St");
        newEmployee.setCity("Manchester");
        newEmployee.setTown("Rochdale");
        newEmployee.setPost_code("R1 2AR");
        newEmployee.setDepartments(new Departments(4, "Health & Beauty", "Manchester"));
        newEmployee.setSalaries(new Salaries(2, "Assistant Manager", 25000f));

        // Setting up mock behaviour
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // departmentId and salaryId are defined in the test class
        int departmentId = 4; //  department ID
        int salaryId = 3; //  salary ID

        // Act - Calling the method under test
        boolean result = employeeDAO.insertEmployee(newEmployee, departmentId, salaryId);

        // Assert: Verifying the result
        assertTrue(result);
    }
    
     
    @Test
    public void testUpdateEmployee() throws SQLException {
        // Creating an existing employee object using no-argument constructor
        Employees existingEmployee = new Employees();

        // Setting properties using setters
        existingEmployee.setEmp_id(1); // Assuming this employee already has an ID
        existingEmployee.setFirst_name("John-Update");
        existingEmployee.setLast_name("Doe-Update");
        existingEmployee.setEmail("john.updated@example.com");
        existingEmployee.setDob("1980-01-02"); 
        existingEmployee.setPhone_number("0333 332 3800");
        existingEmployee.setHire_date("2021-01-01");
        existingEmployee.setAddress("456 Another St");
        existingEmployee.setCity("Leads");
        existingEmployee.setTown("LeadsTown");
        existingEmployee.setPost_code("LL1 2LL");
        existingEmployee.setDepartments(new Departments(1, "Clothing", "Manchester")); // Updated department
        existingEmployee.setSalaries(new Salaries(20, "Software Engineer", 65000f)); // Updated salary

        // Setting up mock behaviour
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Assuming departmentId and salaryId are already defined in your test class
        int departmentId = 1; // Updated department ID
        int salaryId = 20; // Updated salary ID

        // Act: Calling the method under test
        boolean result = employeeDAO.updateEmployee(existingEmployee, departmentId, salaryId);

        // Assert: Verifying the result
        assertTrue(result);
    }

    
    @Test
    public void testDeleteEmployee() throws SQLException {
        // Arrange
        int empId = 56;
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(anyString())).thenReturn(1);

        // Act
        boolean result = employeeDAO.deleteEmployee(empId);

        // Assert
        assertTrue(result);

        // Verify that the correct SQL command is executed
        verify(stmt).executeUpdate("delete from employees WHERE emp_id = " + empId);
    }

    
    @Test
    public void testSearchEmployees() throws SQLException {
        String searchStr = "John";
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        // Mock expected data matching "John"

        ArrayList<Employees> result = employeeDAO.searchEmployees(searchStr);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Additional assertions based on mock data
    }


}
