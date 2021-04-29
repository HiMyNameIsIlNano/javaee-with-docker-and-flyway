package com.example.testcontainer.dummy.entity;

import com.example.testcontainer.configuration.boundary.H2TestDataSource;
import com.example.testcontainer.configuration.boundary.PostgresqlTestDataSource;
import com.example.testcontainer.configuration.boundary.TestFlywayMigrator;
import com.example.testcontainer.configuration.boundary.TestTransactionManagerFactory;
import java.sql.SQLException;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

public class DummyServiceWithPostgresqlDbTest {

    private DummyService testSubject;

    private static TestTransactionManagerFactory transactionManagerFactory;

    @BeforeAll
    public static void runDbMigration() throws SQLException {
        transactionManagerFactory = TestTransactionManagerFactory
                .createEntityManagerForPersistenceUnit(
                        PostgresqlTestDataSource.POSTGRES_PERSISTENCE_UNIT_NAME);

        final EntityManager entityManager = transactionManagerFactory.getEntityManager();
        final PGSimpleDataSource postgresDataSource = new PostgresqlTestDataSource(entityManager)
                .createDataSource();
        TestFlywayMigrator.migrate(postgresDataSource);
    }

    @BeforeEach
    public void createTestSubject() {
        testSubject = new DummyService(transactionManagerFactory.getEntityManager());
    }

    @Test
    void findById() {
        Assertions.assertNotNull(testSubject);

        transactionManagerFactory.startTransaction();
        final Dummy dummy = testSubject.save(new Dummy("test"));

        transactionManagerFactory.startTransaction();
        final Optional<Dummy> dummyOptional = testSubject.findById(dummy.getId());
        Assertions.assertTrue(dummyOptional.isPresent());
        Assertions.assertEquals("test", dummyOptional.get().getText());
    }

    @Test
    void updateDummy() {
        Assertions.assertNotNull(testSubject);

        transactionManagerFactory.startTransaction();
        final Dummy dummy = testSubject.save(new Dummy("test"));

        transactionManagerFactory.startTransaction();
        final Optional<Dummy> dummyOptional = testSubject.findById(dummy.getId());
        Assertions.assertTrue(dummyOptional.isPresent());
        Assertions.assertEquals("test", dummyOptional.get().getText());

        final Dummy dummyToUpdate = dummyOptional.get();
        dummyToUpdate.setText("newValue");

        transactionManagerFactory.startTransaction();
        testSubject.update(dummyToUpdate);

        transactionManagerFactory.startTransaction();
        final Optional<Dummy> dummyAfterUpdateOptional = testSubject.findById(dummy.getId());
        Assertions.assertTrue(dummyAfterUpdateOptional.isPresent());
        Assertions.assertEquals("newValue", dummyAfterUpdateOptional.get().getText());
    }

}
