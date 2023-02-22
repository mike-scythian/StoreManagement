package nix.project.store.management.controllers;


import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.OrderProductDto;
import nix.project.store.management.models.enums.OrderStatus;
import nix.project.store.management.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity <Long> createOrder(@RequestBody long outletId){

        return new ResponseEntity<>(orderService.createEmptyOrder(outletId), HttpStatus.CREATED);
    }

    @PostMapping("/rows")
    public ResponseEntity <OrderProductDto> createRowInOrder(@RequestBody OrderProductDto orderProductDto){

        return new ResponseEntity<>(orderService.addRow(orderProductDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity <OrderDto> findOrder(@PathVariable long id){

        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<OrderDto>> findOrders(){

        return  new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

    @GetMapping("/outlets/{id}")
    public ResponseEntity <List<OrderDto>> findOrdersByOutlet(@PathVariable long id){

        return new ResponseEntity<>(orderService.getOrdersByOutlet(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity <Void> fillOrder(@PathVariable long id, @RequestBody Map<Long, Double> products){

        orderService.fillOrder(id,products);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/processing/{id}/")
    public ResponseEntity <OrderStatus> pushOrder(@PathVariable long id){

        return new ResponseEntity<>(orderService.pushOrder(id), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/rows")
    public ResponseEntity <OrderProductDto> editOrderRow(@RequestBody OrderProductDto orderProductDto){

        return new ResponseEntity<>(orderService.updateRow(orderProductDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteOrder(@PathVariable long id){

        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/rows")
    public ResponseEntity <Void> deleteOrderRow(@RequestBody OrderProductDto orderProductDto){

        orderService.deleteRow(orderProductDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
