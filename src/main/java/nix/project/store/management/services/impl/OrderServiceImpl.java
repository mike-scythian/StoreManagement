package nix.project.store.management.services.impl;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.dto.ProductRowDto;
import nix.project.store.management.dto.mapper.OrderMapper;
import nix.project.store.management.dto.mapper.ProductMapper;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.entities.Order;
import nix.project.store.management.entities.OrderProduct;
import nix.project.store.management.entities.Product;
import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.enums.OrderStatus;
import nix.project.store.management.repositories.OrderProductRepository;
import nix.project.store.management.repositories.OrderRepository;
import nix.project.store.management.services.OrderService;
import nix.project.store.management.services.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final ProductService productService;


    @Override
    public OrderProductKey addRow(ProductQuantityRowDto productQuantityRowDto) {

        Order order = orderRepository.findById(productQuantityRowDto.ownerId()).orElseThrow(DataNotFoundException::new);

        if (order.getStatus() == OrderStatus.DONE)
            throw new RuntimeException();

        OrderProduct orderRow = new OrderProduct();
        Product product = ProductMapper.MAPPER.toEntityMap(productService.getProduct(productQuantityRowDto.productId()));
        orderRow.setProduct(product);
        orderRow.setOrder(order);

        if (productQuantityRowDto.quantity() != null)
            orderRow.setQuantity(productQuantityRowDto.quantity());

        orderRow.setId(new OrderProductKey(productQuantityRowDto.ownerId(), productQuantityRowDto.productId()));

        return orderProductRepository.save(orderRow).getId();
    }

    @Override
    public OrderDto getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(DataNotFoundException::new);

        return OrderMapper.MAPPER.toMap(order);

    }

    @Override
    public List<ProductRowDto> getOrderBody(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(DataNotFoundException::new);

        return order.getOrderBody().stream()
                .map(row -> new ProductRowDto(
                        row.getProductId(),
                        productService.getProduct(row.getProductId()).getName(),
                        productService.getProduct(row.getProductId()).getType(),
                        row.getQuantity()))
                .toList();
    }

    @Override
    public Order getOrderEntity(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(DataNotFoundException::new);
    }

    @Override
    public List<OrderDto> getOrders(Integer page, String sortParam) {

        if (page == null && sortParam == null)
            return orderRepository.findAll()
                    .stream()
                    .map(order -> getOrder(order.getId()))
                    .toList();

        Pageable pageable;

        if (page == null)
            page = 0;

        if (sortParam != null) {
            pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, sortParam));

            return orderRepository.findAll(pageable)
                    .stream()
                    .map(order -> getOrder(order.getId()))
                    .toList();
        } else {
            pageable = PageRequest.of(page, 5);
            return orderRepository.findAll(pageable)
                    .stream()
                    .map(order -> getOrder(order.getId()))
                    .toList();
        }
    }

    @Override
    public List<OrderDto> getOrdersByStore(Long storeId, Integer page) {

        if (page != null) {
            Pageable pageable = PageRequest.of(page, 10);
            return orderRepository.findByStoreId(storeId, pageable)
                    .stream()
                    .map(OrderMapper.MAPPER::toMap)
                    .toList();
        } else
            return orderRepository.findByStoreId(storeId)
                    .stream()
                    .map(OrderMapper.MAPPER::toMap)
                    .toList();
    }

    @Override
    public OrderStatus pushOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(DataNotFoundException::new);

        if (order.getStatus() == OrderStatus.NEW) {

            order.setStatus(OrderStatus.IN_PROCESSING);
            return orderRepository.save(order).getStatus();

        } else
            return order.getStatus();
    }

    @Override
    @Scheduled(cron = "${scheduled-start-time}")
    public void processOrder() {

        orderRepository.findAll().stream()
                .filter(order -> order.getStatus().equals(OrderStatus.NEW))
                .forEach(order -> {
                    order.setStatus(OrderStatus.IN_PROCESSING);
                    orderRepository.save(order);
                });
    }

    @Override
    @Transactional
    public void delete(Long orderId) {

        if (orderRepository.existsById(orderId)) {

            orderProductRepository.deleteByIdOrderId(orderId);
            orderRepository.deleteById(orderId);

        } else
            throw new DataNotFoundException();
    }

    @Override
    public void deleteRow(@NonNull ProductQuantityRowDto productQuantityRowDto) {

        OrderProductKey key = new OrderProductKey(productQuantityRowDto.ownerId(), productQuantityRowDto.productId());

        if (orderProductRepository.existsById(key))
            orderProductRepository.deleteById(key);
        else
            throw new DataNotFoundException();
    }

    @Override
    public OrderDto saveOrder(Order order) {

        return OrderMapper.MAPPER.toMap(orderRepository.save(order));
    }
}
