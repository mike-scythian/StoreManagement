package nix.project.store.management.it.h2repo;

import nix.project.store.management.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestOrderH2Repository extends JpaRepository<Order, Long> {
}
