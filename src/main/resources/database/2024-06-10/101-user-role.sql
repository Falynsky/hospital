--liquibase formatted sql
--changeset falynsky:1
CREATE TABLE "user_role"
(
    user_id BIGINT REFERENCES Users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id BIGINT REFERENCES Role (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id)
);