CREATE TABLE IF NOT EXISTS stores (
  id INT AUTO_INCREMENT PRIMARY KEY,
  income DECIMAL(10,2),
  name VARCHAR(255),
  open_date Date
);

INSERT INTO stores (income, name, open_date)
VALUES
  (15000.00, 'Store A', '2022-01-01'),
  (20000.00, 'Store B', '2022-02-01'),
  (25000.00, 'Store C', '2022-03-01');

CREATE TABLE IF NOT EXISTS orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  create_date Date,
  status VARCHAR(255),
  store INT
);

INSERT INTO orders (create_date, status, store) VALUES
  ('2023-03-16', 'NEW', 1),
  ('2023-03-15', 'IN_PROCESSING', 2),
  ('2023-03-14', 'DONE', 1);

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

CREATE TABLE IF NOT EXISTS stores_products(
  store_id INT,
  product_id INT,
  leftovers DECIMAL(10,2),
  PRIMARY KEY (store_id, product_id)
  );

INSERT INTO stores_products (store_id, product_id, leftovers)
VALUES
  (1, 10, 20.50),
  (1, 11, 15.75),
  (1, 12, 5.00);

CREATE TABLE IF NOT EXISTS products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  type VARCHAR(255),
  units VARCHAR(255),
  price DECIMAL(10, 2)
);

INSERT INTO products (id, name, price, type, units)
VALUES
(10, 'Product A', 19.99, 'Type 1', 'KG'),
(11, 'Product B', 49.99, 'Type 2', 'APIECE'),
(12, 'Product C', 7.99, 'Type 1', 'KG');

CREATE TABLE IF NOT EXISTS orders_products(
  order_id INT,
  product_id INT,
  quantity DECIMAL(10,2),
  PRIMARY KEY (order_id, product_id)
);

INSERT INTO orders_products (order_id, product_id, quantity)
VALUES
(1, 12, 100.0),
(2, 10, 200.0),
(2, 11, 50.0);

CREATE TABLE IF NOT EXISTS summary(
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_time TIMESTAMP,
    payment DECIMAL(10,2),
    product_id INT,
    store INT
);