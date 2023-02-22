package nix.project.store.management.repositories;

import nix.project.store.management.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProductRepository extends CrudRepository<Product,Long> {
}
