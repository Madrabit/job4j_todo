package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.*;

/**
 * @author madrabit on 04.05.2020
 * @version 1$
 * @since 0.1
 */
public class StoreStub implements Store {
    private final Map<Integer, Task> taskMap = new HashMap<>();

    private int taskIds = 0;

    @Override
    public Task create(Task task) {
        task.setId(taskIds++);
        taskMap.put(task.getId(), task);
        return taskMap.get(task.getId());
    }

    @Override
    public void update(int id, boolean completed) {

    }

//    @Override
//    public void update(Task task) {
//        taskMap.put(task.getId(), task);
//    }

    @Override
    public void delete(Integer id) {
        taskMap.remove(id);
    }

    @Override
    public List<Task> findAll(String email) {
        return null;
    }

    @Override
    public Task findById(Integer id) {
        return taskMap.get(id);
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }
}
