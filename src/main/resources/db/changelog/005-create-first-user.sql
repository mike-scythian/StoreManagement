DO
$do$
BEGIN
   IF NOT EXISTS (SELECT FROM nix_project.users) THEN
      INSERT INTO nix_project.users(id, email, first_name, last_name, password, roles, store)
        VALUES (1, 'admin@email.com', 'admin', 'admin', '$2a$12$p5aiZh6xL1D86aghtkvy1uE4KNdD.TKtmaLd./e5Bxq4M3gAr4aGC','ROLE_ADMIN' ,1);
   END IF;
END
$do$