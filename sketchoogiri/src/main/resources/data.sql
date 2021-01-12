-- ダミーユーザー
INSERT INTO usr(user_id, name, password, role) VALUES(
	'tester1',
	'テストマン1',
	'$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK',
	'USER'
);

INSERT INTO usr(user_id, name, password, role) VALUES(
	'tester2',
	'テストマン2',
	'$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK',
	'USER'
)/;

-- お題
INSERT INTO theme(user_id, request, img_url, posted_time) VALUES(
	'tester1',
	'可愛くして',
	'https://placehold.jp/300x100.png',
	'2020-01-01 00:00:00'
)/;
INSERT INTO theme(user_id, request, img_url, posted_time) VALUES(
	'tester1',
	'カッコよくして',
	'https://placehold.jp/200x400.png',
	'2020-01-01 00:01:00'
)/;

-- 回答
INSERT INTO answer(theme_id, user_id, description, img_url, posted_time) VALUES(
	1,
	'tester2',
	'目を可愛くした',
	'https://placehold.jp/200x200.png',
	'2020-01-01 00:00:50'
)/;

INSERT INTO answer(theme_id, user_id, description, img_url, posted_time) VALUES(
	2,
	'tester2',
	'カッコよくした',
	'https://placehold.jp/200x200.png',
	'2020-01-01 00:01:50'
)/;