package nix.project.store.management.repositories.repoDto;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class ProductTotalPaymentDto implements ProductTotalPayment {
    private Double payment;

    private Long product;

    @Override
    public Double getTotalPayment() {
        return payment;
    }

    @Override
    public Long getIdProduct() {
        return product;
    }
}
