DROP TABLE IF EXISTS usr CASCADE /;
DROP TABLE IF EXISTS theme CASCADE /;
DROP TABLE IF EXISTS answer CASCADE /;

CREATE TABLE IF NOT EXISTS usr (
  user_id VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_id)
)/;


CREATE TABLE IF NOT EXISTS theme (
  theme_id VARCHAR(255) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  request VARCHAR(255) NOT NULL,
  img_url VARCHAR NOT NULL,
  posted_date DATE NOT NULL,
  PRIMARY KEY (theme_id)
)/;

CREATE TABLE IF NOT EXISTS answer (
  answer_id VARCHAR(255) NOT NULL,
  theme_id VARCHAR(255) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  img_url VARCHAR NOT NULL,
  posted_date DATE NOT NULL,
  PRIMARY KEY (answer_id)
)/;

ALTER TABLE theme DROP CONSTRAINT IF EXISTS FK_theme_usr_user_id/;
ALTER TABLE theme ADD CONSTRAINT FK_theme_usr_user_id FOREIGN KEY (user_id) REFERENCES usr/;
ALTER TABLE answer DROP CONSTRAINT IF EXISTS FK_answer_usr_user_id/;
ALTER TABLE answer ADD CONSTRAINT FK_answer_usr_user_id FOREIGN KEY (user_id) REFERENCES usr/;
ALTER TABLE answer DROP CONSTRAINT IF EXISTS FK_answer_theme_theme_id/;
ALTER TABLE answer ADD CONSTRAINT FK_answer_theme_theme_id FOREIGN KEY (theme_id) REFERENCES theme/;

/*
CREATE TABLE IF NOT EXISTS reservable_room (
  reserved_date DATE NOT NULL,
  room_id INT4 NOT NULL,
  PRIMARY KEY (reserved_date, room_id)
)/;
CREATE TABLE IF NOT EXISTS reservation (
  reservation_id SERIAL NOT NULL,
  end_time TIME NOT NULL,
  start_time TIME NOT NULL,
  reserved_date DATE NOT NULL,
  room_id INT4 NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (reservation_id)
)/;
CREATE TABLE IF NOT EXISTS usr (
  user_id VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_id)
)/;

ALTER TABLE reservable_room DROP CONSTRAINT IF EXISTS FK_f4wnx2qj0d59s9tl1q5800fw7/;
ALTER TABLE reservation DROP CONSTRAINT IF EXISTS FK_p1k4iriqd4eo1cpnv79uvni9g/;
ALTER TABLE reservation DROP CONSTRAINT IF EXISTS FK_recqnfjcp370rygd9hjjxjtg/;
ALTER TABLE reservable_room ADD CONSTRAINT FK_f4wnx2qj0d59s9tl1q5800fw7 FOREIGN KEY (room_id) REFERENCES meeting_room/;
ALTER TABLE reservation ADD CONSTRAINT FK_p1k4iriqd4eo1cpnv79uvni9g FOREIGN KEY (reserved_date, room_id) REFERENCES reservable_room/;
ALTER TABLE reservation ADD CONSTRAINT FK_recqnfjcp370rygd9hjjxjtg FOREIGN KEY (user_id) REFERENCES usr/;
*/