package com.example.testcontainer.configuration.boundary;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.jboss.logging.Logger;

public class DataSourceProducer {

    private static final Logger LOGGER = Logger.getLogger(DataSourceProducer.class.getName());

    @NotNull
    @Resource(lookup = "java:/postgresDS")
    private DataSource dataSource;

    @Produces
    @Dependent
    public DataSource dataSource() {
        LOGGER.info("Producing the data source");
        return dataSource;
    }

}
