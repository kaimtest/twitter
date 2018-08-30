DELETE FROM user_role;
DELETE from usr;

insert into usr(id, active, password, username) VALUE
(1, true, 'ddd', 'eee'),
(2, true, 'fff', 'rrr');

insert into user_role(user_id, roles) VALUE
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');
