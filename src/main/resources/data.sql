INSERT INTO users (id, username, password) select * FROM (select 1, 'user', '{bcrypt}$2a$10$zVZezD0Vm4z/xRSFTkoXVO4MWWss9VgX/5HxXOSoxBqdfhMnJmnyW') as temp where not exists (select id from users where id = 1) limit 1;
INSERT INTO user_roles (user_id, roles) select * FROM (select 1, 'ROLE_USER') as temp where not exists (select user_id from user_roles where user_id = 1) limit 1;

INSERT INTO users (id, username, password) select * FROM (select 2, 'admin', '{bcrypt}$2a$10$zVZezD0Vm4z/xRSFTkoXVO4MWWss9VgX/5HxXOSoxBqdfhMnJmnyW') as temp where not exists (select id from users where id = 2) limit 1;
INSERT INTO user_roles (user_id, roles) select * FROM (select 2, 'ROLE_USER') as temp where not exists (select roles from user_roles where roles = 'ROLE_USER' and user_id = 2) limit 1;
INSERT INTO user_roles (user_id, roles) select * FROM (select 2, 'ROLE_ADMIN') as temp where not exists (select roles from user_roles where roles = 'ROLE_ADMIN' and user_id = 2) limit 1;

INSERT INTO categorias (cor, titulo) SELECT * FROM (SELECT '#cbd1ff', 'LIVRE') AS tmp WHERE NOT EXISTS (SELECT titulo FROM categorias WHERE titulo = 'LIVRE') LIMIT 1;
UPDATE challenges.videos SET categoria_id = 1 WHERE categoria_id IS NULL;
