package com.example.testcontainer.dummy.entity;

import com.example.testcontainer.configuration.boundary.PostgresqlTestDataSource;
import com.example.testcontainer.configuration.boundary.TestTransactionManagerFactory;
import com.example.testcontainer.configuration.entity.TestFlywayIntegrator;
import com.example.testcontainer.dummy.entity.Dummy.DummyBuilder;
import java.sql.SQLException;
import java.util.Optional;
import javax.persistence.EntityManager;
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
        new TestFlywayIntegrator().migrate(postgresDataSource);
    }

    @BeforeEach
    public void createTestSubject() {
        testSubject = new DummyService(transactionManagerFactory.getEntityManager());
    }

    @Test
    void findById() {
        Assertions.assertNotNull(testSubject);

        transactionManagerFactory.startTransaction();
        final Dummy testObject = DummyBuilder.forCreation()
                .withText("test")
                .build();
        final Dummy dummy = testSubject.save(testObject);

        transactionManagerFactory.startTransaction();
        final Optional<Dummy> dummyOptional = testSubject.findById(dummy.getId());
        Assertions.assertTrue(dummyOptional.isPresent());
        Assertions.assertEquals("test", dummyOptional.get().getText());
    }

    @Test
    void updateDummy() {
        Assertions.assertNotNull(testSubject);

        transactionManagerFactory.startTransaction();
        final Dummy testObject = DummyBuilder.forCreation()
                .withText("test")
                .build();
        final Dummy dummy = testSubject.save(testObject);

        transactionManagerFactory.startTransaction();
        final Optional<Dummy> dummyOptional = testSubject.findById(dummy.getId());
        Assertions.assertTrue(dummyOptional.isPresent());
        Assertions.assertEquals("test", dummyOptional.get().getText());

        final Dummy dummyToUpdate = dummyOptional.get();
        final Dummy updatedTestObject = DummyBuilder.forUpdate(dummyToUpdate)
                .withText("newValue")
                .build();

        transactionManagerFactory.startTransaction();
        final Dummy testObjectAfterUpdate = testSubject.update(updatedTestObject);

        transactionManagerFactory.startTransaction();
        final Optional<Dummy> dummyAfterUpdateOptional = testSubject
                .findById(testObjectAfterUpdate.getId());
        Assertions.assertTrue(dummyAfterUpdateOptional.isPresent());
        Assertions.assertEquals("newValue", dummyAfterUpdateOptional.get().getText());
    }

}
