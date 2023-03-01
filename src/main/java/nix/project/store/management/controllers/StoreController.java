package nix.project.store.management.controllers;

import nix.project.store.management.dto.*;
import nix.project.store.management.models.enums.OrderStatus;
import nix.project.store.management.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;


    @PostMapping
    public ResponseEntity<Long> createStore(@RequestBody String storeName){

        return new ResponseEntity<>(storeService.create(storeName), HttpStatus.CREATED);
    }

    @PostMapping("{id}/orders")
    public ResponseEntity <OrderDto> createOrder(@RequestBody long storeId){

        return new ResponseEntity<>(storeService.createEmptyOrder(storeId), HttpStatus.CREATED);
    }
    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity< List<StoreDto> > getAllStores(@RequestParam int page){

        Pageable pageable = PageRequest.of(page, 5);

        return new ResponseEntity<>(storeService.getStores(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStore(@PathVariable long id){

        return new ResponseEntity<>(storeService.getStore(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/sellers")
    public ResponseEntity<Set<UserDto> > getSellerSet(@PathVariable long id){

        return new ResponseEntity<>(storeService.getSellers(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/orders")
    public ResponseEntity < List<OrderDto> > getOrderList(@PathVariable long id){

        return new ResponseEntity<>(storeService.getOrders(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/stocks")
    public ResponseEntity <List<ProductRowDto>> getLeftovers(@PathVariable long id){

        return new ResponseEntity<>(storeService.getLeftovers(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity <OrderStatus> acceptOrder(@PathVariable long id){

        return new ResponseEntity<>(storeService.acceptOrder(id), HttpStatus.ACCEPTED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateInfo(@PathVariable long id, @RequestBody String newName){

        storeService.update(id, newName);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/selling")
    public ResponseEntity<Double> saleProduct(@RequestBody StoreStockDto storeStockDto){

        return new ResponseEntity<>(storeService.sale(storeStockDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable long id){

        storeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
