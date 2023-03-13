package nix.project.store.management.it;


import nix.project.store.management.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Product, Long> {
}
