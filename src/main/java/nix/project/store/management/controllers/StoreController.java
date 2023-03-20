package nix.project.store.management.controllers;

import nix.project.store.management.dto.*;
import nix.project.store.management.entities.enums.OrderStatus;
import nix.project.store.management.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;


    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> createStore(@RequestParam String storeName) {

        return new ResponseEntity<>(storeService.create(storeName), HttpStatus.CREATED);
    }

    @PostMapping("/orders/{storeId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<OrderDto> createOrder(@PathVariable long storeId) {

        return new ResponseEntity<>(storeService.createEmptyOrder(storeId), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<StoreDto>> getAllStores(@RequestParam(required = false) Integer page) {

        return new ResponseEntity<>(storeService.getStores(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStore(@PathVariable long id) {

        return new ResponseEntity<>(storeService.getStore(id), HttpStatus.OK);
    }

    @GetMapping("/sellers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Set<UserDto>> getSellers(@PathVariable long id) {

        return new ResponseEntity<>(storeService.getSellers(id), HttpStatus.OK);
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<List<ProductRowDto>> getLeftovers(@PathVariable long id) {

        return new ResponseEntity<>(storeService.getLeftovers(id), HttpStatus.OK);
    }

    @PutMapping("/accept/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<OrderStatus> acceptOrder(@PathVariable long id) {

        return new ResponseEntity<>(storeService.acceptOrder(id), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> updateInfo(@PathVariable long id, @RequestParam String newName) {

        storeService.update(id, newName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/sale")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Double> saleProduct(@RequestBody ProductQuantityRowDto productQuantityRow) {

        return new ResponseEntity<>(storeService.sale(productQuantityRow), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteStore(@PathVariable long id) {

        storeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
