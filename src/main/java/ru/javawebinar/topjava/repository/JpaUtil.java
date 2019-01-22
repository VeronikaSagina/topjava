package ru.javawebinar.topjava.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaUtil {

    @PersistenceContext
    private EntityManager entityManager;

    public void clear2ndLevelHibernateCache() {
        Session session = (Session) entityManager.getDelegate();
        SessionFactory sessionFactory = session.getSessionFactory();
        sessionFactory.getCache().evictAllRegions();
    }
}
