package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.util.HibernateUtil;

import java.util.List;

/**
 * @author madrabit on 23.06.2020
 * @version 1$
 * @since 0.1
 */
public class HiberStore implements Store {

    private static final HiberStore INST = new HiberStore();
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static Store instOf() {
        return INST;
    }


    @Override
    public Task create(Task task) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(task);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.save(task);
//        session.getTransaction().commit();
//        session.close();
        return task;
    }

    @Override
    public void update(int id, boolean completed) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Task task = findById(id);
        task.setCompleted(completed);
        session.update(task);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Task task = new Task(null);
        task.setId(id);
        session.delete(task);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Task> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("from ru.job4j.todo.model.Task").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Task findById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
