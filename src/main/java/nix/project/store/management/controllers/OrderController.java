package nix.project.store.management.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.OrderProductDto;
import nix.project.store.management.models.compositeKeys.OrderProductKey;
import nix.project.store.management.models.enums.OrderStatus;
import nix.project.store.management.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity <OrderProductKey> createRowInOrder(@RequestBody OrderProductDto productDto){

        return new ResponseEntity<>(orderService.addRow(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity <OrderDto> findOrder(@PathVariable long id) throws JsonProcessingException {

        String responseJsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderService.getOrder(id).getProducts());

        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<OrderDto>> findOrders(@RequestParam int page){

        Pageable pageable = PageRequest.of(page,5);

        return  new ResponseEntity<>(orderService.getOrders(pageable), HttpStatus.OK);
    }

    @GetMapping("/outlets/{id}")
    public ResponseEntity <List<OrderDto>> findOrdersByStore(@PathVariable long id, @RequestParam int page){

        Pageable pageable = PageRequest.of(page,10);

        return new ResponseEntity<>(orderService.getOrdersByStore(id, pageable), HttpStatus.OK);
    }

    @PatchMapping("/processing/{id}/")
    public ResponseEntity <OrderStatus> pushOrder(@PathVariable long id){

        return new ResponseEntity<>(orderService.pushOrder(id), HttpStatus.ACCEPTED);
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
