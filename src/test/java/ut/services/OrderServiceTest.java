package ut.services;

import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.dto.ProductRowDto;
import nix.project.store.management.entities.Order;
import nix.project.store.management.entities.OrderProduct;
import nix.project.store.management.entities.Product;
import nix.project.store.management.entities.Store;
import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.enums.OrderStatus;
import nix.project.store.management.entities.enums.Units;
import nix.project.store.management.repositories.OrderProductRepository;
import nix.project.store.management.repositories.OrderRepository;
import nix.project.store.management.services.impl.OrderServiceImpl;
import nix.project.store.management.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private static Order order;


    @BeforeAll
    static void init() {

        var testDateTime = LocalDateTime.of(
                LocalDate.of(2000, 1, 1),
                LocalTime.of(12, 0, 0));

        Set<OrderProduct> testProductSet = new HashSet<>();

        testProductSet.add(new OrderProduct(new OrderProductKey(1L, 10L), 100.0, new Order(), new Product()));
        testProductSet.add(new OrderProduct(new OrderProductKey(1L, 20L), 200.0, new Order(), new Product()));

        order = new Order(1L, testDateTime, OrderStatus.NEW, testProductSet, new Store());
    }

    @Test
    void shouldAddNewRow() {

        when(orderProductRepository.save(any(OrderProduct.class)))
                .thenReturn(new OrderProduct(new OrderProductKey(1L, 30L), 10.0, null, null));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        var testRow = orderService.addRow(new ProductQuantityRowDto(2L, 30L, 10.0));

        assertThat(testRow).isNotNull();
        assertThat(testRow.getOrderId()).isEqualTo(order.getId());
        assertThat(testRow.getProductId()).isEqualTo(30L);
    }

    @Test
    void shouldGetOrderBody() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(productService.getProduct(anyLong()))
                .thenReturn(new ProductDto(100L, "testProduct", 1.0, Units.KG, "testType"));

        var testList = orderService.getOrderBody(order.getId());

        assertThat(testList.size()).isEqualTo(order.getOrderBody().size());
        assertThat(testList.stream().mapToDouble(ProductRowDto::quantity)).containsExactly(100.0, 200.0);
    }

    @Test
    void shouldChangeOrderStatus() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        assertThat(orderService.pushOrder(1L)).isEqualTo(OrderStatus.IN_PROCESSING);
    }
}
