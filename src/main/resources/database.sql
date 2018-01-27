
CREATE TABLE functions (
    id bigserial PRIMARY KEY NOT NULL,
    description text,
    body text,
    request_names text,
    type_of text
);

CREATE TABLE users (
    id bigserial PRIMARY KEY NOT NULL,
    username   VARCHAR(20),
    role    VARCHAR(20)
  );

CREATE TABLE Requirement (
    id bigserial PRIMARY KEY NOT NULL,
    description   text,
    type_of text,
    body text
  );


-- # выделяем бинарную семантическая сеть в отдельную табличку
CREATE TABLE Relations (
    id bigserial PRIMARY KEY NOT NULL,
    taxon_parent_id   bigint REFERENCES taxon,
    relation_name text,
    taxon_child_id bigint REFERENCES taxon
  );

CREATE TABLE projects (
    id bigserial PRIMARY KEY NOT NULL,
    description text
  );

SELECT rel.id, t1.description as parent, rel.relation_name, t2.description as child FROM "public".relations rel join taxon t1 on t1.id=rel.taxon_parent_id join taxon t2 on t2.id = rel.taxon_child_id;

CREATE TABLE taxon (
    id bigserial PRIMARY KEY NOT NULL,
    description   text,
    project_id bigint REFERENCES projects
  );

alter table taxon add project_id bigint REFERENCES projects;

alter table requirement add project_id bigint REFERENCES projects;

-- ALTER TABLE Relations ADD CONSTRAINT Relations_fk1 FOREIGN KEY (taxon_parent_id)  REFERENCES taxon; 
