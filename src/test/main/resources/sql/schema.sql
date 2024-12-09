CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(256) PRIMARY KEY,
    user_name VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS main_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(256) NOT NULL,
    description TEXT,
    deadline TIMESTAMP,
    percent DOUBLE,
    total_time DOUBLE,
    author_id VARCHAR(256),
    status SMALLINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES m_user(user_id)
);

CREATE TABLE IF NOT EXISTS sub_task (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     title VARCHAR(256) NOT NULL,
     description TEXT,
     target_id BIGINT,
     author_id VARCHAR(256),
     started_at TIMESTAMP,
     finished_at TIMESTAMP,
     total_time DOUBLE,
     status SMALLINT,
     created_at TIMESTAMP,
     updated_at TIMESTAMP,
     FOREIGN KEY (author_id) REFERENCES m_user(user_id),
     FOREIGN KEY (target_id) REFERENCES main_task(id)
);


ALTER TABLE m_user 
ADD authority SMALLINT NOT NULL AFTER password;