package com.example.testcontainer.configuration.entity;

import javax.ejb.DependsOn;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.flywaydb.core.Flyway;

@Startup
@Singleton
@DependsOn(value = "dataSource")
public class FlywayMigrator {

    public static final String MIGRATIONS_LOCATION = "db";

    public FlywayMigrator() {
    }

    @Inject
    public FlywayMigrator(@NotNull final DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(MIGRATIONS_LOCATION)
                .load();

        flyway.migrate();
    }

}
