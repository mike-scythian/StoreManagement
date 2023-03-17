CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255),
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  password VARCHAR(255),
  roles VARCHAR(255),
  store INT
);

INSERT INTO users (id, email, first_name, last_name, password, roles, store)
VALUES
  (4, 'john.doe@example.com', 'John', 'Doe', '$2a$10$XYpCmtZTvnm88v1fk8oBfO4KODayY1jXJ7/TfPLYba/U8FcDYJWbC', 'ROLE_USER', 1), --password123
  (2, 'jane.smith@example.com', 'Jane', 'Smith', 'mypassword', 'ROLE_USER', 2),
  (3, 'bob.jones@example.com', 'Bob', 'Jones', 'passw0rd', 'ROLE_ADMIN', 3);


CREATE TABLE IF NOT EXISTS stores (
  id INT AUTO_INCREMENT PRIMARY KEY,
  income DECIMAL(10,2),
  name VARCHAR(255),
  open_date Date
);

INSERT INTO stores (id, income, name, open_date)
VALUES (1, 250000.00, 'Test Store', '2022-01-01');
