package com.example.testcontainer.configuration.boundary;

import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.Session;

public class H2TestDataSource {

    public final static String H2_PERSISTENCE_UNIT_NAME = "h2-pu";

    @PersistenceContext(unitName = "h2-pu")
    private EntityManager entityManager;

    private H2TestDataSource() {
        // for JPA.
    }

    @Inject
    public H2TestDataSource(@NotNull final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public JdbcDataSource createDataSource() throws SQLException {
        final Connection h2DbConnection = entityManager.unwrap(Session.class)
                .doReturningWork(connection -> connection);

        final JdbcDataSource h2DataSource = new JdbcDataSource();

        final String url = h2DbConnection.getMetaData().getURL();
        h2DataSource.setURL(String.format("%s;%s", url, "MODE=PostgreSQL;DB_CLOSE_DELAY=-1"));

        final String userName = h2DbConnection.getMetaData().getUserName();
        h2DataSource.setUser(userName);

        h2DataSource.setPassword("");
        return h2DataSource;
    }
}
