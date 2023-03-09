DROP TABLE IF EXISTS tab_exercise_expedition;
DROP TABLE IF EXISTS tab_exercise_group;
DROP TABLE IF EXISTS tab_exercise_participant;

CREATE TABLE tab_exercise_expedition
(
    id         SERIAL PRIMARY KEY,
    start_date date,
    end_date   date,
    leader     integer
);

CREATE TABLE tab_exercise_group
(
    id            SERIAL PRIMARY KEY,
    name          varchar,
    leader        integer,
    expedition_id integer,
    CONSTRAINT FK_EXERCISE_GROUP_ID_EXPEDITION_ID FOREIGN KEY (expedition_id) REFERENCES tab_exercise_expedition (id)
);

CREATE TABLE tab_exercise_participant
(
    id         SERIAL PRIMARY KEY,
    first_name varchar,
    last_name  varchar,
    mail       varchar,
    group_id   integer,
    CONSTRAINT FK_EXERCISE_PARTICIPANT_GROUP FOREIGN KEY (group_id) REFERENCES tab_exercise_group (id)
);

ALTER TABLE tab_exercise_expedition
    ADD CONSTRAINT FK_EXERCISE_LEADER_PARTICIPANT_ID FOREIGN KEY (leader) REFERENCES tab_exercise_participant (id);
ALTER TABLE tab_exercise_group
    ADD CONSTRAINT FK_EXERCISE_LEADER_PARTICIPANT_ID FOREIGN KEY (leader) REFERENCES tab_exercise_participant (id);

