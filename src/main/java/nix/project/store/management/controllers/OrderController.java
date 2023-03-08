package nix.project.store.management.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.dto.ProductRowDto;
import nix.project.store.management.entities.compositeKeys.OrderProductKey;
import nix.project.store.management.entities.enums.OrderStatus;
import nix.project.store.management.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ObjectMapper jsonMapper;

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
    public ResponseEntity <List<OrderDto>> findOrders(@RequestParam int page){

        if(page < 0)
            return  new ResponseEntity<>(orderService.getOrders(null), HttpStatus.OK);
        else {

            Pageable pageable = PageRequest.of(page, 5);

            return new ResponseEntity<>(orderService.getOrders(pageable), HttpStatus.OK);
        }
    }
    @GetMapping("/sort")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity <List<OrderDto>> findOrders(@RequestParam int page, @RequestParam String sortParam){

        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.ASC, sortParam));

        return  new ResponseEntity<>(orderService.getOrders(pageable), HttpStatus.OK);
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity <List<OrderDto>> findOrdersByStore(@PathVariable long id, @RequestParam int page){

        Pageable pageable = PageRequest.of(page,10);

        return new ResponseEntity<>(orderService.getOrdersByStore(id, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}/push/")
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
