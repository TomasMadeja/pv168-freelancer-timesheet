package cz.muni.fi.pv168.freelancertimesheet.backend;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConnectionUtils {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                shutdown();
            }
        }));
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
