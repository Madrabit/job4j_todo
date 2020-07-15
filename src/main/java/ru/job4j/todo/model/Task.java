package ru.job4j.todo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author madrabit on 23.06.2020
 * @version 1$
 * @since 0.1
 */

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Timestamp date;
    private boolean completed;


    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {
    }

    public Task(String name) {
        this.name = name;
        this.date = new Timestamp(System.currentTimeMillis());
    }


    public static Task of(String name, User user) {
        Task task = new Task();
        task.name = name;
        task.user = user;
        task.date = new Timestamp(System.currentTimeMillis());
        return task;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", desc=" + name +
                ", date=" + date +
                ", completed=" + completed +
                '}';
    }
}
