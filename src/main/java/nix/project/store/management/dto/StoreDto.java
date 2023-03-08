package nix.project.store.management.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private Long id;
    private String name;
    private Double income;
    private LocalDate openDate;

    private List<UserDto> sellers;

    private HashMap<ProductDto, Double> leftovers;

}
