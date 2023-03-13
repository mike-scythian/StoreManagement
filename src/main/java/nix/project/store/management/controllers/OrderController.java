package nix.project.store.management.controllers;


import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.dto.ProductRowDto;
import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.enums.OrderStatus;
import nix.project.store.management.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/rows")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity <OrderProductKey> createRowInOrder(@RequestBody ProductQuantityRowDto productDto){

        return new ResponseEntity<>(orderService.addRow(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity <List<ProductRowDto>> findOrder(@PathVariable long id)  {

        return new ResponseEntity<>(orderService.getOrderBody(id), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity <List<OrderDto>> findOrders(@RequestParam(required = false) Integer page,
                                                      @RequestParam(required = false) String sortParam){

        return  new ResponseEntity<>(orderService.getOrders(page, sortParam), HttpStatus.OK);
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity <List<OrderDto>> findOrdersByStore(@PathVariable long id,
                                                             @RequestParam(required = false) Integer page){



        return new ResponseEntity<>(orderService.getOrdersByStore(id, page), HttpStatus.OK);
    }

    @PutMapping("/push/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity <OrderStatus> pushOrder(@PathVariable long id){

        return new ResponseEntity<>(orderService.pushOrder(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity <Void> deleteOrder(@PathVariable long id){

        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/rows")
    public ResponseEntity <Void> deleteOrderRow(@RequestBody ProductQuantityRowDto productQuantityRowDto){

        orderService.deleteRow(productQuantityRowDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
