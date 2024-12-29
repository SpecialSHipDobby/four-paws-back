-- 데이터베이스 생성
CREATE DATABASE animal_care_zone_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE animal_care_zone_db;

-- 사용자 테이블
CREATE TABLE users (
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
CREATE TABLE posts (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       care_zone_id BIGINT,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (care_zone_id) REFERENCES care_zone(id) ON DELETE SET NULL
);

-- 댓글 테이블
CREATE TABLE comments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          post_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 공지사항 테이블
CREATE TABLE announcements (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               title VARCHAR(255) NOT NULL,
                               content TEXT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 게시글 사진 테이블
CREATE TABLE post_photos (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             post_id BIGINT NOT NULL,
                             photo_url VARCHAR(500) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

-- 정거장 사진 테이블
CREATE TABLE care_zone_photos (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  care_zone_id BIGINT NOT NULL,
                                  photo_url VARCHAR(500) NOT NULL,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (care_zone_id) REFERENCES care_zone(id) ON DELETE CASCADE
);

-- 인덱스 추가 (성능 최적화)
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_posts_care_zone_id ON posts(care_zone_id);
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);
CREATE INDEX idx_care_zone_name ON care_zone(name);