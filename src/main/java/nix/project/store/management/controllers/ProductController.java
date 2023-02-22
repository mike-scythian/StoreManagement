package nix.project.store.management.controllers;


import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.services.impl.SausageServiceImpl;
import nix.project.store.management.services.impl.VegetableServiceImpl;
import nix.project.store.management.utility.ProductFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private SausageServiceImpl sausageService;
    @Autowired
    private VegetableServiceImpl vegetableService;
    @Autowired
    private ProductFactory factory;

    @PostMapping("/{productType}")
    public ResponseEntity<Long> createProduct(@PathVariable String productType, @RequestBody ProductDto productDto) {

        if (productType.equalsIgnoreCase("sausage"))
            factory.setService(sausageService);
        if (productType.equalsIgnoreCase("vegetable"))
            factory.setService(vegetableService);

        return new ResponseEntity<>(factory.getService().addProduct(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id, @RequestBody ProductDto productDto) {

        factory.defineService(id);

        return new ResponseEntity<>(factory.getService().update(id, productDto), HttpStatus.OK);
    }

    @GetMapping("/all/{productType}")
    public ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable String productType) {

        if (productType.equalsIgnoreCase("sausage"))
            factory.setService(sausageService);
        if (productType.equalsIgnoreCase("vegetable"))
            factory.setService(vegetableService);

        return new ResponseEntity<>(factory.getService().getProducts(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {

        factory.defineService(id);

        return new ResponseEntity<>(factory.getService().getProduct(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {

        factory.defineService(id);
        factory.getService().delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
