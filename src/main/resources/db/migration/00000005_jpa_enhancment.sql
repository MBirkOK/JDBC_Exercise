ALTER TABLE tab_exercise_employee ADD COLUMN dtype varchar;
ALTER TABLE tab_exercise_employee ADD COLUMN responsiblefor_id int;
ALTER TABLE tab_exercise_employee ADD CONSTRAINT FK_employee_treatment FOREIGN KEY (responsiblefor_id) REFERENCES tab_exercise_treatment(id);