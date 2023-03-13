package nix.project.store.management.ut.mappers;

import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.dto.mapper.ProductQuantityRowMapper;
import nix.project.store.management.entities.*;
import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.compositeKeys.StoreStockKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ProductQuantityRowMapperTest {

    private final ProductQuantityRowMapper mapper = ProductQuantityRowMapper.MAPPER;

    private static OrderProduct orderProductObject;
    private static StoreStock storeStockObject;

    @BeforeAll
    static void init(){

        orderProductObject = new OrderProduct(
                new OrderProductKey(1L, 10L),
                1000.0,
                new Order(),
                new Product());

        storeStockObject = new StoreStock(
                new StoreStockKey(2L, 20L),
                2000.0,
                new Store(),
                new Product());
    }

    @Test
    void shouldCreateDtoFromOrderProductObject(){

        ProductQuantityRowDto testDto = mapper.toMap(orderProductObject);

        assertAll(
                () -> assertThat(testDto.ownerId()).isEqualTo(orderProductObject.getId().getOrderId()),
                () -> assertThat(testDto.productId()).isEqualTo(orderProductObject.getId().getProductId()),
                () -> assertThat(testDto.quantity()).isEqualTo(orderProductObject.getQuantity())
        );
    }

    @Test
    void shouldCreateDtoFromStoreStockObject(){

        ProductQuantityRowDto testDto = mapper.toMap(storeStockObject);

        assertAll(
                () -> assertThat(testDto.ownerId()).isEqualTo(storeStockObject.getId().getStoreId()),
                () -> assertThat(testDto.productId()).isEqualTo(storeStockObject.getId().getProductId()),
                () -> assertThat(testDto.quantity()).isEqualTo(storeStockObject.getLeftovers())
        );
    }
}
