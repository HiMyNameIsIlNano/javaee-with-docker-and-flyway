package com.example.testcontainer.configuration.entity;

import java.util.Map;
import java.util.Properties;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceProperty;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.jboss.logging.Logger;

/**
 * When an application server deploys an application, it will also validate the database schema
 * based on the JPA entities that are shipped as part of your application.
 * <p>
 * This will happen before your EJBs are deployed and started. Therefore, using an EJB with the
 * {@link Startup} annotation is too late for calling your migrations.
 * <p>
 * While there might be different solutions to this problem, I have decided to use a Hibernate
 * Integrator. It will be called <b>before</b> Hibernate validates the database schema.<br>
 * <p>
 * The class must be registered in a file named
 * <b>META-INF/services/org.hibernate.integrator.spi.Integrator</b>.<br>
 * <p>
 * The SQL files must be located at <b>src/main/resources/db/migration/</b>.
 */
public class FlywayIntegrator implements Integrator {

    private static final Logger LOGGER = Logger.getLogger(FlywayIntegrator.class.getName());

    private static final String MIGRATIONS_LOCATION = "db/migration";

    private static final String PROD_DATA_SOURCE = "java:/postgresDS";

    public void integrate(Metadata metadata,
            SessionFactoryImplementor sessionFactoryImplementor,
            SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {

        try {
            Map<String, Object> properties = sessionFactoryImplementor.getProperties();
            boolean isFlywayIntegratorEnabled = Boolean
                    .parseBoolean((String) properties.get("hibernate.flyway.integrator.enable"));

            if (!isFlywayIntegratorEnabled) {
                return;
            }

            DataSource dataSource = InitialContext.doLookup(PROD_DATA_SOURCE);
            Flyway.configure()
                    .dataSource(dataSource)
                    .locations(MIGRATIONS_LOCATION)
                    .load()
                    .migrate();

        } catch (NamingException e) {
            LOGGER.info(String.format("No data source %s found.", PROD_DATA_SOURCE));
        }
    }

    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor,
            SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
        // Nothing to do.
    }

}