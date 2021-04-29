package com.example.testcontainer.configuration.boundary;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;

public class TestTransactionManagerFactory {

    private final EntityManager entityManager;

    private TestTransactionManagerFactory(final String persistenceUnitName) {
        entityManager = Persistence.createEntityManagerFactory(persistenceUnitName)
                .createEntityManager();
    }

    public static TestTransactionManagerFactory createEntityManagerForPersistenceUnit(@NotNull final String persistenceUnitName) {
        return new TestTransactionManagerFactory(persistenceUnitName);
    }

    public EntityTransaction startTransaction() {
        return entityManager.getTransaction();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
