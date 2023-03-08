package nix.project.store.management.services;

import nix.project.store.management.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {


    List<ProductDto> getProducts(Pageable pageable);

    ProductDto getProduct(Long productId);

    ProductDto createProduct(ProductDto productDto);

    ProductDto update(Long productId, ProductDto productDto);

    void delete(Long productId);

}
