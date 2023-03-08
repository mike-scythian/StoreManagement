package ut.services;

import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.mapper.ProductMapper;
import nix.project.store.management.entities.Product;
import nix.project.store.management.entities.enums.Units;
import nix.project.store.management.exceptions.ValueExistsAlreadyException;
import nix.project.store.management.repositories.ProductRepository;
import nix.project.store.management.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper mapper;
    @InjectMocks
    private ProductServiceImpl productService;

    private  static Product product;
    private static  ProductDto productDto;

    @BeforeAll
    static void init(){

        product = new Product(1L, "Orange", 100.0, Units.KG, "fruit", null, null);
        productDto = new ProductDto(2L, "Orange", 150.0, Units.KG, "fruit");
    }

    @Test
    void shouldCreateProduct(){

        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.existsByNameAndType(anyString(),anyString())).thenReturn(false);

        var createdProduct = productService.createProduct(productDto);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getId())
                .isNotNull().isNotNegative();
    }

    @Test
    void shouldThrowValueExistsAlreadyException(){

        when(productRepository.existsByNameAndType("Orange", "fruit")).thenThrow(ValueExistsAlreadyException.class);

        assertThrows(ValueExistsAlreadyException.class, ()->productService.createProduct(productDto));
    }

    @Test
    void shouldUpdateProductInfo(){

        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var updatedProduct = productService.update(1L, productDto);

        assertThat(updatedProduct.getPrice()).isEqualTo(150.0);
    }
}
