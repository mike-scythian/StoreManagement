package nix.project.store.management.services;

import nix.project.store.management.dto.*;
import nix.project.store.management.entities.Store;
import nix.project.store.management.entities.enums.OrderStatus;

import java.util.List;
import java.util.Set;

public interface StoreService {

    long create(String name);

    Set<UserDto> getSellers(Long storeId);

    List<OrderDto> getOrders(Long storeId);

    List<ProductRowDto> getLeftovers(Long storeId);

    StoreDto getStore(Long storeId);
    Store getStoreEntity(Long storeId);

    List<StoreDto> getStores(Integer page);

    StoreDto update(Long storeId, String name);

    double sale(ProductQuantityRowDto productQuantityRow);

    OrderDto createEmptyOrder(Long storeId);

    OrderStatus acceptOrder(Long orderId);

    void delete(Long storeId);

}
