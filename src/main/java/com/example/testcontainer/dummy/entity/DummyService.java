package com.example.testcontainer.dummy.entity;

import java.math.BigInteger;
import java.util.Optional;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Transactional
@Singleton
public class DummyService {

    @NotNull
    private EntityManager entityManager;

    public DummyService() {
        // Do not remove. For DI.
    }

    @Inject
    public DummyService(@NotNull final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Dummy save(final Dummy dummy) {
        entityManager.persist(dummy);
        return dummy;
    }

    public Dummy update(final Dummy dummy) {
        entityManager.merge(dummy);
        return dummy;
    }

    public Optional<Dummy> findById(final long id) {
        final Dummy dummy = entityManager.find(Dummy.class, id);
        return Optional.ofNullable(dummy);
    }

    public Long count() {
        // TODO: 10.07.21 check why using the Entity Name this does not work
        final Query query = entityManager
                .createNativeQuery("SELECT count(1) FROM TEST_TABLE");

        return ((BigInteger) query.getSingleResult()).longValue();
    }
}
