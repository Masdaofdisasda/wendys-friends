CREATE TABLE IF NOT EXISTS horse
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255),
    birthdate     DATE NOT NULL,
    gender        VARCHAR(1) NOT NULL,
    owner         VARCHAR(255),
    mom           BIGINT,
    dad           BIGINT
);

CREATE TABLE IF NOT EXISTS owner
  (
      id            BIGINT AUTO_INCREMENT PRIMARY KEY,
      givenname     VARCHAR(255) NOT NULL,
      surname       VARCHAR(255) NOT NULL,
      email         VARCHAR(255)
  );