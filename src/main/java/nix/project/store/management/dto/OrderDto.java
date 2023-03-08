package nix.project.store.management.dto;

import lombok.*;
import nix.project.store.management.entities.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.HashMap;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto{
    private  Long id;
    private  LocalDateTime createTime;
    private Long store;
    private HashMap<Long,Double> products;
    private OrderStatus status;


}
