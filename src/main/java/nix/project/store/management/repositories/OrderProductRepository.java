package nix.project.store.management.repositories;

import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductKey> {
    void deleteById_OrderId(Long orderId);
    Set<OrderProduct> findById_OrderId(Long orderId);

    boolean existsById_OrderId(Long orderId);



}
