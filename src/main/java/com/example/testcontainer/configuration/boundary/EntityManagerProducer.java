package com.example.testcontainer.configuration.boundary;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import org.jboss.logging.Logger;

public class EntityManagerProducer {

    private static final Logger LOGGER = Logger.getLogger(EntityManagerProducer.class.getName());

    @NotNull
    @PersistenceContext(unitName = "postgresDS")
    private EntityManager entityManager;

    @Produces
    @Dependent
    public EntityManager entityManager() {
        LOGGER.info("Producing the entity manager");
        return entityManager;
    }

}
