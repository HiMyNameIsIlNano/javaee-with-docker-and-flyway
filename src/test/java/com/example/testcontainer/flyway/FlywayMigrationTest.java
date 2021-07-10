package com.example.testcontainer.flyway;

import static com.example.testcontainer.helper.boundary.PostgresqlTestDataSource.DB_NAME;
import static com.example.testcontainer.helper.boundary.PostgresqlTestDataSource.DOCKER_IMAGE_NAME;
import static com.example.testcontainer.helper.boundary.PostgresqlTestDataSource.DOCKER_IMAGE_VERSION;
import static com.example.testcontainer.helper.boundary.PostgresqlTestDataSource.PASSWORD;
import static com.example.testcontainer.helper.boundary.PostgresqlTestDataSource.PORT;
import static com.example.testcontainer.helper.boundary.PostgresqlTestDataSource.POSTGRES_PERSISTENCE_UNIT_NAME;
import static com.example.testcontainer.helper.boundary.PostgresqlTestDataSource.USER;

import com.example.testcontainer.helper.boundary.PostgresqlTestDataSource;
import com.example.testcontainer.helper.entity.TransactionManagerTestHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class FlywayMigrationTest {

    private static PostgresqlTestDataSource POSTGRES_TEST_DATASOURCE;

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(
            String.format("%s:%s", DOCKER_IMAGE_NAME, DOCKER_IMAGE_VERSION))
            .withDatabaseName(DB_NAME)
            .withExposedPorts(PORT)
            .withUsername(USER)
            .withPassword(PASSWORD);

    @BeforeAll
    public static void runDbMigration() {
        Assertions.assertTrue(POSTGRESQL_CONTAINER.isRunning());

        final String host = POSTGRESQL_CONTAINER.getHost();
        final Integer port = POSTGRESQL_CONTAINER
                .getMappedPort(PORT);

        POSTGRES_TEST_DATASOURCE = new PostgresqlTestDataSource(host, port);
    }

    @Test
   public void testFlywayMigration() throws SQLException {
        TransactionManagerTestHelper transactionManagerFactory = TransactionManagerTestHelper
                .createEntityManagerForPersistenceUnit(
                        POSTGRES_PERSISTENCE_UNIT_NAME,
                        POSTGRES_TEST_DATASOURCE.getPersistenceUnitProperties()
                );

        final EntityManager entityManager = transactionManagerFactory.getEntityManager();
        final PGSimpleDataSource postgresDataSource = POSTGRES_TEST_DATASOURCE
                .withEntityManager(entityManager)
                .createDataSource();
        new FlywayTestMigrator().migrate(postgresDataSource);

        ResultSet resultSet = transactionManagerFactory.performQuery(postgresDataSource,
                "SELECT version, description FROM flyway_schema_history");

        String migrationVersionFromDb = resultSet.getString(1);
        String migrationDescriptionFromDb = resultSet.getString(2);
        Assertions.assertEquals("1", migrationVersionFromDb);
        Assertions.assertEquals( "Create dummy table", migrationDescriptionFromDb);
    }
}