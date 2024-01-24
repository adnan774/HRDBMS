package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import database.DepartmentDAO;
import models.Departments;

public class DepartmentDAOTest {

    @Mock
    private Connection conn;
    @Mock
    private Statement stmt;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private DepartmentDAO departmentDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Mocking database and statement behaviour
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Mocking ResultSet behaviour for multiple departments
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false); // Mocking two departments
        when(resultSet.getInt("department_id")).thenReturn(1).thenReturn(2);
        when(resultSet.getString("department_name")).thenReturn("IT").thenReturn("HR");
        when(resultSet.getString("location")).thenReturn("New York").thenReturn("London");
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);

        ArrayList<Departments> result = departmentDAO.getAllDepartments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("IT", result.get(0).getDepartment_name());
        assertEquals("HR", result.get(1).getDepartment_name());
    }

    @Test
    public void testGetDepartmentByID() throws SQLException {
        int departmentId = 1;
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("department_id")).thenReturn(departmentId);
        when(resultSet.getString("department_name")).thenReturn("IT");
        when(resultSet.getString("location")).thenReturn("New York");

        Departments result = departmentDAO.getDepartmentByID(departmentId);

        assertNotNull(result);
        assertEquals(departmentId, result.getDepartment_id());
        assertEquals("IT", result.getDepartment_name());
        assertEquals("New York", result.getLocation());
    }

    @Test
    public void testInsertDepartment() throws SQLException {
        Departments newDepartment = new Departments();
        newDepartment.setDepartment_name("Finance");
        newDepartment.setLocation("San Francisco");

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = departmentDAO.insertDepartment(newDepartment);

        assertTrue(result);
    }

    @Test
    public void testUpdateDepartment() throws SQLException {
        Departments existingDepartment = new Departments();
        existingDepartment.setDepartment_id(1);
        existingDepartment.setDepartment_name("Updated IT");
        existingDepartment.setLocation("Updated Location");

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(anyString())).thenReturn(1);

        boolean result = departmentDAO.updateDepartment(existingDepartment);

        assertTrue(result);
    }

    @Test
    public void testDeleteDepartment() throws SQLException {
        int departmentId = 1;
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(anyString())).thenReturn(1);

        boolean result = departmentDAO.deleteDepartment(departmentId);

        assertTrue(result);
        verify(stmt).executeUpdate("delete from departments WHERE department_id = " + departmentId);
    }
    
 // Test how the DAO handles SQL exceptions
    @Test(expected = SQLException.class)
    public void testSQLExceptionInGetAllDepartments() throws SQLException {
        when(conn.createStatement()).thenThrow(new SQLException());
        departmentDAO.getAllDepartments();
    }

    // Test inserting or updating departments with unusual values
    // test with unusually long strings for department names and locations
    @Test
    public void testInsertDepartmentWithUnusualValues() throws SQLException {
        Departments unusualDepartment = new Departments();
        unusualDepartment.setDepartment_name(String.join("", Collections.nCopies(1000, "A"))); // Extremely long department name
        unusualDepartment.setLocation(String.join("", Collections.nCopies(1000, "B"))); // Extremely long location

        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = departmentDAO.insertDepartment(unusualDepartment);
        assertTrue(result);
    }

    // Test methods with invalid department IDs e.g. IDs that don't exist
    @Test
    public void testGetDepartmentByInvalidID() throws SQLException {
        int invalidDepartmentId = -1;
        when(conn.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Departments result = departmentDAO.getDepartmentByID(invalidDepartmentId);

        assertNull(result);
    }


}
