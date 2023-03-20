package nix.project.store.management.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="summary")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="productId")
    private Long product;

    @Column(name="payment")
    private Double payment;

    @Column(name="date_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeOperation;

    @Column(name="store")
    private Long store;
}
