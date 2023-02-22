package nix.project.store.management.dto;

import java.time.LocalDateTime;

public record SaleHistoryDto(
        Long id,
        Long product,
        Double payment,
        LocalDateTime timeOperation,
        Long store
) {
}
