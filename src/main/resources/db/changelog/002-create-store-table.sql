CREATE TABLE IF NOT EXISTS nix_project.stores(
    id SERIAL NOT NULL PRIMARY KEY,
    income NUMERIC(8,2),
    name VARCHAR(100),
    open_date DATE NOT NULL);