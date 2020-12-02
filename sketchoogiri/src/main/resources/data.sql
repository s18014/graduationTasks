-- ダミーユーザー
INSERT INTO usr(user_id, name, password, role) VALUES(
	'hentai',
	'変態',
	'$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK',
	'USER'
);

INSERT INTO usr(user_id, name, password, role) VALUES(
	'gentleman',
	'紳士',
	'$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK',
	'USER'
)/;

-- お題
INSERT INTO theme(user_id, request, img_url, posted_time) VALUES(
	'hentai',
	'エロくして',
	'/images/20201130234739002.png',
	'2020-01-01 00:00:00'
)/;
INSERT INTO theme(user_id, request, img_url, posted_time) VALUES(
	'hentai',
	'もっとエロくして',
	'/images/20201201103739315.png',
	'2020-01-01 00:01:00'
)/;

-- 回答
INSERT INTO answer(theme_id, user_id, description, img_url, posted_time) VALUES(
	1,
	'gentleman',
	'エロくした',
	'dummy',
	'2020-01-01 00:00:50'
)/;

INSERT INTO answer(theme_id, user_id, description, img_url, posted_time) VALUES(
	2,
	'gentleman',
	'もっとエロくした',
	'dummy',
	'2020-01-01 00:01:50'
)/;