INSERT INTO users(id, name, email, password) select * FROM (select 1, 'Teste', 'teste@aluraflix.com', '$2a$10$zVZezD0Vm4z/xRSFTkoXVO4MWWss9VgX/5HxXOSoxBqdfhMnJmnyW') AS tmp WHERE NOT EXISTS (SELECT id FROM users WHERE id = 1) LIMIT 1;
INSERT INTO users(id, name, email, password) select * FROM (select 2, 'Admin', 'admin@aluraflix.com', '$2a$10$zVZezD0Vm4z/xRSFTkoXVO4MWWss9VgX/5HxXOSoxBqdfhMnJmnyW') AS tmp WHERE NOT EXISTS (SELECT id FROM users WHERE id = 2) LIMIT 1;
--senha(password)
INSERT INTO profile(id, name) select * FROM (select 1, 'ROLE_USER') AS tmp WHERE NOT EXISTS (SELECT id FROM profile WHERE id = 1) LIMIT 1;
INSERT INTO profile(id, name) select * FROM (select 2, 'ROLE_ADMIN') AS tmp WHERE NOT EXISTS (SELECT id FROM profile WHERE id = 2) LIMIT 1;

INSERT INTO users_profiles(user_id, profiles_id) select * FROM (select 1 as user_id , 1 as profiles_id) AS tmp WHERE NOT EXISTS (SELECT user_id FROM users_profiles WHERE user_id = '1' and profiles_id = '1') LIMIT 1;
INSERT INTO users_profiles(user_id, profiles_id) select * FROM (select 2 as user_id , 2 as profiles_id) AS tmp WHERE NOT EXISTS (SELECT user_id FROM users_profiles WHERE user_id = '2' and profiles_id = '2') LIMIT 1;

INSERT INTO categorias (cor, titulo) SELECT * FROM (SELECT '#AAAAAA', 'LIVRE') AS tmp WHERE NOT EXISTS (SELECT titulo FROM categorias WHERE titulo = 'LIVRE') LIMIT 1;
UPDATE challenges.videos SET categoria_id = 1 WHERE categoria_id IS NULL;