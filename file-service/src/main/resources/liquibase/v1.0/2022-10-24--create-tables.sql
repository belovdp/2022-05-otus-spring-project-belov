--liquibase formatted sql

--changeset BelovDP:createTables
CREATE TABLE files_info
(
    id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    entity_category character varying(255) NOT NULL,
    content_type    character varying(255) NOT NULL,
    entity_id       bigint NOT NULL
);