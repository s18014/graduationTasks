-- ダミーユーザー
INSERT INTO usr(user_id, name, password, role_name) VALUES(
	'hentai',
	'変態',
	'$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK',
	'USER'
);

INSERT INTO usr(user_id, name, password, role_name) VALUES(
	'gentleman',
	'紳士',
	'$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK',
	'USER'
)/;

-- お題
INSERT INTO theme(theme_id, user_id, request, img_url, posted_time) VALUES(
	'1',
	'hentai',
	'エロくして',
	'dummy',
	'2020-01-01 00:00:00'
)/;
-- 回答
INSERT INTO answer(answer_id, theme_id, user_id, description, img_url, posted_time) VALUES(
	'1',
	'1',
	'gentleman',
	'エロくした',
	'dummy',
	'2020-01-01 00:00:5'
)/;