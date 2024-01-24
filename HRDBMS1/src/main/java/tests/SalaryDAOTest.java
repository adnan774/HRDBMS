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

import database.SalaryDAO;
import models.Salaries;

public class SalaryDAOTest {

    @Mock
    private Connection conn;
    @Mock
    private Statement stmt;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private SalaryDAO salaryDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Mocking database and statement behaviour
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Mocking ResultSet behaviour for multiple salaries
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false); // Mocking two salaries
        when(resultSet.getInt("salary_id")).thenReturn(1).thenReturn(2);
        when(resultSet.getString("job_title")).thenReturn("Software Engineer").thenReturn("Project Manager");
        when(resultSet.getFloat("salary")).thenReturn(60000f).thenReturn(80000f);
    }

    @Test
    public void testGetAllSalaries() throws Exception {
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);

        ArrayList<Salaries> result = salaryDAO.getAllSalaries();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Software Engineer", result.get(0).getJob_title());
        assertEquals("Project Manager", result.get(1).getJob_title());
    }

    @Test
    public void testGetSalaryByID() throws SQLException {
        int salaryId = 1;
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("salary_id")).thenReturn(salaryId);
        when(resultSet.getString("job_title")).thenReturn("Software Engineer");
        when(resultSet.getFloat("salary")).thenReturn(60000f);

        Salaries result = salaryDAO.getSalaryByID(salaryId);

        assertNotNull(result);
        assertEquals(salaryId, result.getSalary_id());
        assertEquals("Software Engineer", result.getJob_title());
        assertEquals(60000f, result.getSalary(), 0.0);
    }
    
    // Test methods with invalid salary IDs e.g. IDs that doesn't exist
    @Test
    public void testGetSalaryByInvalidID() throws SQLException {
        int invalidSalaryId = -1;
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Salaries result = salaryDAO.getSalaryByID(invalidSalaryId);

        assertNull(result);
    }

    
    @Test
    public void testInsertSalary() throws SQLException {
        Salaries newSalary = new Salaries();
        newSalary.setJob_title("Developer");
        newSalary.setSalary(50000f);

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = salaryDAO.insertSalary(newSalary);

        assertTrue(result);
    }
    
    
    // Test inserting or updating salaries with extremely high or low values
    @Test
    public void testInsertSalaryWithExtremeValue() throws SQLException {
        // Assume Float.MAX_VALUE is the extreme value
        Salaries extremeSalary = new Salaries();
        extremeSalary.setJob_title("Test");
        extremeSalary.setSalary(Float.MAX_VALUE);

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = salaryDAO.insertSalary(extremeSalary);
        assertTrue(result);
    }


    @Test
    public void testUpdateSalary() throws SQLException {
        Salaries existingSalary = new Salaries();
        existingSalary.setSalary_id(1);
        existingSalary.setJob_title("Senior Developer");
        existingSalary.setSalary(70000f);

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = salaryDAO.updateSalary(existingSalary);

        assertTrue(result);
    }

    @Test
    public void testDeleteSalary() throws SQLException {
        int salaryId = 1;
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(anyString())).thenReturn(1);

        boolean result = salaryDAO.deleteSalary(salaryId);

        assertTrue(result);
        verify(stmt).executeUpdate("delete from salaries WHERE salary_id = " + salaryId);
    }
    
    // Test how the DAO handles SQL exceptions
    @Test(expected = SQLException.class)
    public void testSQLExceptionInGetAllSalaries() throws SQLException {
        when(conn.createStatement()).thenThrow(new SQLException());
        salaryDAO.getAllSalaries();
    }

    
    

}
