# JavaEE Application Journey

This is just a small POC to build a simple JavaEE Web Application.

## 01_add_flyway

In this 'chapter' I extend the work done in the `00_init_webapp` branch. The tests are now runnable using `mvn`. The DB is initialized using `flyway`.

### Flyway Migrations

The scripts are under `src/main/resources/db/migration`. The flyway migration bean is registered under `src/main/resources/META-INF/services` and it is triggered at startup by Hibernate. For the tests the migrations are run manually (see `.example.testcontainer.flyway.FlywayTestMigrator`).

### How to run the tests

```shell
mvn clean test 
```

### How to run the application locally

```shell
mvn clean package
cd /src/main/java/docker/
docker-compose -f docker-compose.yml up -d
```

#### Test Application

In order to test the application one can call:

```shell
curl -XGET http://localhost:8080/javaee-docker-flyway/api/loopback/{text}
```

where `{text}` is an arbitrary string passed from the command line (e.g. `hello`). If everything works as expected the `{text}` should be printed on the console.

#### Application DB

Url:

```
jdbc:postgresql://localhost:5432/postgres
```
Credentials:
```text
User: postgresprod
Pwd: postgresprod
```

#### Management Console

the management console can be found under `http://localhost:9990/management` and it is accessible by: 

```text
User: admin 
Pwd: Admin#123456
```