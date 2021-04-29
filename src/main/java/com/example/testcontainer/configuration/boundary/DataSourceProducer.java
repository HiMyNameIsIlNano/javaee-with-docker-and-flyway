package com.example.testcontainer.configuration.boundary;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

public class DataSourceProducer {

    @NotNull
    @Resource(lookup = "java:global/DUMMY_DS")
    private DataSource dataSource;

    @Produces
    public DataSource dataSource() {
        return dataSource;
    }

}
