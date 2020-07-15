package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;

/**
 * @author madrabit on 23.06.2020
 * @version 1$
 * @since 0.1
 * TodoList tasks store.
 */
public interface Store {

    /**
     * Create new Task.
     * @param task New task.
     * @return Return new task object.
     */
    Task create(Task task);

    /**
     * Make task completed.
     * @param id Task id.
     * @param completed isCompleted.
     */
    void update(int id, boolean completed);

    /**
     * Delete task.
     * @param id Task id.
     */
    void delete(Integer id);

    /**
     * Find all tasks.
     * @return Return tasks.
     */
    List<Task> findAll(String email);

    /**
     * Find task by id.
     * @param id Task id.
     * @return Task.
     */
    Task findById(Integer id);

    /**
     * Find User by email.
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * Add new User.
     * @param user
     */
    void addUser(User user);
}
