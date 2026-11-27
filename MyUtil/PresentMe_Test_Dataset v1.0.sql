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


USE main;

-- 1. FACULTY
CREATE TABLE FACULTY (
    faculty_code VARCHAR(20) PRIMARY KEY,
    faculty_name VARCHAR(100) NOT NULL
);

-- 2. DEPARTMENT
CREATE TABLE DEPARTMENT (
    department_code VARCHAR(20) PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL,
    faculty_code VARCHAR(20) NOT NULL,

    CONSTRAINT fk_department_faculty
    FOREIGN KEY (faculty_code)
    REFERENCES FACULTY(faculty_code)
);

ALTER TABLE DEGREE
ADD COLUMN department_code VARCHAR(20) NULL;

ALTER TABLE DEGREE
ADD CONSTRAINT fk_degree_department
FOREIGN KEY (department_code)
REFERENCES DEPARTMENT(department_code);

CREATE TABLE SEMESTER (
    academic_year VARCHAR(9) NOT NULL,
    semester_no TINYINT UNSIGNED NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    PRIMARY KEY (academic_year, semester_no)
);

CREATE TABLE SUBJECT (
    subject_code VARCHAR(20) PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    academic_year VARCHAR(9) NOT NULL,
    semester_no TINYINT UNSIGNED NOT NULL,
    lecturer_code VARCHAR(30) NOT NULL,
    credits DECIMAL(3,1) DEFAULT 3.0,

    CONSTRAINT fk_subject_semester
    FOREIGN KEY (academic_year, semester_no)
    REFERENCES SEMESTER(academic_year, semester_no),

    CONSTRAINT fk_subject_lecturer
    FOREIGN KEY (lecturer_code)
    REFERENCES LECTURER(lecturer_code)
);
CREATE TABLE SUBJECT_OF_DEGREE (
    degree_code VARCHAR(20) NOT NULL,
    subject_code VARCHAR(20) NOT NULL,

    PRIMARY KEY (degree_code, subject_code),

    CONSTRAINT fk_sod_degree
    FOREIGN KEY (degree_code)
    REFERENCES DEGREE(degree_code),

    CONSTRAINT fk_sod_subject
    FOREIGN KEY (subject_code)
    REFERENCES SUBJECT(subject_code)
);
CREATE TABLE DEPARTMENT_LECTURER (
    department_code VARCHAR(20) NOT NULL,
    lecturer_code VARCHAR(30) NOT NULL,

    PRIMARY KEY (department_code, lecturer_code),

    CONSTRAINT fk_dl_department
    FOREIGN KEY (department_code)
    REFERENCES DEPARTMENT(department_code),

    CONSTRAINT fk_dl_lecturer
    FOREIGN KEY (lecturer_code)
    REFERENCES LECTURER(lecturer_code)
);

CREATE TABLE REGISTERED_DEVICE (
    android_id VARCHAR(100) PRIMARY KEY,
    uni_id VARCHAR(30) NOT NULL,
    registered_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    device_status ENUM('ACTIVE', 'BLOCKED') DEFAULT 'ACTIVE',

    CONSTRAINT fk_device_student
    FOREIGN KEY (uni_id)
    REFERENCES STUDENT(uni_id)
);

CREATE TABLE ATTENDANCE_SESSION (
    session_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    lecturer_code VARCHAR(30) NOT NULL,
    subject_code VARCHAR(20) NOT NULL,
    +Student list code

    -lecture_date DATE NOT NULL,
    lecture_start_time TIME NOT NULL,
    lecture_end_time TIME NOT NULL,

    qr_token CHAR(64) NOT NULL UNIQUE,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    expires_at DATETIME NOT NULL,

    session_status ENUM('ACTIVE', 'EXPIRED', 'CANCELLED')
    DEFAULT 'ACTIVE',

    CONSTRAINT fk_session_lecturer
    FOREIGN KEY (lecturer_code)
    REFERENCES LECTURER(lecturer_code),

    CONSTRAINT fk_session_subject
    FOREIGN KEY (subject_code)
    REFERENCES SUBJECT(subject_code)
);
alter table main.ATTENDANCE_SESSION
drop column lecture_date;

