package nix.project.store.management.dto;

public record StoreStockDto(
        Long storeId,
        Long productId,
        Double quantity) {
}
