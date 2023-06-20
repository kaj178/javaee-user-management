CREATE DATABASE `public-api`;
USE `public-api`;

CREATE TABLE `user` (
	id INT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT 'Tên người dùng',
    birthday DATE NOT NULL COMMENT 'Ngày tháng năm sinh',
    gender VARCHAR(100) NOT NULL DEFAULT('Male') COMMENT 'Giới tính',
    status VARCHAR(100) NOT NULL COMMENT 'Trạng thái (active or inactive)',
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci COMMENT='Người dùng';

INSERT INTO `user` VALUES 
(NULL, 'Phan Anh Khoa', 'Male', 'Active'),
(NULL, 'Le Ngoc Han', 'Female', 'Active'),
(NULL, 'Nguyen Van A', 'Male', 'Inactive'),
(NULL, 'Le Thi B', 'Female', 'Inactive');

INSERT INTO `user` VALUES (NULL, 'Nguyen Van C', 'Male', 'Active');
INSERT INTO `public-api`.user VALUES (NULL, 'Tran Thi E', 'Female', 'Inactive');

ALTER TABLE `user` DROP COLUMN 	`birthday`;
ALTER USER 'root'@'localhost' IDENTIFIED BY '';