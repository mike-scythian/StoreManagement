package nix.project.store.management.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDto{
        private String firstName;
        private String lastName;
        @Email
        private String email;
        private String password;
        private String roles;
        private Long store;
}
