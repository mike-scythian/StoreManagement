package nix.project.store.management.dto;

public record ProductRowDto(

        Long id,
        String productName,
        String productType,
        Double quantity
) {}
