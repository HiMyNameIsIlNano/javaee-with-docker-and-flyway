package com.example.testcontainer.configuration.entity;

import java.util.List;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

public class TestApplicationIntegratorProvider implements IntegratorProvider {

    @Override
    public List<Integrator> getIntegrators() {
        return List.of();
    }
}
