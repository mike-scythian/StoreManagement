package nix.project.store.management.services.impl;

import lombok.RequiredArgsConstructor;
import nix.project.store.management.dto.*;
import nix.project.store.management.dto.mapper.*;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.exceptions.NotEnoughLeftoversException;
import nix.project.store.management.models.Order;
import nix.project.store.management.models.Store;
import nix.project.store.management.models.StoreStock;
import nix.project.store.management.models.Product;
import nix.project.store.management.models.compositeKeys.StoreStockKey;
import nix.project.store.management.models.enums.OrderStatus;
import nix.project.store.management.repositories.StoreRepository;
import nix.project.store.management.repositories.StoreStockRepository;
import nix.project.store.management.services.OrderService;
import nix.project.store.management.services.StoreService;
import nix.project.store.management.services.SaleHistoryService;
import nix.project.store.management.utility.ProductFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final SaleHistoryService saleHistoryService;

    @Autowired
    private ProductFactory productFactory;

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
    public List<StoreStockDto> getLeftovers(Long storeId) {

        return storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new)
                .getStoreStock()
                .stream()
                .map(StoreStockMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public StoreDto getStore(Long storeId) {
        return StoreMapper.MAPPER.toMap(storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new));
    }

    @Override
    public List<StoreDto> getStores() {
        return storeRepository.findAll().stream()
                .map(StoreMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public void update(Long storeId, String name) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(DataNotFoundException::new);

        store.setName(Optional.of(name).orElse(store.getName()));

        storeRepository.save(store);
    }

    @Override
    public double sale(StoreStockDto storeStockDto) {

        StoreStock storeStock =
                storeStockRepository
                        .findById(new StoreStockKey(storeStockDto.storeId(), storeStockDto.productId()))
                        .orElseThrow(DataNotFoundException::new);

        if (storeStockDto.quantity() > storeStock.getLeftovers())
            throw new NotEnoughLeftoversException();

        nullToZeroLeftovers();

        Store store = storeRepository.findById(storeStockDto.storeId())
                .orElseThrow(DataNotFoundException::new);
        Product product = getProduct(storeStockDto.productId());

        double resultIncome = store.getIncome() + product.getPrice() * storeStockDto.quantity();
        store.setIncome(resultIncome);
        storeStock.setLeftovers(storeStock.getLeftovers() - storeStockDto.quantity());
        storeStockRepository.save(storeStock);

        saleHistoryService.createReport(
                storeStockDto.productId(),
                product.getPrice() * storeStockDto.quantity(),
                storeStockDto.storeId());

        return storeRepository.save(store).getIncome();
    }

    @Override
    public OrderStatus acceptOrder(Long orderId) {

        Order order = orderService.getOrderEntity(orderId);

        Long storeId = order.getStore().getId();

        Set<StoreStock> stockSet = order.getOrderBody().stream()
                .map(OrderProductMapper.MAPPER::toMap)
                .map(orderProd -> fromOrderToStock(storeId, orderProd))
                .collect(Collectors.toSet());

        storeStockRepository.saveAll(stockSet);
        order.setStatus(OrderStatus.DONE);

        return orderService.saveOrder(order).getStatus();
    }

    @Override
    public void delete(Long storeId) {

        if (storeRepository.existsById(storeId)) {

            List<StoreStock> stockList = storeStockRepository.findByStore_Id(storeId);
            storeStockRepository.deleteAll(stockList);
            storeRepository.deleteById(storeId);
        } else
            throw new DataNotFoundException();
    }

    private StoreStock fromOrderToStock(Long storeId, OrderProductDto orderProductDto) {

        StoreStockKey key = new StoreStockKey(storeId, orderProductDto.productId());
        StoreStock storeStockRow = new StoreStock();
        if (storeStockRepository.existsById(key))
            storeStockRow = storeStockRepository.findById(key)
                    .orElseThrow(DataNotFoundException::new);

        else {
            storeStockRow.setId(key);
            storeStockRow.setProduct(getProduct(orderProductDto.productId()));
            storeStockRow.setStore(storeRepository.findById(storeId)
                    .orElseThrow(DataNotFoundException::new));
        }
        if(storeStockRow.getLeftovers() == null)
            storeStockRow.setLeftovers(orderProductDto.quantity());
        else
            storeStockRow.setLeftovers(storeStockRow.getLeftovers() + orderProductDto.quantity());

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

    private Product getProduct(Long productId) {

        productFactory.defineProduct(productId);
        return productFactory.getProduct();
    }
}