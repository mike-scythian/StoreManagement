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
    public List<OrderDto> getOrders(Integer page, String sortParam) {

        if(page == null && sortParam == null)
            return orderRepo.findAll()
                    .stream()
                    .map(order -> getOrder(order.getId()))
                    .toList();;

        Pageable pageable;

        if(page == null)
            page = 0;

        if(sortParam != null) {

            if(sortParam.equals("createTime"))
                pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, sortParam));
            else
                pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, sortParam));

            return orderRepo.findAll(pageable)
                    .stream()
                    .map(order -> getOrder(order.getId()))
                    .toList();
        }
        else {
            pageable = PageRequest.of(page, 5);
            return orderRepo.findAll(pageable)
                    .stream()
                    .map(order -> getOrder(order.getId()))
                    .toList();
        }
    }

    @Override
    public List<OrderDto> getOrdersByStore(Long storeId, Integer page) {

        if(page != null) {
            Pageable pageable = PageRequest.of(page, 10);
            return orderRepo.findByStoreId(storeId, pageable)
                    .stream()
                    .map(OrderMapper.MAPPER::toMap)
                    .toList();
        }
        else
            return orderRepo.findByStoreId(storeId)
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
    @Transactional
    public void delete(Long orderId) {

        if (orderRepo.existsById(orderId)) {

            orderProductRepo.deleteByIdOrderId(orderId);
            orderRepo.deleteById(orderId);

        } else
            throw new DataNotFoundException();
    }

    @Override
    public void deleteRow(@NonNull ProductQuantityRowDto productQuantityRowDto) {

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
