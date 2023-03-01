package nix.project.store.management.dto;

public record ProductRowDto(
        String productName,
        String productType,
        Double quantity
) {
}
