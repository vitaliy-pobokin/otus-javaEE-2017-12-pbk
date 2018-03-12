package org.examples.pbk.otus.javaee.hw6.resources;

import org.examples.pbk.otus.javaee.hw6.listener.AppContextListener;

import javax.persistence.EntityManager;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionUtils {
    public static <R> R runInTransaction(Function<EntityManager, R> function) {
        EntityManager em = AppContextListener.getEmf().createEntityManager();
        em.getTransaction().begin();
        R result = function.apply(em);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public static void runInTransactionWithoutResult(Consumer<EntityManager> consumer) {
        EntityManager em = AppContextListener.getEmf().createEntityManager();
        em.getTransaction().begin();
        consumer.accept(em);
        em.getTransaction().commit();
        em.close();
    }
}
