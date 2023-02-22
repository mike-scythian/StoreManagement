package nix.project.store.management.services;

import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.StoreDto;
import nix.project.store.management.dto.StoreStockDto;
import nix.project.store.management.dto.UserDto;
import nix.project.store.management.models.enums.OrderStatus;

import java.util.List;
import java.util.Set;

public interface StoreService {

    long create(String name);

    Set<UserDto> getSellers(Long storeId);

    List<OrderDto> getOrders(Long storeId);

    List<StoreStockDto> getLeftovers(Long storeId);

    StoreDto getStore(Long storeId);

    List<StoreDto> getStores();

    void update(Long storeId, String name);

    double sale(StoreStockDto storeStockDto);

    OrderStatus acceptOrder(Long orderId);

    void delete(Long storeId);

}
