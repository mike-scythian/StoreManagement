package nix.project.store.management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record SummaryDto(
        Long id,
        Long product,
        Double payment,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")LocalDateTime timeOperation,
        Long store
) {
}
