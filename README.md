# JavaEE Application Journey

This is just a small POC to build a simple JavaEE Web Application.

## 00_init_webapp

In this 'chapter' I just initialized a web application with a Dummy endpoint. The only thing that one can do is to 
click on the java tests folder and run them in Intellij (run All Tests). Before running the tests it is necessary to:

```shell
cd src/main/java/docker
docker-compose -f docker-compose.yml up -d integration-test-db 
```