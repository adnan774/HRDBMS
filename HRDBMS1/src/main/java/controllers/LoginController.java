package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import database.UserDAO;
import models.User;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests here, which is to display the login page
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	UserDAO userDAO = new UserDAO();
    	
    	String submittedUsername = request.getParameter("username");
        String submittedPassword = request.getParameter("password");

        // Retrieves the user by the submitted username
        User user = userDAO.getUserByUsername(submittedUsername);
        
        if (user != null) {
        	// Gets the stored hashed password from the retrieved user
        	String storedHashedPassword = user.getPassword();
        	
        	// A Check if the submitted password matches the stored hashed password
        	boolean passwordMatch = BCrypt.checkpw(submittedPassword, storedHashedPassword);
        	
        	if (passwordMatch) {
                HttpSession session = request.getSession();
                session.setAttribute("username", submittedUsername);
                session.setMaxInactiveInterval(30 * 60); // 30 minutes session to be logged in
                response.sendRedirect("./employee");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
        	request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}



