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

    @Column(name = "quantity")
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
    private Sausage sausage;

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Vegetable vegetable;*/


    public <P extends Product> void setProduct(P product) {
        if (product instanceof Sausage) {
            this.sausage = (Sausage) product;
     //       this.id.setProductId(sausage.getId());
        }
      /*  if (product instanceof Vegetable) {
            this.vegetable = (Vegetable) product;
            this.id.setProductId(vegetable.getId());
        }*/
    }

    public Long getProductId() {
        return this.id.getProductId();
    }

    public Long getOrderId() {
        return this.id.getOrderId();
    }
}
