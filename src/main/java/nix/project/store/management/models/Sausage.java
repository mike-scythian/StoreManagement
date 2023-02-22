package nix.project.store.management.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sausage")
public class Sausage extends Product{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sausages_sequence")
    @SequenceGenerator(name = "sausages_sequence", sequenceName = "product_id_seq", allocationSize = 1)
    private Long id;
    private String type;

    public Sausage(String name, Double price, String type){
        super(name, price);
        this.type = type;
    }
    @OneToMany(mappedBy = "sausage",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<OrderProduct> bodyOrder;

    @OneToMany(mappedBy = "sausage",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<StoreStock> storeStock;

    @Override
    public boolean equals(@NonNull Product anotherProduct) {
        if(!this.getClass().equals(anotherProduct.getClass()))
            return false;
        Sausage sausage = (Sausage) anotherProduct;
        return (this.getName().equals(sausage.getName())) &&
                (this.getType().equals(sausage.getType()));
    }

    @Override
    public int hashCode() {

        int resultHash = this.getName().hashCode();
        resultHash += 33 * this.getType().hashCode();
        return resultHash;
    }
}