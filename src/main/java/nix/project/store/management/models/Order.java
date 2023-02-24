package nix.project.store.management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import nix.project.store.management.models.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
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
