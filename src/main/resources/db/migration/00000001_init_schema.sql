DROP TABLE IF EXISTS tab_exercise_employee;
DROP TABLE IF EXISTS tab_exercise_inventory;

CREATE TABLE tab_exercise_employee (
                                       pers_nr SERIAL PRIMARY KEY,
                                       first_name varchar,
                                       last_name varchar,
                                       birthday date
);

CREATE TABLE tab_exercise_inventory (
                                        id SERIAL PRIMARY KEY,
                                        description VARCHAR,
                                        procurement DATE,
                                        worth DOUBLE PRECISION,
                                        employee_nr int,
                                        CONSTRAINT FK_EXERCISE_EMPLOYEE_NR_INVENTORY FOREIGN KEY (employee_nr) REFERENCES tab_exercise_employee(pers_nr)
);