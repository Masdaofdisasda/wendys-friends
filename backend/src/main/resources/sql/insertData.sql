-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE FROM horse WHERE id <0;

INSERT INTO horse (id, name, description, birthdate, gender, owner, mom, dad)
VALUES (-1, 'Wendy', 'horse test 1', '2001-01-01', 'f', 'sql', null, null),
    (-2, 'Darkbolt', 'horse test 2', '2002-01-01', 'm', 'sql', null, null),
(-3, 'Maiko', 'horse test 3', '2003-01-01', 'f', 'sql', -1, -2),
(-4, 'Ceres', 'horse test 4', '2004-01-01', 'f', 'sql', -1, -2),
(-5, 'Kalika', 'horse test 5', '2005-01-01', 'f', 'sql', -1, -2),
(-6, 'Foxtail', 'horse test 6', '2006-01-01', 'f', 'sql', -1, -2),
(-7, 'Eden', 'horse test 7', '2007-01-01', 'm', 'sql', -1, -2),
(-8, 'Cleo', 'horse test 8', '2008-01-01', 'm', 'sql', -1, -2),
(-9, 'Shamin', 'horse test 9', '2009-01-01', 'm', 'sql', -1, -2),
(-10, 'Lad', 'horse test 10', '2010-01-01', 'm', 'sql', -1, -2)
;
