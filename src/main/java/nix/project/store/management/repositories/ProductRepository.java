package nix.project.store.management.repositories;

import nix.project.store.management.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByNameAndType(String name, String type);

}
