package nix.project.store.management.services.impl;

import lombok.AllArgsConstructor;
import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.OrderProductDto;
import nix.project.store.management.dto.mapper.OrderMapper;
import nix.project.store.management.dto.mapper.OrderProductMapper;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.models.Order;
import nix.project.store.management.models.OrderProduct;
import nix.project.store.management.models.Product;
import nix.project.store.management.models.compositeKeys.OrderProductKey;
import nix.project.store.management.models.enums.OrderStatus;
import nix.project.store.management.repositories.OrderProductRepository;
import nix.project.store.management.repositories.OrderRepository;
import nix.project.store.management.repositories.StoreRepository;
import nix.project.store.management.services.OrderService;
import nix.project.store.management.utility.ProductFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepo;

    private final StoreRepository outletRepo;

    private final OrderProductRepository orderProductRepo;

    @Autowired
    private ProductFactory productFactory;

    @Override
    public long createEmptyOrder(Long outletId) {
        Order order = new Order();

        order.setStore(outletRepo.findById(outletId)
                .orElseThrow(DataNotFoundException::new));
        order.setCreateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        return orderRepo.save(order).getId();
    }

    @Override
    public void fillOrder(Long orderId,
                          Map<Long, Double> productList) {

        if(orderRepo.existsById(orderId))
            productList.forEach((key, value) -> addRow(new OrderProductDto(orderId, key,value)));
    }

    @Override
    public OrderProductDto addRow(OrderProductDto orderProductDto) {

        OrderProduct orderRow = new OrderProduct();

        orderRow.setProduct(getProduct(orderProductDto.productId()));
        orderRow.setOrder(orderRepo.findById(orderProductDto.orderId())
                .orElseThrow(DataNotFoundException::new));
        if(orderProductDto.quantity() != null)
            orderRow.setQuantity(orderProductDto.quantity());

        orderRow.setId(new OrderProductKey(orderProductDto.orderId(), orderProductDto.productId()));

        return OrderProductMapper.MAPPER.toMap(orderProductRepo.save(orderRow));
    }

    @Override
    public OrderDto getOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(DataNotFoundException::new);
        OrderDto orderDto = OrderMapper.MAPPER.toMap(order);

        Map<Long, Double> prodList = orderProductRepo.findById_OrderId(orderId).stream()
                .collect(Collectors.toMap(
                        OrderProduct::getProductId,
                        OrderProduct::getQuantity));
        orderDto.setProducts(new HashMap<>(prodList));

        return orderDto;
    }

    @Override
    public Order getOrderEntity(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(DataNotFoundException::new);
    }

    @Override
    public List<OrderDto> getOrders() {

        return orderRepo.findAll()
                .stream()
                .map(order -> getOrder(order.getId()))
                .toList();
    }

    @Override
    public List<OrderDto> getOrdersByOutlet(Long outletId) {

        return orderRepo.findByStore_Id(outletId)
                .stream()
                .map(OrderMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public OrderProductDto updateRow(OrderProductDto orderProductDto){

        OrderProductKey newKey;

        if(orderProductDto.orderId() == null || orderProductDto.productId() == null)
            throw new IllegalArgumentException();

        newKey = new OrderProductKey(orderProductDto.orderId(), orderProductDto.productId());
        OrderProduct row = new OrderProduct();

        if (orderProductRepo.existsById(newKey))
            row = orderProductRepo.findById(newKey)
                    .orElseThrow(DataNotFoundException::new);
        else {
            row.setId(newKey);
            row.setOrder(orderRepo.findById(orderProductDto.orderId())
                    .orElseThrow(DataNotFoundException::new));
            row.setProduct(getProduct(orderProductDto.productId()));
        }
        row.setQuantity(orderProductDto.quantity());

        return OrderProductMapper.MAPPER.toMap(orderProductRepo.save(row));
    }


    @Override
    public OrderStatus pushOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(DataNotFoundException::new);

        order.setStatus(OrderStatus.IN_PROCESSING);

        return  orderRepo.save(order).getStatus();
    }

    @Override
    public void delete(Long orderId) {

        if(orderRepo.existsById(orderId)) {

            orderProductRepo.deleteById_OrderId(orderId);
            orderRepo.deleteById(orderId);
        }
        else
            throw new DataNotFoundException();
    }

    @Override
    public void deleteRow(OrderProductDto orderProductDto) {

        OrderProductKey key = new OrderProductKey(orderProductDto.orderId(), orderProductDto.productId());
        if(orderProductRepo.existsById(key))
            orderProductRepo.deleteById(key);
        else
           throw new DataNotFoundException();
    }

    @Override
    public OrderDto saveOrder(Order order) {

        return OrderMapper.MAPPER.toMap(orderRepo.save(order));
    }

    private Product getProduct(Long id){

        productFactory.defineProduct(id);
        return productFactory.getProduct();
    }

}
