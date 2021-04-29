package com.example.testcontainer.configuration.boundary;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

public class EntityManagerProducer {

    @NotNull
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Produces
    @Dependent
    public EntityManager entityManager() {
        return entityManager;
    }

}
