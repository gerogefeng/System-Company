package by.psu.logical.unit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Vector;


public class SessionHibernate {
    private static final int MAX_COUNT_SESSION = 1000;
    private static SessionHibernate instance;
    private SessionFactory factory;
    private Vector<Session> availableSession = new Vector<>();
    private Vector<Session> usedSession = new Vector<>();

    private SessionHibernate() {
        factory = new Configuration().configure("META-INF/hibernate.cfg.xml").buildSessionFactory();
    }

    public static synchronized SessionHibernate getInstance() {
        if(instance == null) {
            instance = new SessionHibernate();
            for (int i = 0; i < MAX_COUNT_SESSION; i++)
                instance.availableSession.add(instance.factory.openSession());
        }
        return instance;
    }

    public synchronized Session retriver(){
        Session session = null;
        if(!availableSession.isEmpty()) {
            session = availableSession.lastElement();
            availableSession.removeElement(session);
        } else {
            session = factory.openSession();
        }
        usedSession.add(session);
        return session;
    }

    public synchronized void putback(Session session){
        if(session != null) {
            if(usedSession.removeElement(session))
                availableSession.add(session);
        } else {
            throw new NullPointerException("Session not finding");
        }
    }

    private Session getSession() {
        return factory.openSession();
    }

    public void closeFactory(){
        if(instance != null)
            factory.close();
    }
}
