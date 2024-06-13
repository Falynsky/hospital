--liquibase formatted sql
--changeset falynsky:1
CREATE TABLE Role
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);