CREATE TABLE ATTENDANCE (
    attendance_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    session_id BIGINT UNSIGNED NOT NULL,
    uni_id VARCHAR(30) NOT NULL,

    attendance_status ENUM('PRESENT', 'LATE', 'ABSENT')
    DEFAULT 'PRESENT',

    marked_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_attendance_session
    FOREIGN KEY (session_id)
    REFERENCES ATTENDANCE_SESSION(session_id),

    CONSTRAINT fk_attendance_student
    FOREIGN KEY (uni_id)
    REFERENCES STUDENT(uni_id),

    CONSTRAINT unique_student_session
    UNIQUE (session_id, uni_id)
);



INSERT INTO FACULTY (faculty_code, faculty_name)
VALUES
('FAC001', 'Faculty of Computing and Technology'),
('FAC002', 'Faculty of Health Sciences and Technology'),
('FAC003', 'Faculty of Management Studies');


INSERT INTO DEPARTMENT
(department_code, department_name, faculty_code)
VALUES
('DEP001', 'Department of Software Engineering', 'FAC001'),
('DEP002', 'Department of Health Information Technology', 'FAC002'),
('DEP003', 'Department of Business Analytics', 'FAC003');

UPDATE DEGREE
SET department_code =
    CASE degree_code
        WHEN 'SE' THEN 'DEP001'
        WHEN 'HICT' THEN 'DEP002'
        WHEN 'MBA' THEN 'DEP003'
    END
WHERE degree_code IN ('SE', 'HICT', 'MBA');

ALTER TABLE DEGREE
MODIFY department_code VARCHAR(20) NOT NULL;

INSERT INTO SEMESTER
(academic_year, semester_no, start_date, end_date)
VALUES
('2026/2027', 1, '2026-08-01', '2026-12-31'),
('2026/2027', 2, '2027-01-01', '2027-05-31');

INSERT INTO DEPARTMENT_LECTURER
(department_code, lecturer_code)
VALUES
('DEP001', 'LECT2026001');

INSERT INTO SUBJECT
(subject_code, subject_name, academic_year,
 semester_no, lecturer_code, credits)
VALUES
('SE101', 'Programming Fundamentals',
 '2026/2027', 1, 'LECT2026001', 3.0),

('SE102', 'Object Oriented Programming',
 '2026/2027', 1, 'LECT2026001', 3.0);

 INSERT INTO SUBJECT_OF_DEGREE
(degree_code, subject_code)
VALUES
('SE', 'SE101'),
('SE', 'SE102');

INSERT INTO REGISTERED_DEVICE
(android_id, uni_id)
VALUES
('a1b2c3d4e5f67890', 'UOC/SE/2022/13');


INSERT INTO ATTENDANCE_SESSION
(lecturer_code, subject_code, lecture_date,
 lecture_start_time, lecture_end_time,
 qr_token, expires_at, session_status)
VALUES
(
    'LECT2026001',
    'SE101',
    CURDATE(),
    TIME(NOW()),
    TIME(DATE_ADD(NOW(), INTERVAL 1 HOUR)),
    SHA2(UUID(), 256),
    DATE_ADD(NOW(), INTERVAL 10 MINUTE),
    'ACTIVE'
);

SET @demo_session_id = LAST_INSERT_ID();

SELECT
    session_id,
    lecturer_code,
    subject_code,
    qr_token
FROM ATTENDANCE_SESSION
ORDER BY session_id DESC
LIMIT 5;

INSERT INTO ATTENDANCE
(session_id, uni_id, attendance_status)
VALUES
(1, 'UOC/SE/2022/13', 'PRESENT'),
(1, 'UOC/SE/2021/06', 'PRESENT');


alter table main.LECTURER
add unique (lecturer_code);



alter table main.ATTENDANCE_SESSION
add column student_list_code varchar(30) not null;

update main.ATTENDANCE_SESSION
set student_list_code="UOC/MBA/2022"
where lecturer_code="LECT2026001";

alter table main.ATTENDANCE_SESSION 
  add constraint foreign key (student_list_code) references main.STUDENT_LIST(student_list_code)
  ON UPDATE CASCADE ON DELETE CASCADE;