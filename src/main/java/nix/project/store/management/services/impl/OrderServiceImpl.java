package nix.project.store.management.services.impl;

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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;

    private final OrderProductRepository orderProductRepo;

    private final ProductService productService;


    @Override
    public OrderProductKey addRow(ProductQuantityRowDto productQuantityRowDto) {

        Order order = orderRepo.findById(productQuantityRowDto.ownerId()).orElseThrow(DataNotFoundException::new);

        if(order.getStatus() == OrderStatus.DONE)
            throw new RuntimeException();

        OrderProduct orderRow = new OrderProduct();
        Product product = ProductMapper.MAPPER.toEntityMap(productService.getProduct(productQuantityRowDto.productId()));
        orderRow.setProduct(product);
        orderRow.setOrder(order);

        if (productQuantityRowDto.quantity() != null)
            orderRow.setQuantity(productQuantityRowDto.quantity());

        orderRow.setId(new OrderProductKey(productQuantityRowDto.ownerId(), productQuantityRowDto.productId()));

        return orderProductRepo.save(orderRow).getId();
    }

    @Override
    public OrderDto getOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(DataNotFoundException::new);

        return OrderMapper.MAPPER.toMap(order);

    }

    @Override
    public List<ProductRowDto> getOrderBody(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(DataNotFoundException::new);

        return order.getOrderBody().stream()
                .map(row -> new ProductRowDto(
                        productService.getProduct(row.getProductId()).getName(),
                        productService.getProduct(row.getProductId()).getType(),
                        row.getQuantity()))
                .toList();
    }

    @Override
    public Order getOrderEntity(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(DataNotFoundException::new);
    }

    @Override
    public List<OrderDto> getOrders(Pageable pageable) {
        if(pageable != null)
            return orderRepo.findAll(pageable)
                    .stream()
                    .map(order -> getOrder(order.getId()))
                    .toList();
        else
            return orderRepo.findAll()
                .stream()
                .map(order -> getOrder(order.getId()))
                .toList();
    }

    @Override
    public List<OrderDto> getOrdersByStore(Long storeId, Pageable pageable) {

        return orderRepo.findByStore_Id(storeId, pageable)
                .stream()
                .map(OrderMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public OrderStatus pushOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(DataNotFoundException::new);

        if (order.getStatus() == OrderStatus.NEW) {

            order.setStatus(OrderStatus.IN_PROCESSING);
            return orderRepo.save(order).getStatus();

        } else
            return order.getStatus();
    }

    @Override
    public void delete(Long orderId) {

        if (orderRepo.existsById(orderId)) {

            orderProductRepo.deleteById_OrderId(orderId);
            orderRepo.deleteById(orderId);

        } else
            throw new DataNotFoundException();
    }

    @Override
    public void deleteRow(ProductQuantityRowDto productQuantityRowDto) {

        OrderProductKey key = new OrderProductKey(productQuantityRowDto.ownerId(), productQuantityRowDto.productId());

        if (orderProductRepo.existsById(key))
            orderProductRepo.deleteById(key);
        else
            throw new DataNotFoundException();
    }

    @Override
    public OrderDto saveOrder(Order order) {

        return OrderMapper.MAPPER.toMap(orderRepo.save(order));
    }
}
