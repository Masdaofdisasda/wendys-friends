-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE FROM horse WHERE id <0;

INSERT INTO horse (id, name, description, birthdate, gender, owner)
VALUES (-1, 'Wendy', 'horse', '2000-01-01', 'f', 'sql')
;
