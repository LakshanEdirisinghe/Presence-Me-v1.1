DROP DATABASE IF EXISTS main;
CREATE DATABASE main;
use main;

-- =====================================
-- 1. DEGREE TABLE
-- =====================================

CREATE TABLE DEGREE (
    degree_code VARCHAR(20) PRIMARY KEY,
    degree_name VARCHAR(100) NOT NULL
);

-- =====================================
-- 2. STUDENT_LIST TABLE
-- =====================================

CREATE TABLE STUDENT_LIST (
    student_list_code VARCHAR(30) PRIMARY KEY,
    list_name VARCHAR(100) NOT NULL
);


-- =====================================
-- 3. USER TABLE
-- =====================================

CREATE TABLE USER (
    user_id VARCHAR(30) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    contact_no VARCHAR(20),
    account_status VARCHAR(20) DEFAULT 'ACTIVE'
);


-- =====================================
-- 4. ADMIN TABLE
-- =====================================

CREATE TABLE ADMIN (
    user_id VARCHAR(30) PRIMARY KEY,

    CONSTRAINT fk_admin_user
    FOREIGN KEY (user_id)
    REFERENCES USER(user_id)
);


-- =====================================
-- 5. STUDENT TABLE
-- =====================================

CREATE TABLE STUDENT (
    user_id VARCHAR(30) PRIMARY KEY,
    uni_id VARCHAR(30) NOT NULL UNIQUE,
    degree_code VARCHAR(20) NOT NULL,
    student_list_code VARCHAR(30) NOT NULL,

    CONSTRAINT fk_student_user
    FOREIGN KEY (user_id)
    REFERENCES USER(user_id),

    CONSTRAINT fk_student_degree
    FOREIGN KEY (degree_code)
    REFERENCES DEGREE(degree_code),

    CONSTRAINT fk_student_list
    FOREIGN KEY (student_list_code)
    REFERENCES STUDENT_LIST(student_list_code)
);


-- =====================================
-- 6. LECTURER TABLE
-- =====================================

CREATE TABLE LECTURER (
    user_id VARCHAR(30) PRIMARY KEY,
    lecturer_code VARCHAR(30) NOT NULL UNIQUE,

    CONSTRAINT fk_lecturer_user
    FOREIGN KEY (user_id)
    REFERENCES USER(user_id)
);


------------------------------------

DROP DATABASE IF EXISTS main;
CREATE DATABASE main;
use main;

-- =====================================
-- 1. DEGREE SAMPLE DATA
-- =====================================

INSERT INTO DEGREE (degree_code, degree_name)
VALUES
('SE', 'BSc (Hons) in Software Engineering'),
('HICT', 'BSc (Hons) in Health Information and Communication Technology'),
('MBA', 'Bachelor of Management (Honors) in Business Analytics');


-- =====================================
-- 2. STUDENT_LIST SAMPLE DATA
-- =====================================

INSERT INTO STUDENT_LIST (student_list_code, list_name)
VALUES
('UOC/SE/2022', 'Software Engineering'),
('UOC/SE/2021', 'Software Engineering'),
('UOC/HICT/2022', 'Health-ICT'),
('UOC/MBA/2022', 'Business Analytics');


=====================================
3. USER SAMPLE DATA
=====================================

INSERT INTO `USER`
(user_id, first_name, last_name, email, password_hash, contact_no, account_status)
VALUES

('ADM001', 'Admin', 'Perera',
 'admin@presentme.test', 'TEST_HASH_NOT_FOR_LOGIN',
 '0711111111', 'ACTIVE'),

('LEC001', 'Kasun', 'Fernando',
 'kasun@presentme.test', 'TEST_HASH_NOT_FOR_LOGIN',
 '0722222222', 'ACTIVE'),

('ST001', 'Nimal', 'Perera',
 'nimal@presentme.test', 'TEST_HASH_NOT_FOR_LOGIN',
 '0773333333', 'ACTIVE'),

('ST002', 'Amali', 'Silva',
 'amali@presentme.test', 'TEST_HASH_NOT_FOR_LOGIN',
 '0764444444', 'ACTIVE'),

('ST003', 'Sahan', 'Fernando',
 'sahan@presentme.test', 'TEST_HASH_NOT_FOR_LOGIN',
 '0755555555', 'ACTIVE'),

('ST004', 'Tharushi', 'Perera',
 'tharushi@presentme.test', 'TEST_HASH_NOT_FOR_LOGIN',
 '0746666666', 'ACTIVE');


=====================================
4. ADMIN SAMPLE DATA
=====================================

INSERT INTO ADMIN (user_id)
VALUES
('ADM001');


=====================================
5. STUDENT SAMPLE DATA
=====================================

INSERT INTO STUDENT
(user_id, uni_id, degree_code, student_list_code)
VALUES

('ST001', 'UOC/SE/2022/13', 'SE', 'UOC/SE/2022'),
('ST002', 'UOC/SE/2021/06', 'SE', 'UOC/SE/2021'),
('ST003', 'UOC/MBA/2026/03', 'MBA', 'UOC/MBA/2022'),
('ST004', 'UOC/HICT/2022/45', 'HICT', 'UOC/HICT/2022');


-- =====================================
-- 6. LECTURER SAMPLE DATA
-- =====================================

INSERT INTO LECTURER (user_id, lecturer_code)
VALUES
('LEC001', 'LECT2026001');


