package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * @author madrabit on 23.06.2020
 * @version 1$
 * @since 0.1
 */
public class HiberMain {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Task task = create(new Task("Learn Hibernate"), sf);
            System.out.println(task);
            task.setName("Learn Hibernate 5.");
            update(task, sf);
            System.out.println(task);
            Task rsl = findById(task.getId(), sf);
            System.out.println(rsl);
            delete(rsl.getId(), sf);
            List<Task> list = findAll(sf);
            for (Task it : list) {
                System.out.println(it);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Task create(Task task, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public static void update(Task task, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task task = new Task(null);
        task.setId(id);
        session.delete(task);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Task> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task>  result = session.createQuery("from ru.job4j.todo.model.Task").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static Task findById(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
