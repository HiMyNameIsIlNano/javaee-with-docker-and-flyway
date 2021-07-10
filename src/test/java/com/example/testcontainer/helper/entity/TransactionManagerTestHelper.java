package com.example.testcontainer.helper.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

public class TransactionManagerTestHelper {

    private final EntityManager entityManager;

    private TransactionManagerTestHelper(@NotNull final String persistenceUnitName,
            @NotNull final Map<String, String> persistenceUnitProperties) {
        entityManager = Persistence
                .createEntityManagerFactory(persistenceUnitName, persistenceUnitProperties)
                .createEntityManager();
    }

    public static TransactionManagerTestHelper createEntityManagerForPersistenceUnit(
            @NotNull final String persistenceUnitName,
            @NotNull final Map<String, String> persistenceUnitProperties) {
        return new TransactionManagerTestHelper(persistenceUnitName, persistenceUnitProperties);
    }

    public EntityTransaction startTransaction() {
        return entityManager.getTransaction();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ResultSet performQuery(@NotNull final DataSource dataSource, @NotNull final String sql) throws SQLException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();

        resultSet.next();
        return resultSet;
    }
}
