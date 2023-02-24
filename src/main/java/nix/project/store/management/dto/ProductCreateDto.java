package nix.project.store.management.dto;

import nix.project.store.management.models.enums.Units;

public record ProductCreateDto (
     Long id,
     String name,
     Double price,
     Units units,
     String type
    ){}
