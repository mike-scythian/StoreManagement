package nix.project.store.management.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Office{
    private List<Store> stores;
    private List<UserEntity> userEntities;
    private List<Product> productList;
    private List<Order> orders;
    private Double totalIncome;
}