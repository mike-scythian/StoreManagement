package nix.project.store.management.repositories;

import nix.project.store.management.models.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository <Order,Long> {
    List<Order> findByStore_Id(Long id);

    List<Order> findByStore_Id(Long id, Pageable pageable);

    List<Order> findAll();

    List<Order> findAll(Pageable pageable);

}
