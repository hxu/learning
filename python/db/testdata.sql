\c pythontest

TRUNCATE test1;

INSERT INTO test1 (str, nmbr, longerstring) VALUES
    ('string one', 10, 'longer string one'),
    ('string two', 20, 'longer string two');
