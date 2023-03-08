package nix.project.store.management.services;

import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.dto.ProductRowDto;
import nix.project.store.management.entities.Order;
import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    OrderProductKey addRow(ProductQuantityRowDto productQuantityRowDto);

    OrderDto getOrder(Long orderId);
    List<ProductRowDto> getOrderBody(Long orderId);

    Order getOrderEntity(Long orderId);

    List<OrderDto> getOrders(Pageable pageable);

    List <OrderDto> getOrdersByStore(Long storeId, Pageable pageable);

    void delete(Long orderId);

    void deleteRow(ProductQuantityRowDto productQuantityRowDto);

    OrderDto saveOrder(Order order);

    OrderStatus pushOrder(Long orderId);

}
