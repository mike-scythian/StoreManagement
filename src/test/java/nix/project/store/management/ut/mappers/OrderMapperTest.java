package nix.project.store.management.ut.mappers;

import nix.project.store.management.dto.mapper.OrderMapper;
import nix.project.store.management.entities.Order;
import nix.project.store.management.entities.OrderProduct;
import nix.project.store.management.entities.Product;
import nix.project.store.management.entities.Store;
import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.enums.OrderStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class OrderMapperTest {

    private final OrderMapper orderMapper = OrderMapper.MAPPER;

    private static Order order;

    @BeforeAll
    static void init() {

        LocalDateTime testDateTime = LocalDateTime.of(
                LocalDate.of(2000, 1, 1),
                LocalTime.of(12, 0, 0));

        Set<OrderProduct> testProductSet = Set.of(
                new OrderProduct(new OrderProductKey(1L, 10L), 100.0, new Order(), new Product()),
                new OrderProduct(new OrderProductKey(1L, 20L), 200.0, new Order(), new Product()));

        order = new Order(1L, testDateTime, OrderStatus.NEW, testProductSet, new Store());
    }

    @Test
    void shouldCreateDtoFromEntity() {

        var orderDto = orderMapper.toMap(order);

        Map<Long, Double> testProductMap = order.getOrderBody().stream()
                .collect(Collectors.toMap(OrderProduct::getProductId, OrderProduct::getQuantity));

        var isEqualsByValue = orderDto.getProducts().entrySet().stream()
                .allMatch(x -> x.getValue().equals(testProductMap.get(x.getKey())));

        assertAll(
                () -> assertThat(orderDto.getId()).isEqualTo(order.getId()),
                () -> assertThat(orderDto.getProducts().keySet().size()).isEqualTo(order.getOrderBody().size()),
                () -> assertThat(orderDto.getProducts().keySet()).isEqualTo(testProductMap.keySet()),
                () -> assertThat(isEqualsByValue).isTrue()
        );
    }
}
