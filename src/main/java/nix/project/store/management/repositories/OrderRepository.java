package nix.project.store.management.repositories;

import nix.project.store.management.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository <Order,Long> {

    List<Order> findByStoreId(Long id);

    List<Order> findByStoreId(Long id, Pageable pageable);
}
