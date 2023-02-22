package nix.project.store.management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nix.project.store.management.models.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "create_date")
    private LocalDateTime createTime;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<OrderProduct> orderBody;

    @ManyToOne
    @JoinColumn(name="store", nullable = false)
    @JsonBackReference
    private Store store;

    public Order (LocalDateTime date, Store store, OrderProduct @NonNull ... orderSausages){
        this.createTime = date;
        this.store = store;
        for(OrderProduct orderProduct : orderSausages)
            orderProduct.setOrder(this);
        this.orderBody = Stream.of(orderSausages).collect(Collectors.toSet());
    }
}
