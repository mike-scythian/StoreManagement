package nix.project.store.management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nix.project.store.management.models.compositeKeys.StoreStockKey;

@Entity
@Table(name = "stores_products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreStock {

    @EmbeddedId
    private StoreStockKey id;

    @Column
    private Double leftovers;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @MapsId("store_id")
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    public Long storeId() {
        return this.id.getStoreId();
    }

    public Long productId() {
        return this.id.getProductId();
    }
}
