package com.example.testcontainer.configuration.boundary;

import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import org.hibernate.Session;
import org.postgresql.ds.PGSimpleDataSource;

public class PostgresqlTestDataSource {

    public final static String POSTGRES_PERSISTENCE_UNIT_NAME = "postgres-pu";

    public final static String PASSWORD = "postgrestest";

    private final EntityManager entityManager;

    public PostgresqlTestDataSource(@NotNull final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PGSimpleDataSource createDataSource() throws SQLException {
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

}
