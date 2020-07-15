package ru.job4j.todo.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author madrabit on 03.05.2020
 * @version 1$
 * @since 0.1
 * Logout user.
 */
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        session.invalidate();
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
