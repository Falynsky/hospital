--liquibase formatted sql
--changeset falynsky:1
CREATE TABLE Users
(
    id      BIGINT PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName  VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    enabled   BOOLEAN NOT NULL,
    tokenExpired BOOLEAN NOT NULL
);