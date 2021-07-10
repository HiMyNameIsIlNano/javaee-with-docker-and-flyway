package com.example.testcontainer.helper.boundary;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresSQLContainerHelper {

    public static PostgreSQLContainer<?> build() {
        final String imageName = String.format("%s:%s", PostgresqlTestDataSource.DOCKER_IMAGE_NAME,
                PostgresqlTestDataSource.DOCKER_IMAGE_VERSION);

        return new PostgreSQLContainer<>(imageName)
                .withDatabaseName(PostgresqlTestDataSource.DB_NAME)
                .withExposedPorts(PostgresqlTestDataSource.PORT)
                .withUsername(PostgresqlTestDataSource.USER)
                .withPassword(PostgresqlTestDataSource.PASSWORD);
    }

}
