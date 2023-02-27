package nix.project.store.management.controllers;


import nix.project.store.management.dto.ProductCreateDto;
import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.services.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping()
    public ResponseEntity<ProductCreateDto> createProduct(@RequestBody ProductCreateDto productCreateDto) {

        return new ResponseEntity<>(productService.createProduct(productCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id, @RequestBody ProductDto productDto) {

        return new ResponseEntity<>(productService.update(id, productDto), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam int page) {

        Pageable pageable = PageRequest.of(page,3);

        return new ResponseEntity<>(productService.getProducts(pageable), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {

        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {

       productService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
