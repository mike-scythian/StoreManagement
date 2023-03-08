package nix.project.store.management.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import nix.project.store.management.entities.enums.Units;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private Double price;

    @Column(name = "units")
    @Enumerated(EnumType.STRING)
    private Units units;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "product",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<OrderProduct> bodyOrder;

    @OneToMany(mappedBy = "product",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<StoreStock> storeStock;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;

        if (!getId().equals(product.getId())) return false;
        if (!getName().equals(product.getName())) return false;
        if (!getPrice().equals(product.getPrice())) return false;
        if (getUnits() != product.getUnits()) return false;
        return getType().equals(product.getType());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getUnits().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }
}