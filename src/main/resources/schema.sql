CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    zip_code VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(255) NOT NULL,
    apartment VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS enrollees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(40) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    gender VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    passing_score INTEGER NOT NULL,
    address_id BIGINT UNIQUE,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS exam_scores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(255) NOT NULL,
    score INTEGER NOT NULL,
    enrollee_id BIGINT NOT NULL,
    FOREIGN KEY (enrollee_id) REFERENCES enrollees(id) ON DELETE CASCADE
);