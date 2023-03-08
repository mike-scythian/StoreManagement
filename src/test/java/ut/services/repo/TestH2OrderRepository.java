package ut.services.repo;

import nix.project.store.management.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface TestH2OrderRepository extends CrudRepository<Order, Long> {
}
