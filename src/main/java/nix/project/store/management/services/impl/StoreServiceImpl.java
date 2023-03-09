package nix.project.store.management.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nix.project.store.management.dto.*;
import nix.project.store.management.dto.mapper.*;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.exceptions.NotEnoughLeftoversException;
import nix.project.store.management.entities.Order;
import nix.project.store.management.entities.Product;
import nix.project.store.management.entities.Store;
import nix.project.store.management.entities.StoreStock;
import nix.project.store.management.entities.compositeKeys.StoreStockKey;
import nix.project.store.management.entities.enums.OrderStatus;
import nix.project.store.management.repositories.StoreRepository;
import nix.project.store.management.repositories.StoreStockRepository;
import nix.project.store.management.services.OrderService;
import nix.project.store.management.services.ProductService;
import nix.project.store.management.services.StoreService;
import nix.project.store.management.services.SummaryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreStockRepository storeStockRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final SummaryService summaryService;


    @Override
    public long create(String name) {

        if (name == null)
            throw new IllegalArgumentException();

        Store store = Store.builder()
                .name(name)
                .openDate(LocalDate.now())
                .income(0.0)
                .build();

        return storeRepository.save(store).getId();
    }

    @Override
    public OrderDto createEmptyOrder(Long storeId) {
        Order order = new Order();

        order.setStore(storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new));
        order.setCreateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        return orderService.saveOrder(order);
    }

    @Override
    public Set<UserDto> getSellers(Long storeId) {

        return storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new)
                .getSellers()
                .stream()
                .map(UserMapper.MAPPER::toMap)
                .collect(Collectors.toSet());
    }

    @Override
    public List<OrderDto> getOrders(Long storeId) {

        return storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new)
                .getOrders()
                .stream()
                .map(OrderMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<ProductRowDto> getLeftovers(Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(DataNotFoundException::new);

        return store.getStoreStock().stream()
                .map(row -> new ProductRowDto(
                        productService.getProduct(row.productId()).getName(),
                        productService.getProduct(row.productId()).getType(),
                        row.getLeftovers()))
                .toList();
    }

    @Override
    public StoreDto getStore(Long storeId) {

        return StoreMapper.MAPPER.toMap(storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new));
    }

    @Override
    public Store getStoreEntity(Long storeId){

        return storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new);
    }

    @Override
    public List<StoreDto> getStores(Integer page) {

        if(page != null) {
            Pageable pageable = PageRequest.of(page, 5);
            return storeRepository.findAll(pageable).stream()
                    .map(StoreMapper.MAPPER::toMap)
                    .toList();
        }
        else
            return storeRepository.findAll().stream()
                    .map(StoreMapper.MAPPER::toMap)
                    .toList();
    }

    @Override
    public StoreDto update(Long storeId, String name) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new);

        store.setName(Optional.of(name).orElse(store.getName()));

        return StoreMapper.MAPPER.toMap(storeRepository.save(store));
    }

    @Override
    @Transactional
    public double sale(ProductQuantityRowDto productQuantityRow) {

        StoreStock storeStock = storeStockRepository.findById(
                new StoreStockKey(productQuantityRow.ownerId(), productQuantityRow.productId()))
                        .orElseThrow(DataNotFoundException::new);

        if (productQuantityRow.quantity() > storeStock.getLeftovers())
            throw new NotEnoughLeftoversException();

        nullToZeroLeftovers();

        Store store = storeRepository.findById(productQuantityRow.ownerId())
                .orElseThrow(DataNotFoundException::new);

        double productPrice = productService.getProduct(productQuantityRow.productId()).getPrice();

        double resultIncome = store.getIncome() + productPrice * productQuantityRow.quantity();
        store.setIncome(resultIncome);
        storeStock.setLeftovers(storeStock.getLeftovers() - productQuantityRow.quantity());
        storeStockRepository.save(storeStock);

        summaryService.createReport(
                productQuantityRow.productId(),
                productPrice * productQuantityRow.quantity(),
                productQuantityRow.ownerId());

        return storeRepository.save(store).getIncome();
    }

    @Override
    @Transactional
    public OrderStatus acceptOrder(Long orderId) {

        Order order = orderService.getOrderEntity(orderId);

        Long storeId = order.getStore().getId();

        Set<StoreStock> stockSet = order.getOrderBody().stream()
                .map(ProductQuantityRowMapper.MAPPER::toMap)
                .map(orderProd -> pushOrderToStore(storeId, orderProd))
                .collect(Collectors.toSet());

        storeStockRepository.saveAll(stockSet);
        order.setStatus(OrderStatus.DONE);

        return orderService.saveOrder(order).getStatus();
    }

    @Override
    public void delete(Long storeId) {

        if (storeRepository.existsById(storeId)) {

            List<StoreStock> stockList = storeStockRepository.findByStoreId(storeId);

            storeStockRepository.deleteAll(stockList);
            storeRepository.deleteById(storeId);
        } else
            throw new DataNotFoundException();
    }

    private StoreStock pushOrderToStore(Long storeId, ProductQuantityRowDto productQuantityRowDto) {

        StoreStockKey key = new StoreStockKey(storeId, productQuantityRowDto.productId());
        StoreStock storeStockRow = new StoreStock();

        if (storeStockRepository.existsById(key))
            storeStockRow = storeStockRepository.findById(key)
                    .orElseThrow(DataNotFoundException::new);

        else {
            Product product = ProductMapper.MAPPER.toEntityMap(productService.getProduct(productQuantityRowDto.productId()));
            storeStockRow.setId(key);
            storeStockRow.setProduct(product);
            storeStockRow.setStore(storeRepository.findById(storeId)
                    .orElseThrow(DataNotFoundException::new));
        }
        if(storeStockRow.getLeftovers() == null)
            storeStockRow.setLeftovers(productQuantityRowDto.quantity());
        else
            storeStockRow.setLeftovers(storeStockRow.getLeftovers() + productQuantityRowDto.quantity());

        return storeStockRow;
    }

    private void nullToZeroLeftovers() {

        List<StoreStock> stockList = storeStockRepository.findAll()
                .stream()
                .filter(row -> row.getLeftovers() == null)
                .toList();
        stockList.forEach(row -> row.setLeftovers(0.0));

        storeStockRepository.saveAll(stockList);
    }

}