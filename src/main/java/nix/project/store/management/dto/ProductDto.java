package nix.project.store.management.dto;

import lombok.*;
import nix.project.store.management.entities.enums.Units;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private Double price;
    private Units units;
    private String type;


}
