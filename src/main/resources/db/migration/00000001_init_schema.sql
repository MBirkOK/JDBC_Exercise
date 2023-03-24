DROP TABLE IF EXISTS tab_exercise_literature;

CREATE TABLE tab_exercise_literature
(
    id           uuid,
    title        varchar,
    author_first_name varchar,
    author_last_name varchar,
    release_year date,
    edition      varchar,
    publisher    varchar
);
