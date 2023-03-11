package nix.project.store.management.dto;

public record RequestUpdatePasswordDto(String oldPassword, String newPassword) {
}
