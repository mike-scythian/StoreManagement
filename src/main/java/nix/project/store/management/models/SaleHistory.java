package nix.project.store.management.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="sale_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product")
    private Long product;

    @Column(name="payment")
    private Double payment;

    @Column(name="date_time")
    private LocalDateTime timeOperation;

    @Column(name="store")
    private Long store;
}
