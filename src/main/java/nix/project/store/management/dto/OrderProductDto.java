package nix.project.store.management.dto;

public record OrderProductDto(
        Long orderId,
        Long productId,
        Double quantity) {}


