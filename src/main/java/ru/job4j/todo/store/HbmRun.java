package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.util.HibernateUtil;

import java.util.List;

/**
 * @author madrabit on 07.07.2020
 * @version 1$
 * @since 0.1
 */
public class HbmRun {

    public static void main(String[] args) {

        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            User user = create(User.of("asd@asf", "123a"), sessionFactory);
            create(Task.of("Do smth", user), sessionFactory);
            for (Task task : findAll(Task.class, sessionFactory)) {
                System.out.println(task.getName() + " " + task.getUser().getEmail());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + cl.getName(), cl).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
