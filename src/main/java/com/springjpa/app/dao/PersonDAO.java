package com.springjpa.app.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * @author Ivan L
 */
@Component
public class PersonDAO {
    private final EntityManager entityManager;
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
