package nix.project.store.management.repositories;

import nix.project.store.management.models.compositeKeys.OrderProductKey;
import nix.project.store.management.models.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderProductRepository extends CrudRepository <OrderProduct, OrderProductKey> {
    void deleteById_OrderId(Long orderId);
    Set<OrderProduct> findById_OrderId(Long orderId);

    boolean existsById_OrderId(Long orderId);



}
