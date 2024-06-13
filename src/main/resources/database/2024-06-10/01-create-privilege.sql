--liquibase formatted sql
--changeset falynsky:1
CREATE TABLE Privilege
(
    id      BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);