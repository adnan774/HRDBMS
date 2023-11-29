package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.UserDAO;

@WebServlet("/hash-passwords")
public class HashPasswordsController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        userDAO.hashPasswords();
        response.sendRedirect("/employee");
        
        //this calls hashPasswords method from the userDAO and hashes passwords that were stored as plain text in the database
        //only run this once (already ran) ... dont run again
    }

}
