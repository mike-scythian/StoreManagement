package nix.project.store.management.services;

import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.models.Product;
import nix.project.store.management.utility.ProductFactory;

import java.util.List;

public interface ProductService {

    default Product findProduct(Long productId){

        ProductFactory factory = new ProductFactory();
        factory.defineProduct(productId);
        return factory.getProduct();
    }

    <T extends ProductDto>List<T> getProducts();

    ProductDto getProduct(Long productId);

    long addProduct(ProductDto productDto);

    ProductDto update(Long productId, ProductDto productDto);

    void delete(Long productId);

    <T extends Product> boolean isUnique(T product);



}
