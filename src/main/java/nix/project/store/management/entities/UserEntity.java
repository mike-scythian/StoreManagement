package nix.project.store.management.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NonNull
    private String firstName;

    @Column(name = "last_name")
    @NonNull
    private String lastName;

    @Column(name = "email")
    @NonNull
    @Email
    private String email;

    @Column(name = "password")
    @NonNull
    private String password;

    @Column(name = "roles")
    @NonNull
    private String roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store", nullable = false)
    @JsonBackReference
    @NonNull
    private Store store;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @NoArgsConstructor
    public static class UserBuilder {

        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String roles;
        private Store store;

        public UserBuilder firstName(@NonNull String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(@NonNull String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(@NonNull String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(@NonNull String password) {
            this.password = password;
            return this;
        }
        public UserBuilder roles(String roles){
            this.roles = roles;
            return this;
        }

        public UserBuilder store(@NonNull Store store) {
            this.store = store;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(firstName, lastName, email, password, roles, store);
        }
    }
}
