DO
$do$
BEGIN
   IF NOT EXISTS (SELECT FROM nix_project.stores) THEN
      INSERT INTO nix_project.stores (id, income, name, open_date)
      VALUES (1, 0.0, 'main' ,'01-01-2000');
   END IF;
END
$do$