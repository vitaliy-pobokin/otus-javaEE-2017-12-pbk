package org.examples.pbk.otus.javaee.hw6.resources;

import org.examples.pbk.otus.javaee.hw6.listener.AppContextListener;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionUtils {
    public static <R> R runInTransaction(Function<EntityManager, R> function) {
        Session session = AppContextListener.getSessionFactory().openSession();
        session.getTransaction().begin();
        R result = function.apply(session);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static void runInTransactionWithoutResult(Consumer<EntityManager> consumer) {
        Session session = AppContextListener.getSessionFactory().openSession();
        session.getTransaction().begin();
        consumer.accept(session);
        session.getTransaction().commit();
        session.close();
    }
}
