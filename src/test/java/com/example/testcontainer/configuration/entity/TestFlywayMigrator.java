package com.example.testcontainer.configuration.entity;

import javax.validation.constraints.NotNull;
import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class TestFlywayMigrator {

    public static void migrate(@NotNull final JdbcDataSource jdbcDataSource) {
        new FlywayMigrator(jdbcDataSource);
    }

    public static void migrate(@NotNull final PGSimpleDataSource pgSimpleDataSource) {
        new FlywayMigrator(pgSimpleDataSource);
    }

}
