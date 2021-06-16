package com.example.testcontainer.configuration.entity;

import com.example.testcontainer.configuration.boundary.H2TestDataSource;
import com.example.testcontainer.configuration.boundary.TestTransactionManagerFactory;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

class FlywayMigrationTest {

    @Test
    public void flywayMigratorTest() throws SQLException {
        final EntityManager entityManager = TestTransactionManagerFactory
                .createEntityManagerForPersistenceUnit(H2TestDataSource.H2_PERSISTENCE_UNIT_NAME)
                .getEntityManager();

        final JdbcDataSource h2DataSource = new H2TestDataSource(entityManager)
                .createDataSource();

        new TestFlywayIntegrator().migrate(h2DataSource);
    }

}