CREATE TABLE IF NOT EXISTS orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  create_date Date,
  status VARCHAR(255),
  store INT
);

INSERT INTO orders (id, create_date, status, store) VALUES
  (1,'2023-03-16', 'NEW', 1),
  (3, '2023-03-15', 'IN_PROCESSING', 2),
  (2, '2023-03-14', 'DONE', 1);

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

CREATE TABLE IF NOT EXISTS stores (
  id INT AUTO_INCREMENT PRIMARY KEY,
  income DECIMAL(10,2),
  name VARCHAR(255),
  open_date Date
);

INSERT INTO stores (id, income, name, open_date)
VALUES
(1, 250000.00, 'Test Store 1', '2022-01-01'),
(2, 50000.00, 'Test Store 2', '2022-01-01');

