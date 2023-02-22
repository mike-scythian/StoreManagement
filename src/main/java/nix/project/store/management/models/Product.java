package nix.project.store.management.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@MappedSuperclass
public abstract class Product{

    @NonNull
    @Column(name="name")
    private String name;
    @NonNull
    @Column(name="price")
    private Double price;


    public abstract boolean equals(Product anotherProduct);
    public abstract Long getId();
}
