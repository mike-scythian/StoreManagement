package nix.project.store.management.services;

import nix.project.store.management.models.Product;

import java.util.List;

public interface OfficeService {


    List<Product> getAssortment(ProductService productService);

    double getIncome(Long outletId);

}
