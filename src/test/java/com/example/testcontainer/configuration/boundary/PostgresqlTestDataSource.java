package com.example.testcontainer.configuration.boundary;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import org.hibernate.Session;
import org.postgresql.ds.PGSimpleDataSource;

public class PostgresqlTestDataSource {

    public final static String POSTGRES_PERSISTENCE_UNIT_NAME = "postgres-pu";

    public final static int PORT = 5432;

    public final static String DOCKER_IMAGE_NAME = "postgres";

    public final static String DOCKER_IMAGE_VERSION = "12.6";

    public final static String DB_NAME = "postgres";

    public final static String USER = "postgrestest";

    public final static String PASSWORD = "postgrestest";

    @NotNull
    private EntityManager entityManager;

    @NotNull
    private final Map<String, String> persistenceUnitProperties;

    public PostgresqlTestDataSource(@NotNull final String host, @NotNull final int port) {
        this.persistenceUnitProperties = Map.of(
                "javax.persistence.jdbc.driver", "org.postgresql.Driver",
                "javax.persistence.jdbc.url", String.format("jdbc:postgresql://%s:%d/postgres", host, port),
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect",
                "javax.persistence.schema-generation.database.action", "none",
                "javax.persistence.jdbc.password", PASSWORD,
                "javax.persistence.jdbc.user", USER
        );
    }

    public PostgresqlTestDataSource withEntityManager(@NotNull final EntityManager entityManager) {
        this.entityManager = entityManager;
        return this;
    }

    public PGSimpleDataSource createDataSource() throws SQLException {
        Objects.requireNonNull(entityManager, "entityManager is null");

        final Connection postgresDbConnection = entityManager.unwrap(Session.class)
                .doReturningWork(connection -> connection);

        final PGSimpleDataSource postgresDataSource = new PGSimpleDataSource();

        final String url = postgresDbConnection.getMetaData().getURL();
        postgresDataSource.setURL(url);

        final String userName = postgresDbConnection.getMetaData().getUserName();
        postgresDataSource.setUser(userName);

        postgresDataSource.setPassword(PASSWORD);
        return postgresDataSource;
    }

    public Map<String, String> getPersistenceUnitProperties() {
        return persistenceUnitProperties;
    }
}
