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
CREATE TABLE IF NOT EXISTS stores_products(
  store_id INT,
  product_id INT,
  leftovers DECIMAL(10,2),
  PRIMARY KEY (store_id, product_id)
  );
