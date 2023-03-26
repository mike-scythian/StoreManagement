package nix.project.store.management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import nix.project.store.management.entities.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.HashMap;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private Long store;
    private HashMap<Long, Double> products;
    private OrderStatus status;
}
