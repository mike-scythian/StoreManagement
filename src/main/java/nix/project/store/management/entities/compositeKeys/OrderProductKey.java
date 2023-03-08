package nix.project.store.management.entities.compositeKeys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProductKey implements Serializable {

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductKey that)) return false;

        if (!getOrderId().equals(that.getOrderId())) return false;
        return getProductId().equals(that.getProductId());
    }

    @Override
    public int hashCode() {
        int result = getOrderId().hashCode();
        result = 31 * result + getProductId().hashCode();
        return result;
    }
}
