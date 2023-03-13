package nix.project.store.management.ut.services;

import nix.project.store.management.dto.UserCreateDto;
import nix.project.store.management.dto.UserDto;
import nix.project.store.management.entities.Product;
import nix.project.store.management.entities.Store;
import nix.project.store.management.entities.StoreStock;
import nix.project.store.management.entities.UserEntity;
import nix.project.store.management.entities.compositeKeys.StoreStockKey;
import nix.project.store.management.repositories.StoreRepository;
import nix.project.store.management.repositories.UserRepository;
import nix.project.store.management.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Test
    void shouldCreateNewUser() {

        var credentialUser = initialCreatedUser();
        var userEntity = initialUser();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(new Store()));
        when(passwordEncoder.encode(anyString())).thenReturn("password");

        var newTestUser = userService.create(credentialUser);

        assertThat(newTestUser).isNotNull();
        assertThat(newTestUser.getEmail()).isEqualTo(userEntity.getEmail());
    }

    @Test
    void shouldUpdateUserInfo() {

        var testUpdateUser = UserDto.builder()
                .id(1L)
                .firstName("Peter")
                .build();
        var userEntity = initialUser();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        var updatedUser = userService.updateUser(testUpdateUser);

        assertThat(updatedUser.getFirstName()).isEqualTo("Peter");
    }

    @Test
    void shouldChangeStoreForUser(){

        var testUser = initialUser();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        userService.changeStore(1L, 100L);

        assertThat(testUser.getStore().getId()).isEqualTo(100L);
    }

    private UserEntity initialUser(){

        return UserEntity.builder()
                .firstName("John")
                .lastName("Dou")
                .email("test@mail.com")
                .password("pswd")
                .roles("ROLE_USER")
                .store(initialStore())
                .build();
    }

    private UserCreateDto initialCreatedUser(){

        return UserCreateDto.builder()
                .firstName("John")
                .lastName("Dou")
                .email("test@mail.com")
                .password("pswd")
                .roles("ROLE_USER")
                .store(1L)
                .build();
    }

    private Store initialStore(){

        return Store.builder()
                .id(100L)
                .openDate(LocalDate.of(2000, 12, 1))
                .name("TestStore")
                .income(1000.0)
                .build();
    }
}
