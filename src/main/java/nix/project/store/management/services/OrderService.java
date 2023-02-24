package nix.project.store.management.services;

import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.OrderProductDto;
import nix.project.store.management.models.Order;
import nix.project.store.management.models.compositeKeys.OrderProductKey;
import nix.project.store.management.models.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderProductKey addRow(OrderProductDto orderProductDto);

    OrderDto getOrder(Long orderId);

    Order getOrderEntity(Long orderId);

    List<OrderDto> getOrders();

    List <OrderDto> getOrdersByStore(Long storeId);

    void delete(Long orderId);

    void deleteRow(OrderProductDto orderProductDto);

    OrderDto saveOrder(Order order);

    OrderStatus pushOrder(Long orderId);

}
