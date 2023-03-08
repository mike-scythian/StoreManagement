package nix.project.store.management.services;

import nix.project.store.management.dto.UserCreateDto;
import nix.project.store.management.dto.UserDto;

import java.util.List;


public interface UserService {
    UserDto create(UserCreateDto userCreateDto);

    UserDto getUser(Long userId);

    List<UserDto> getUsers(Integer page);

    void updatePassword(String newPassword, String oldPassword);

    UserDto updateUser(UserDto userDto);

    void changeStore(Long userId, Long outletId);

    void delete(Long userId);
}
