package br.com.lopes.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

public class JPAUtils {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(\"persistence-unit");
    private static EntityManager entityManager = factory.createEntityManager();

    private static final Logger log = Logger.getLogger(JPAUtils.class);

    public JPAUtils(){
        entityManager = factory.createEntityManager();
    }

    public static EntityManager getEntityManager() {

        if (entityManager == null || !entityManager.isOpen()){
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public void beginTransaction(){
        entityManager.getTransaction().begin();
    }

    public void commit(){
        entityManager.getTransaction().commit();
    }

    public void close(){
        entityManager.close();
        factory.close();
    }

    public void rollBack(){
        entityManager.getTransaction().rollback();
    }

}