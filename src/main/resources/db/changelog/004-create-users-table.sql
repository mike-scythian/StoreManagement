CREATE TABLE IF NOT EXISTS nix_project.users(
    id SERIAL NOT NULL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    password  VARCHAR(255),
    roles VARCHAR(100),
    store BIGINT NOT NULL,
    FOREIGN KEY (store) REFERENCES nix_project.stores(id));