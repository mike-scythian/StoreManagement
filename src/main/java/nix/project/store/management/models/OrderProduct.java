package nix.project.store.management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nix.project.store.management.models.compositeKeys.OrderProductKey;

@Entity
@Table(name = "orders_products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    @EmbeddedId
    private OrderProductKey id;

    private Double quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;


    public Long getProductId() {
        return this.id.getProductId();
    }

    public Long getOrderId() {
        return this.id.getOrderId();
    }
}
