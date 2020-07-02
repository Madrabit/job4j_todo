package ru.job4j.todo.servlets;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.HiberStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author madrabit on 23.06.2020
 * @version 1$
 * @since 0.1
 */
public class TodoServlet extends HttpServlet {

    /**
     * Update tasks make it completed or not.
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("ID");
        String completed = req.getParameter("completed");
        HiberStore.instOf().update(
                Integer.parseInt(id),
                Boolean.valueOf(completed)
        );
    }

    /**
     * Create JSON with all tasks.
     * Adding new Task.
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(req.getReader());
        if (node != null) {
            String name = node.get("name").asText();
            if (!name.isEmpty()) {
                HiberStore.instOf().create(
                        new Task(
                                name
                        )
                );
            }
        }
        List<Task> taskList = HiberStore.instOf().findAll();
        StringWriter writer = new StringWriter();
        objectMapper.writeValue(writer, taskList);
        String taskAsString = writer.toString();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.print(taskAsString);
        out.flush();
    }

    public static class TaskJSON {

        private int id;
        private String name;
        private Timestamp date;
        private boolean completed;

        public TaskJSON(int id, String name, Timestamp date, boolean completed) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.completed = completed;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
