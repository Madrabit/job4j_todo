package ru.job4j.todo.servlets;

import ru.job4j.todo.store.HiberStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author madrabit on 29.06.2020
 * @version 1$
 * @since 0.1
 * Delete task by ID with get request.
 */
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("ID");
        Integer intId = Integer.parseInt(id);
        HiberStore.instOf().delete(intId);
    }
}
