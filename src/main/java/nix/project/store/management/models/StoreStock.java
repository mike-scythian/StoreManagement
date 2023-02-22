package nix.project.store.management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nix.project.store.management.models.compositeKeys.StoreStockKey;

@Entity
@Table(name="stores_products")
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
    @JoinColumn(name="store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @MapsId("product_id")
    @JoinColumn(name="product_id")
    private Sausage sausage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Vegetable vegetable;

    public <P extends Product> void setProduct(P product) {
        if(product.getClass().equals(Sausage.class))
            this.sausage = (Sausage) product;
        if (product.getClass().equals(Vegetable.class))
            this.vegetable = (Vegetable) product;
    }
    public Long storeId(){
        return this.id.getStoreId();
    }
    public Long productId(){
        return this.id.getProductId();
    }
}
