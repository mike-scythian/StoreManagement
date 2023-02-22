package nix.project.store.management.dto;

public record AuthRequestDto(
        String email,
        String password) {
}
