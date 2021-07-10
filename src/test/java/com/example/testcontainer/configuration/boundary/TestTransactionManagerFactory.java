package com.example.testcontainer.configuration.boundary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

public class TestTransactionManagerFactory {

    private final EntityManager entityManager;

    private TestTransactionManagerFactory(@NotNull final String persistenceUnitName,
            @NotNull final Map<String, String> persistenceUnitProperties) {
        entityManager = Persistence
                .createEntityManagerFactory(persistenceUnitName, persistenceUnitProperties)
                .createEntityManager();
    }

    public static TestTransactionManagerFactory createEntityManagerForPersistenceUnit(
            @NotNull final String persistenceUnitName,
            @NotNull final Map<String, String> persistenceUnitProperties) {
        return new TestTransactionManagerFactory(persistenceUnitName, persistenceUnitProperties);
    }

    public EntityTransaction startTransaction() {
        return entityManager.getTransaction();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ResultSet performQuery(DataSource dataSource, String sql) throws SQLException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();

        resultSet.next();
        return resultSet;
    }
}
