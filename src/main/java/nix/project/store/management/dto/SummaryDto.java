package nix.project.store.management.dto;

import java.time.LocalDateTime;

public record SummaryDto(
        Long id,
        Long product,
        Double payment,
        LocalDateTime timeOperation,
        Long store
) {
}
