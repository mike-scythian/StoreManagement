package ut.mappers;

import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.mapper.ProductMapper;
import nix.project.store.management.entities.Product;
import nix.project.store.management.entities.enums.Units;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper productMapper = ProductMapper.MAPPER;

    private static Product product;
    private static ProductDto productDto;

    @BeforeAll
    static void init(){

        product = Product.builder()
                .id(1L)
                .name("Pineapple")
                .price(100.0)
                .type("fruit")
                .units(Units.KG)
                .build();

        productDto  = ProductDto.builder()
                .id(2L)
                .name("Cucumber")
                .price(129.0)
                .type("vegetable")
                .units(Units.KG)
                .build();
    }

    @Test
    void shouldCreateDtoFromEntity(){
        ProductDto localDto = productMapper.toMap(product);

        assertAll(
                () -> assertThat(localDto.getName()).isEqualTo(product.getName()),
                () -> assertThat(localDto.getPrice()).isEqualTo(product.getPrice()),
                () -> assertThat(localDto.getUnits()).isEqualTo(product.getUnits())
        );
    }

    @Test
    void shouldConvertEntityFromDto(){

        Product localEntity = productMapper.toEntityMap(productDto);

        assertAll(
                () -> assertThat(localEntity.getName()).isEqualTo(productDto.getName()),
                () -> assertThat(localEntity.getPrice()).isEqualTo(productDto.getPrice()),
                () -> assertThat(localEntity.getUnits()).isEqualTo(productDto.getUnits())
        );
    }
}
