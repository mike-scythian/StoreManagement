package nix.project.store.management.entities.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreStockKey implements Serializable {

    @Column(name="store_id")
    private Long storeId;

    @Column(name="product_id")
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreStockKey that = (StoreStockKey) o;

        if (!storeId.equals(that.storeId)) return false;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        int result = storeId.hashCode();
        result = 31 * result + productId.hashCode();
        return result;
    }
}
