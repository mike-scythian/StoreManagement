package nix.project.store.management.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import nix.project.store.management.models.Product;
import nix.project.store.management.models.enums.OrderStatus;

import java.io.Serializable;
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
