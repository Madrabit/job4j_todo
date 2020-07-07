package ru.job4j.todo.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.util.HibernateUtil;

import java.util.List;
import java.util.function.Function;

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

    /**
     * Wrapper. Open session -> execute Function -> commit
     *
     * @param command Function for execution: create, update, delete, etc.
     * @param <T>     Type for Funtion and returning result.
     * @return Return query result.
     */
    private <T> T tx(final Function<Session, T> command) {
        T rsl = null;
        Transaction tx = null;
        try (final Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public Task create(Task task) {
        return this.tx(
                session -> {
                    session.save(task);
                    return task;
                }
        );
    }

    @Override
    public void update(int id, boolean completed) {
        this.tx(session -> {
            Task task = findById(id);
            task.setCompleted(completed);
            session.update(task);
            return null;
        });
    }

    @Override
    public void delete(Integer id) {
        this.tx(session -> {
            Task task = new Task(null);
            task.setId(id);
            session.delete(task);
            return null;
        });
    }

    @Override
    public List<Task> findAll() {
        return this.tx(
                session -> session.createQuery("from ru.job4j.todo.model.Task").list()
        );
    }

    @Override
    public Task findById(Integer id) {
        return this.tx(
                session -> session.get(Task.class, id)
        );
    }
}
