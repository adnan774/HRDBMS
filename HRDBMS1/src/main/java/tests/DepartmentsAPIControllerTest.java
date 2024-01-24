package tests;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;

import controllers.DepartmentsAPIController;
import database.DepartmentDAO;
import models.Departments;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;



public class DepartmentsAPIControllerTest {
	
	@Mock
    private Connection conn;

    @InjectMocks
    DepartmentsAPIController controller;
    
    private StringWriter stringWriter;
    private PrintWriter writer;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;
    
    @Mock
    private Statement mockStatement;

    @Mock
    DepartmentDAO departmentDAO;

 
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	Statement mockStatement = mock(Statement.class);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Mock the behaviour of DepartmentDAO and its dependencies
        when(departmentDAO.deleteDepartment(anyInt())).thenReturn(true);

        // Mock the creation of Statement
        when(mockStatement.executeUpdate(anyString())).thenReturn(1);

        // Mock the Connection to return the mocked Statement
        when(conn.createStatement()).thenReturn(mockStatement);

        // If DepartmentDAO requires a connection, set the mocked connection here
        // Example: if there's a setConnection method
        departmentDAO.setConnection(conn);
        
      
    }

    @Test
    public void testDoGet() throws Exception {
        ArrayList<Departments> departments = new ArrayList<>();
        departments.add(new Departments(1, "IT", "New York"));
        departments.add(new Departments(2, "HR", "London"));

        when(departmentDAO.getAllDepartments()).thenReturn(departments);

        // Using reflection to invoke protected method
        Method doGetMethod = DepartmentsAPIController.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        doGetMethod.setAccessible(true); // Make it accessible
        doGetMethod.invoke(controller, request, response);

        verify(departmentDAO, times(1)).getAllDepartments();
        writer.flush();
        assertTrue(stringWriter.toString().contains("IT"));
        assertTrue(stringWriter.toString().contains("HR"));
    }
    
    @Test
    public void testDoPost() throws Exception {
        Departments newDepartment = new Departments(3, "Finance", "Tokyo");
        String json = new Gson().toJson(newDepartment);
        StringReader stringReader = new StringReader(json);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        when(request.getReader()).thenReturn(bufferedReader);

        // Use reflection to call doPost
        Method doPostMethod = DepartmentsAPIController.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPostMethod.setAccessible(true);
        doPostMethod.invoke(controller, request, response);

        verify(departmentDAO, times(1)).insertDepartment(any(Departments.class));
        writer.flush();
        assertTrue(stringWriter.toString().contains("Finance"));
    }


    
    @Test
    public void testDoPut() throws Exception {
        Departments updatedDepartment = new Departments(1, "IT-Updated", "New York");
        String json = new Gson().toJson(updatedDepartment);
        StringReader stringReader = new StringReader(json);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        when(request.getReader()).thenReturn(bufferedReader);
        when(departmentDAO.updateDepartment(any(Departments.class))).thenReturn(true);

        // Use reflection to call doPut
        Method doPutMethod = DepartmentsAPIController.class.getDeclaredMethod("doPut", HttpServletRequest.class, HttpServletResponse.class);
        doPutMethod.setAccessible(true);
        doPutMethod.invoke(controller, request, response);

        verify(departmentDAO, times(1)).updateDepartment(any(Departments.class));
        writer.flush();
        assertTrue(stringWriter.toString().contains("IT-Updated"));
    }


    
    @Test
    public void testDoDelete() throws Exception {
        when(request.getParameter("department_id")).thenReturn("1");

        // Use reflection to call doDelete
        Method doDeleteMethod = DepartmentsAPIController.class.getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
        doDeleteMethod.setAccessible(true);
        doDeleteMethod.invoke(controller, request, response);

        verify(departmentDAO, times(1)).deleteDepartment(1);
        writer.flush();
        assertTrue(stringWriter.toString().contains("Department deleted successfully."));
    }





}
