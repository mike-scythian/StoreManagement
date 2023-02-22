package nix.project.store.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VegetableDto extends ProductDto{

    private Long id;

    public VegetableDto(Long id, String name, Double price, String variety){

        super(name,price, variety);
        this.id = id;
    }
}
