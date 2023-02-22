package nix.project.store.management.services;

import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.OrderProductDto;
import nix.project.store.management.models.Order;
import nix.project.store.management.models.enums.OrderStatus;

import java.util.List;
import java.util.Map;

public interface OrderService {

    long createEmptyOrder(Long outletId);

    void fillOrder(Long orderId, Map<Long, Double> productList);

    OrderProductDto addRow(OrderProductDto orderProductDto);

    OrderDto getOrder(Long orderId);

    Order getOrderEntity(Long orderId);

    List<OrderDto> getOrders();

    List <OrderDto> getOrdersByOutlet(Long outletId);

    OrderProductDto updateRow(OrderProductDto orderProductDto);

    void delete(Long orderId);

    void deleteRow(OrderProductDto orderProductDto);

    OrderDto saveOrder(Order order);

    OrderStatus pushOrder(Long orderId);

}
