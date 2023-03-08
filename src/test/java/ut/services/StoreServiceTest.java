package ut.services;


import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.dto.ProductRowDto;
import nix.project.store.management.dto.mapper.OrderMapper;
import nix.project.store.management.entities.*;
import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.compositeKeys.StoreStockKey;
import nix.project.store.management.entities.enums.OrderStatus;
import nix.project.store.management.entities.enums.Units;
import nix.project.store.management.exceptions.NotEnoughLeftoversException;
import nix.project.store.management.repositories.StoreRepository;
import nix.project.store.management.repositories.StoreStockRepository;
import nix.project.store.management.services.OrderService;
import nix.project.store.management.services.ProductService;
import nix.project.store.management.services.SummaryService;
import nix.project.store.management.services.impl.StoreServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreStockRepository storeStockRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private ProductService productService;

    @Mock
    private SummaryService summaryService;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    void shouldCreateStore() {

        var testStore = initialStore();

        when(storeRepository.save(any(Store.class))).thenReturn(testStore);

        var createStoreTest = storeService.create("TestStore");

        assertThat(createStoreTest).isEqualTo(8L);
    }

    @Test
    void shouldCreateEmptyOrder() {

        var testStore = initialStore();
        var order = OrderDto.builder()
                .id(1L)
                .createTime(LocalDateTime.now())
                .status(OrderStatus.NEW)
                .store(testStore.getId())
                .build();

        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(testStore));
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);

        var testNewOrder = storeService.createEmptyOrder(8L);

        assertThat(testNewOrder).isNotNull();
        assertThat(testNewOrder.getStatus()).isEqualTo(OrderStatus.NEW);
    }

    @Test
    void shouldGetLeftovers() {

        var testStore = initialStore();
        var product = initialProduct();

        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(testStore));
        when(productService.getProduct(anyLong())).thenReturn(product);

        var leftovers = storeService.getLeftovers(8L);

        assertThat(leftovers).hasSize(2);
        assertThat(leftovers.stream().mapToDouble(ProductRowDto::quantity)).contains(100.0, 200.0);
    }

    @Test
    void shouldUpdateStoreName() {

        var testStore = initialStore();

        when(storeRepository.save(any(Store.class))).thenReturn(testStore);
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(testStore));

        var testUpdateStore = storeService.update(8L, "TestUpdate");

        assertThat(testUpdateStore).isNotNull();
        assertThat(testUpdateStore.getName()).isEqualTo("TestUpdate");
    }

    @Test
    void shouldGetIncomeAfterSuccessSale() {

        var testStore = initialStore();
        var product = initialProduct();

        var storeStockTest = new StoreStock(
                new StoreStockKey(1L, 10L),
                100.0,
                new Store(),
                new Product());

        when(storeStockRepository.findById(any(StoreStockKey.class))).thenReturn(Optional.of(storeStockTest));
        when(storeStockRepository.save(any(StoreStock.class))).thenReturn(storeStockTest);
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(testStore));
        when(storeRepository.save(any(Store.class))).thenReturn(testStore);
        when(productService.getProduct(anyLong())).thenReturn(product);

        var testSale = storeService.sale(new ProductQuantityRowDto(testStore.getId(), product.getId(), 50.0));

        assertThat(testSale).isEqualTo(1500.0);
    }

    @Test
    void shouldGetExceptionAfterWrongSale() {

        var productRow = new ProductQuantityRowDto(1L, 10L, 2000.0);
        var storeStockTest = new StoreStock(
                new StoreStockKey(1L, 10L),
                100.0,
                new Store(),
                new Product());

        when(storeStockRepository.findById(any(StoreStockKey.class))).thenReturn(Optional.of(storeStockTest));

        assertThrows(NotEnoughLeftoversException.class, () -> storeService.sale(productRow));
    }

    @Test
    void shouldSetupDoneOrderStatus(){

        var testStore = initialStore();
        var order = initialOrder();
        var product = initialProduct();

        when(orderService.getOrderEntity(anyLong())).thenReturn(order);
        when(storeStockRepository.saveAll(anyCollection())).thenReturn(testStore.getStoreStock().stream().toList());
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(testStore));
        when(productService.getProduct(anyLong())).thenReturn(product);
        when(orderService.saveOrder(any(Order.class))).thenReturn(OrderMapper.MAPPER.toMap(order));

        storeService.acceptOrder(1L);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.DONE);
    }

    @Test
    void shouldChangeLeftoversAfterOrderAccept(){

        var testOrder = initialOrder();
        var testStore = initialStore();
        var product = initialProduct();
        var storeStockTest = new StoreStock(
                new StoreStockKey(8L, 10L),
                100.0,
                new Store(),
                new Product());

        when(orderService.getOrderEntity(anyLong())).thenReturn(testOrder);
        when(storeStockRepository.saveAll(anyCollection())).thenReturn(testStore.getStoreStock().stream().toList());
        when(orderService.saveOrder(any(Order.class))).thenReturn(OrderMapper.MAPPER.toMap(testOrder));
        when(storeStockRepository.existsById(any(StoreStockKey.class))).thenReturn(true);
        when(storeStockRepository.findById(any(StoreStockKey.class))).thenReturn(Optional.of(storeStockTest));

        storeService.acceptOrder(1L);

        assertThat(testStore.getStoreStock().size()).isEqualTo(2);
        assertThat(testStore.getStoreStock()
                .stream()
                .mapToDouble(StoreStock::getLeftovers)).contains(200.0,200.0);
    }

    private Store initialStore(){

        Set<StoreStock> leftoversSet = new HashSet<>();

        leftoversSet.add(new StoreStock(new StoreStockKey(8L, 10L), 100.0, new Store(), new Product()));
        leftoversSet.add(new StoreStock(new StoreStockKey(8L, 20L), 200.0, new Store(), new Product()));

        return Store.builder()
                .id(8L)
                .openDate(LocalDate.of(2000, 12, 1))
                .name("TestStore")
                .income(1000.0)
                .storeStock(leftoversSet)
                .sellers(Collections.EMPTY_SET)
                .orders(Collections.EMPTY_SET)
                .build();
    }

    private Order initialOrder(){

        var testDateTime = LocalDateTime.of(
                LocalDate.of(2000, 1, 1),
                LocalTime.of(12, 0, 0));

        Set<OrderProduct> testProductSet = new HashSet<>();

        testProductSet.add(new OrderProduct(new OrderProductKey(1L, 10L), 100.0, new Order(), new Product()));
        testProductSet.add(new OrderProduct(new OrderProductKey(1L, 20L), 200.0, new Order(), new Product()));

        return new Order(1L, testDateTime, OrderStatus.IN_PROCESSING, testProductSet, initialStore());

    }

    private ProductDto initialProduct(){

        return ProductDto.builder()
                .id(10L)
                .name("TestProd")
                .price(10.0)
                .units(Units.APIECE)
                .type("testType")
                .build();
    }
}