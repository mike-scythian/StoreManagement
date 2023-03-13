package nix.project.store.management.ut.mappers;

import nix.project.store.management.dto.UserDto;
import nix.project.store.management.dto.mapper.UserMapper;
import nix.project.store.management.entities.Store;
import nix.project.store.management.entities.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = UserMapper.MAPPER;

    private static UserEntity user;

    @BeforeAll
    static void init(){

        user = UserEntity.builder()
                .firstName("Taras")
                .lastName("Shevchenko")
                .email("sheva@gmail.com")
                .password("qwerty")
                .roles("ROLE_SUPERADMIN")
                .store(new Store())
                .build();
    }

    @Test
    void shouldCreateDtoFromEntity(){

        UserDto testUserDto = userMapper.toMap(user);

        assertAll(
                () -> assertThat(testUserDto.getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(testUserDto.getFirstName()).isEqualTo(user.getFirstName()),
                () -> assertThat(testUserDto.getLastName()).isEqualTo(user.getLastName())
        );
    }

    @Test
    void shouldThrowNPE(){

        assertThrows(NullPointerException.class, () -> UserEntity.builder().build());
    }
}
