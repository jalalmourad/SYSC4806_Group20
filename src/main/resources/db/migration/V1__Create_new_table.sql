
CREATE TABLE Topic (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       topic_name VARCHAR(255) NOT NULL
);

INSERT INTO Topic (topic_name) VALUES ('Software Engineering');

CREATE TABLE Student (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         student_id BIGINT NOT NULL UNIQUE,
                         has_topic BOOLEAN DEFAULT false,
                         joined_topic_id BIGINT,
                         CONSTRAINT FK_Student_Topic FOREIGN KEY (joined_topic_id) REFERENCES Topic(id)
);

INSERT INTO Student (first_name, last_name, student_id, has_topic, joined_topic_id)
VALUES
    ('Jalal', 'Mourad', 101195263, false, NULL),
    ('Alex', 'Smith', 101195222, false, NULL),
    ('Sam', 'Blake', 10028361, false, NULL),
    ('Emily', 'Johnson', 101256789, true, 1),
    ('Chris', 'Taylor', 101265432, false, NULL);


SELECT * FROM Student;

CREATE TABLE User_Migrated (
                               id BIGINT PRIMARY KEY,
                               first_name VARCHAR(255) NOT NULL,
                               has_topic BOOLEAN DEFAULT false,
                               last_name VARCHAR(255) NOT NULL,
                               student_id BIGINT NOT NULL UNIQUE,
                               joined_topic_id BIGINT
);

INSERT INTO User_Migrated (id, first_name, has_topic, last_name, student_id, joined_topic_id)
SELECT id, first_name, has_topic, last_name, student_id, joined_topic_id
FROM Student
WHERE id <= 2;

SELECT * FROM User_Migrated;

DELETE FROM Student
WHERE id <= 2;

SELECT * FROM Student;

UPDATE Student
SET has_topic = true
WHERE id > 2;

ALTER TABLE User_Migrated ADD CONSTRAINT UNIQUE_StudentID UNIQUE (student_id);


