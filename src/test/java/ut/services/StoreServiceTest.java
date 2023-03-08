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
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    private static Store store;

    @BeforeAll
    static void init() {

        Set<StoreStock> leftoversSet = new HashSet<>();

        leftoversSet.add(new StoreStock(new StoreStockKey(8L, 10L), 100.0, new Store(), new Product()));
        leftoversSet.add(new StoreStock(new StoreStockKey(8L, 20L), 200.0, new Store(), new Product()));

        store = Store.builder()
                .id(8L)
                .openDate(LocalDate.of(2000, 12, 1))
                .name("TestStore")
                .income(1000.0)
                .storeStock(leftoversSet)
                .sellers(Collections.EMPTY_SET)
                .orders(Collections.EMPTY_SET)
                .build();
    }

    @Test
    void shouldCreateStore() {

        when(storeRepository.save(any(Store.class))).thenReturn(store);

        var createStoreTest = storeService.create("TestStore");

        assertThat(createStoreTest).isEqualTo(8L);
    }

    @Test
    void shouldCreateEmptyOrder() {

        var testDateTime = LocalDateTime.of(
                LocalDate.of(2000, 1, 1),
                LocalTime.of(12, 0, 0));

        var order = OrderDto.builder()
                .id(1L)
                .createTime(testDateTime)
                .status(OrderStatus.NEW)
                .store(store.getId())
                .build();

        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);

        var testNewOrder = storeService.createEmptyOrder(8L);

        assertThat(testNewOrder).isNotNull();
        assertThat(testNewOrder.getStatus()).isEqualTo(OrderStatus.NEW);
    }

    @Test
    void shouldGetLeftovers() {

        Set<StoreStock> innerLeftoversSet = new HashSet<>();

        innerLeftoversSet.add(new StoreStock(new StoreStockKey(8L, 10L), 100.0, new Store(), new Product()));
        innerLeftoversSet.add(new StoreStock(new StoreStockKey(8L, 20L), 200.0, new Store(), new Product()));

        Store innerTestStore = Store.builder()
                .id(8L)
                .openDate(LocalDate.of(2000, 12, 1))
                .name("TestStore")
                .income(1000.0)
                .storeStock(innerLeftoversSet)
                .sellers(Collections.EMPTY_SET)
                .orders(Collections.EMPTY_SET)
                .build();

        var product = ProductDto.builder()
                .id(1L)
                .name("TestProd")
                .price(10.0)
                .units(Units.APIECE)
                .type("testType")
                .build();

        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(innerTestStore));
        when(productService.getProduct(anyLong())).thenReturn(product);

        var leftovers = storeService.getLeftovers(8L);

        assertThat(leftovers).hasSize(2);
        assertThat(leftovers.stream().mapToDouble(ProductRowDto::quantity)).containsExactly(100.0, 200.0);
    }

    @Test
    void shouldUpdateStoreName() {

        when(storeRepository.save(any(Store.class))).thenReturn(store);
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));

        var testUpdateStore = storeService.update(8L, "TestUpdate");

        assertThat(testUpdateStore).isNotNull();
        assertThat(testUpdateStore.getName()).isEqualTo("TestUpdate");
    }

    @Test
    void shouldGetIncomeAfterSuccessSale() {

        var storeStockTest = new StoreStock(
                new StoreStockKey(1L, 10L),
                100.0,
                new Store(),
                new Product());

        var product = ProductDto.builder()
                .id(10L)
                .name("TestProd")
                .price(10.0)
                .units(Units.APIECE)
                .type("testType")
                .build();

        when(storeStockRepository.findById(any(StoreStockKey.class))).thenReturn(Optional.of(storeStockTest));
        when(storeStockRepository.save(any(StoreStock.class))).thenReturn(storeStockTest);
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));
        when(storeRepository.save(any(Store.class))).thenReturn(store);
        when(productService.getProduct(anyLong())).thenReturn(product);


        var testSale = storeService.sale(new ProductQuantityRowDto(store.getId(), product.getId(), 50.0));

        assertThat(testSale).isEqualTo(1500.0);
    }

    @Test
    void shouldGetExceptionAfterWrongSale() {

        var storeStockTest = new StoreStock(
                new StoreStockKey(1L, 10L),
                100.0,
                new Store(),
                new Product());

        var productRow = new ProductQuantityRowDto(1L, 10L, 2000.0);

        when(storeStockRepository.findById(any(StoreStockKey.class))).thenReturn(Optional.of(storeStockTest));

        assertThrows(NotEnoughLeftoversException.class, () -> storeService.sale(productRow));
    }

    @Test
    void shouldSetupDoneOrderStatus(){

        Set<OrderProduct> testProductSet = new HashSet<>();

        testProductSet.add(new OrderProduct(new OrderProductKey(1L, 10L), 100.0, new Order(), new Product()));
        testProductSet.add(new OrderProduct(new OrderProductKey(1L, 20L), 200.0, new Order(), new Product()));

        var order = new Order(1L, LocalDateTime.now(), OrderStatus.IN_PROCESSING, testProductSet, store);

        var product = ProductDto.builder()
                .id(10L)
                .name("TestProd")
                .price(10.0)
                .units(Units.APIECE)
                .type("testType")
                .build();

        when(orderService.getOrderEntity(anyLong())).thenReturn(order);
        when(storeStockRepository.saveAll(anyCollection())).thenReturn(store.getStoreStock().stream().toList());
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));
        when(productService.getProduct(anyLong())).thenReturn(product);
        when(orderService.saveOrder(any(Order.class))).thenReturn(OrderMapper.MAPPER.toMap(order));

        storeService.acceptOrder(1L);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.DONE);
    }
}