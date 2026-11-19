select setval(
       pg_get_serial_sequence('board', 'id'),
       COALESCE((SELECT MAX(id) FROM board), 1),
       EXISTS(SELECT 1 FROM board)
);

SELECT setval(
       pg_get_serial_sequence('cards', 'id'),
       COALESCE((SELECT MAX(id) FROM cards), 1),
       EXISTS (SELECT 1 FROM cards)
);

select setval(
       pg_get_serial_sequence('columns', 'id'),
       COALESCE((SELECT MAX(id) FROM columns), 1),
       EXISTS (SELECT 1 FROM columns)
);

select setval(
       pg_get_serial_sequence('users', 'id'),
       COALESCE((SELECT MAX(id) FROM users), 1),
       EXISTS (SELECT 1 FROM users)
);

select setval(
       pg_get_serial_sequence('comments', 'id'),
       COALESCE((SELECT MAX(id) FROM comments), 1),
       EXISTS(SELECT 1 FROM comments)
);

select setval(
       pg_get_serial_sequence('labels', 'id'),
       COALESCE((SELECT MAX(id) FROM labels), 1),
       EXISTS(SELECT 1 FROM labels)
);