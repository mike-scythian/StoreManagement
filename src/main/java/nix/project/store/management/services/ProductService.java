package nix.project.store.management.services;

import nix.project.store.management.dto.ProductCreateDto;
import nix.project.store.management.dto.ProductDto;

import java.util.List;

public interface ProductService {


    List<ProductDto> getProducts();

    ProductDto getProduct(Long productId);

    ProductCreateDto createProduct(ProductCreateDto productDto);

    ProductDto update(Long productId, ProductDto productDto);

    void delete(Long productId);

}
