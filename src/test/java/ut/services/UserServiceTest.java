package ut.services;

import nix.project.store.management.dto.UserCreateDto;
import nix.project.store.management.dto.UserDto;
import nix.project.store.management.entities.Store;
import nix.project.store.management.entities.UserEntity;
import nix.project.store.management.repositories.StoreRepository;
import nix.project.store.management.repositories.UserRepository;
import nix.project.store.management.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private static UserEntity user;

    @BeforeAll
    static void init() {

        user = UserEntity.builder()
                .firstName("John")
                .lastName("Dou")
                .email("test@mail.com")
                .password("pswd")
                .roles("ROLE_USER")
                .store(new Store())
                .build();
    }

    @Test
    void shouldCreateNewUser() {

        UserCreateDto credentialUser = UserCreateDto.builder()
                .firstName("John")
                .lastName("Dou")
                .email("test@mail.com")
                .password("pswd")
                .roles("ROLE_USER")
                .store(1L)
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(new Store()));
        when(passwordEncoder.encode(anyString())).thenReturn("password");

        var newTestUser = userService.create(credentialUser);

        assertThat(newTestUser).isNotNull();
        assertThat(newTestUser.getEmail()).isEqualTo(user.getEmail());
    }

    /*@Test
    void shouldUpdateUserInfo() {

        var testUpdateUser = UserDto.builder()
                .firstName("Peter")
                .build();
       // var innerUser = new UserEntity()

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        var updatedUser = userService.updateUser(testUpdateUser);

        assertThat(updatedUser.getFirstName()).isEqualTo("Peter");
    }*/


}
