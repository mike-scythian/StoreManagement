package nix.project.store.management.repositories;

import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductKey> {
    void deleteByIdOrderId(Long orderId);

}
