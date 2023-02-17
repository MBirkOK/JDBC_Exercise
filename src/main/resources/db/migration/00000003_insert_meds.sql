DROP TABLE IF EXISTS tab_exercise_med CASCADE;
CREATE TABLE tab_exercise_med
(
    id          SERIAL PRIMARY KEY,
    description varchar(255)
);

ALTER TABLE tab_exercise_treatment ADD COLUMN med_id int;
ALTER TABLE tab_exercise_treatment ADD CONSTRAINT tab_exercise_med_tab_exercise_treatment FOREIGN KEY (med_id) REFERENCES tab_exercise_med(id);