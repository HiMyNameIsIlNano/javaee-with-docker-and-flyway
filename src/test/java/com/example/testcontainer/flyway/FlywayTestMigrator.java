package com.example.testcontainer.flyway;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

public class FlywayTestMigrator {

    private final String[] migrationLocations;

    public FlywayTestMigrator() {
        this.migrationLocations = new String[]{"db/migration"};
    }

    public FlywayTestMigrator(String... locations) {
        this.migrationLocations = locations;
    }

    public void migrate(@NotNull final PGSimpleDataSource pgSimpleDataSource) {
        initFlyway(pgSimpleDataSource);
    }

    private void initFlyway(DataSource dataSource) {
        Flyway.configure()
                .dataSource(dataSource)
                .locations(migrationLocations)
                .load()
                .migrate();
    }

}
