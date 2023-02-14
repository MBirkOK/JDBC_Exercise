DROP TABLE IF EXISTS tab_exercise_inventory;
DROP TABLE IF EXISTS tab_exercise_ward CASCADE;
DROP TABLE IF EXISTS tab_exercise_patient CASCADE ;
DROP TABLE IF EXISTS tab_exercise_inventory;
DROP TABLE IF EXISTS tab_exercise_treatment;
DROP TABLE IF EXISTS tab_exercise_room;

CREATE TABLE tab_exercise_ward(
    id SERIAL PRIMARY KEY,
    description varchar(255)
);
CREATE TABLE tab_exercise_patient(
    id SERIAL PRIMARY KEY,
    first_name varchar,
    last_name varchar,
    birthday date,
    room_id int,
    nurse_id int
);

CREATE TABLE tab_exercise_treatment(
    id SERIAL PRIMARY KEY,
    treatment varchar(255),
    employee_id int,
    patient_id int
);

CREATE TABLE tab_exercise_room(
    id int PRIMARY KEY,
    amount_beds int,
    ward_id int
);

ALTER TABLE tab_exercise_employee ADD COLUMN ward_id int;
ALTER TABLE tab_exercise_employee ADD COLUMN type varchar(50);
ALTER TABLE tab_exercise_employee ADD COLUMN salary double precision;

ALTER TABLE tab_exercise_employee ADD CONSTRAINT FK_tab_exercise_ward_tab_exercise_employee FOREIGN KEY (ward_id) REFERENCES tab_exercise_ward(id);

ALTER TABLE tab_exercise_treatment ADD CONSTRAINT FK_tab_exercise_treatment_tab_exercise_patient FOREIGN KEY (patient_id) REFERENCES tab_exercise_patient(id);
ALTER TABLE tab_exercise_treatment ADD CONSTRAINT FK_tab_exercise_treatment_tab_exercise_employee FOREIGN KEY (employee_id) REFERENCES tab_exercise_employee(pers_nr);

ALTER TABLE tab_exercise_patient ADD CONSTRAINT FK_tab_exercise_patient_tab_exercise_room FOREIGN KEY (room_id) REFERENCES tab_exercise_room(id);
ALTER TABLE tab_exercise_patient ADD CONSTRAINT FK_tab_exercise_patient_tab_exercise_employee FOREIGN KEY (nurse_id) REFERENCES tab_exercise_employee(pers_nr);
ALTER TABLE tab_exercise_patient ADD COLUMN stay_start date;
ALTER TABLE tab_exercise_patient ADD COLUMN stay_end date;

ALTER TABLE tab_exercise_room ADD CONSTRAINT FK_tab_exercise_room_tab_exercise_ward FOREIGN KEY (ward_id) REFERENCES tab_exercise_ward(id);
