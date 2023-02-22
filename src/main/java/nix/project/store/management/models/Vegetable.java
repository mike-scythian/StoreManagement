package nix.project.store.management.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="vegetable")
@Getter
@Setter
@NoArgsConstructor
public class Vegetable extends Product{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vegetables_sequence")
    @SequenceGenerator(name = "vegetables_sequence", sequenceName = "product_id_seq", allocationSize = 1)
    private Long id;
    private String variety;
    public Vegetable(String name, Double price, String variety){
        super(name,price);
        this.variety = variety;
    }
    @Override
    public boolean equals(Product anotherVegetable) {
        if(!this.getClass().equals(anotherVegetable.getClass()))
            return false;
        Vegetable vegetable = (Vegetable) anotherVegetable;
        return (this.getName().equals(vegetable.getName())) &&
                (this.getVariety().equals(vegetable.getVariety()));
    }
}
