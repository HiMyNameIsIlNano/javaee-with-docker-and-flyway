create sequence DUMMY_ID_GEN start with 1 increment by 50;
create table TEST_TABLE
(
    id   bigint not null,
    text varchar(255),
    primary key (id)
);