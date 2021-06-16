package com.example.testcontainer.configuration.entity;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.jboss.logging.Logger;
import org.postgresql.ds.PGSimpleDataSource;

public class TestFlywayIntegrator {

    private final String migrationLocation;

    public TestFlywayIntegrator() {
        this.migrationLocation = "db/migration";
    }

    public void migrate(@NotNull final JdbcDataSource jdbcDataSource) {
        initFlyway(jdbcDataSource);
    }

    public void migrate(@NotNull final PGSimpleDataSource pgSimpleDataSource) {
        initFlyway(pgSimpleDataSource);
    }

    private void initFlyway(DataSource dataSource) {
        Flyway.configure()
                .dataSource(dataSource)
                .locations(migrationLocation)
                .load()
                .migrate();
    }

}
