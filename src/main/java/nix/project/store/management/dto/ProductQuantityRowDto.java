package nix.project.store.management.dto;


public record ProductQuantityRowDto(
    Long ownerId,
    Long productId,
    Double quantity
){}


