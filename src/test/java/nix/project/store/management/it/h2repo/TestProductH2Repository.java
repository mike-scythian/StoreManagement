package nix.project.store.management.it.h2repo;


import nix.project.store.management.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestProductH2Repository extends JpaRepository<Product, Long> {
}
