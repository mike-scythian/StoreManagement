package nix.project.store.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SausageDto extends ProductDto{
    Long id;

    public SausageDto(Long id, String name, Double price, String type){

        super(name,price, type);
        this.id = id;
    }
}
