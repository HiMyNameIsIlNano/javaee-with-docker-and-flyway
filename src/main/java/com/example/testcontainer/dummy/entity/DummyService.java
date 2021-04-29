package com.example.testcontainer.dummy.entity;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Transactional
@ApplicationScoped
public class DummyService {

    @NotNull
    private EntityManager entityManager;

    public DummyService() {
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
}
