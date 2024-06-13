--liquibase formatted sql
--changeset falynsky:1
CREATE TABLE "privilege_role"
(
    privilege_id BIGINT REFERENCES Privilege (id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id      BIGINT REFERENCES Role (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT privilege_role_pkey PRIMARY KEY (privilege_id, role_id)
);