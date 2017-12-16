package by.psu.logical.unit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SessionHibernate {
    private static SessionHibernate instance;
    private SessionFactory factory;

    private SessionHibernate() {
        factory = new Configuration().configure("META-INF/hibernate.cfg.xml").buildSessionFactory();
    }

    public static synchronized SessionHibernate getInstance() {
        if(instance == null)
            instance = new SessionHibernate();
        return instance;
    }

    public Session getSession() {
        return factory.openSession();
    }

    public void closeFactory(){
        factory.close();
    }
}
