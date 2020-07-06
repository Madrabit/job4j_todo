package ru.job4j.todo.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.util.HibernateUtil;

import java.util.List;

/**
 * @author madrabit on 23.06.2020
 * @version 1$
 * @since 0.1
 */
public class HiberStore implements Store {

    private static final Logger LOG = LogManager.getLogger(HiberStore.class.getName());

    private static final HiberStore INST = new HiberStore();
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static Store instOf() {
        return INST;
    }


    @Override
    public Task create(Task task) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(e.getMessage(), e);
        }
        return task;
    }

    @Override
    public void update(int id, boolean completed) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Task task = findById(id);
            task.setCompleted(completed);
            session.update(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            Task task = new Task(null);
            task.setId(id);
            session.delete(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Task> findAll() {
        Transaction transaction = null;
        List<Task> result = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.createQuery("from ru.job4j.todo.model.Task").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Task findById(Integer id) {
        Transaction transaction = null;
        Task result = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.get(Task.class, id);
            session.getTransaction().commit();
        }
        return result;
    }
}
