package nix.project.store.management.services;

import nix.project.store.management.dto.ProductDto;

import java.util.List;

public interface ProductService {


    List<ProductDto> getProducts(Integer page);

    ProductDto getProduct(Long productId);

    ProductDto createProduct(ProductDto productDto);

    ProductDto update(Long productId, ProductDto productDto);

    void delete(Long productId);

}
