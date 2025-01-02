-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS animal_care_zone_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE animal_care_zone_db;

-- 테이블 삭제 순서:
-- 1. 종속 테이블 (자식 테이블) 먼저 삭제
DROP TABLE IF EXISTS `group_post`;
DROP TABLE IF EXISTS post_photo;
DROP TABLE IF EXISTS care_zone_photo;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS announcement;
-- 2. 중간 종속 테이블 삭제
DROP TABLE IF EXISTS post;

-- 3. 독립 종속 테이블 삭제
DROP TABLE IF EXISTS care_zone;

-- 4. 독립 테이블 삭제
DROP TABLE IF EXISTS `group`;

DROP TABLE IF EXISTS user;

-- 사용자 테이블
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      nickname VARCHAR(50) NOT NULL,
                      profile_image_url VARCHAR(500),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 정거장 테이블
CREATE TABLE care_zone (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           address VARCHAR(255) NOT NULL,
                           latitude DECIMAL(9,6) NOT NULL,
                           longitude DECIMAL(9,6) NOT NULL,
                           contact_number VARCHAR(20),
                           operating_hours VARCHAR(100),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 게시글 테이블
CREATE TABLE post (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      care_zone_id BIGINT,
                      title VARCHAR(255) NOT NULL,
                      content TEXT NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                      FOREIGN KEY (care_zone_id) REFERENCES care_zone(id) ON DELETE SET NULL
);

-- 댓글 테이블
CREATE TABLE comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         post_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         content TEXT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
                         FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 공지사항 테이블
CREATE TABLE announcement (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
    care_zone_id BIGINT NOT NULL,
                              title VARCHAR(255) NOT NULL,
                              content TEXT NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            foreign key (care_zone_id) REFERENCES care_zone(id)
);

-- 게시글 사진 테이블
CREATE TABLE post_photo (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            post_id BIGINT NOT NULL,
                            photo_url VARCHAR(500) NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);

-- 정거장 사진 테이블
CREATE TABLE care_zone_photo (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 care_zone_id BIGINT NOT NULL,
                                 photo_url VARCHAR(500) NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (care_zone_id) REFERENCES care_zone(id) ON DELETE CASCADE
);
CREATE TABLE `group` (
                         `id`	BIGINT	NOT NULL PRIMARY KEY ,
                         `title`	VARCHAR(100)	NULL,
                         `description`	VARCHAR(500)	NULL,
                         `created_at`	TIMESTAMP	NULL,
                         `updated_at`	TIMESTAMP	NULL
);

CREATE TABLE `group_post` (
                              `group_id`	BIGINT	NOT NULL,
                              `post_id`	BIGINT	NOT NULL,
                              `created_at`	TIMESTAMP	NULL,
                              `updated_at`	TIMESTAMP	NULL,
    FOREIGN KEY (group_id) REFERENCES `group`(id),
    FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE `user_group` (
                              `user_id`	BIGINT	NOT NULL,
                              `group_id`	BIGINT	NOT NULL,
                              `role`	VARCHAR(20)	NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (group_id) REFERENCES `group`(id)
);
