package nix.project.store.management.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "stores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "income")
    private Double income;
    @Column(name = "open_date")
    private LocalDate openDate;
    @Column(name = "name")
    private String name;
    @OneToMany(
            mappedBy = "store",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<Order> orders;
    @OneToMany(
            mappedBy = "store",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<UserEntity> sellers;

    @OneToMany(
            mappedBy = "store",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<StoreStock> storeStock;

}
