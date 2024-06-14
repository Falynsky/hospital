--liquibase formatted sql
--changeset falynsky:1

create sequence privilege_seq;

alter sequence privilege_seq owner to postgres;

create sequence role_seq;

alter sequence role_seq owner to postgres;

create sequence user_seq;

alter sequence user_seq owner to postgres;