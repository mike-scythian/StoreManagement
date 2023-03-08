package nix.project.store.management.services.impl;

import lombok.RequiredArgsConstructor;
import nix.project.store.management.dto.UserCreateDto;
import nix.project.store.management.dto.UserDto;
import nix.project.store.management.dto.mapper.UserMapper;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.exceptions.InvalidPasswordException;
import nix.project.store.management.exceptions.ValueExistsAlreadyException;
import nix.project.store.management.entities.UserEntity;
import nix.project.store.management.repositories.StoreRepository;
import nix.project.store.management.repositories.UserRepository;
import nix.project.store.management.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto create(UserCreateDto userCreateDto) {

        if (userRepository.existsByEmail(userCreateDto.getEmail()))
            throw new ValueExistsAlreadyException();

        UserEntity userEntity = UserEntity.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .roles(userCreateDto.getRoles())
                .store(storeRepository.findById(userCreateDto.getStore())
                        .orElseThrow(DataNotFoundException::new))
                .build();

        return UserMapper.MAPPER.toMap(userRepository.save(userEntity));
    }

    @Override
    public UserDto getUser(Long userId) {

        return UserMapper.MAPPER.toMap(userRepository.findById(userId)
                .orElseThrow(DataNotFoundException::new));
    }

    @Override
    public List<UserDto> getUsers(Integer page) {

        if (page != null) {
            Pageable pageable = PageRequest.of(page, 5);
            return userRepository.findAll(pageable).stream()
                    .map(UserMapper.MAPPER::toMap)
                    .toList();
        }
        else
            return userRepository.findAll().stream()
                    .map(UserMapper.MAPPER::toMap)
                    .toList();
    }

    @Override
    public void updatePassword(String newPassword, String oldPassword) {

        UserEntity userEntity = getCurrentUser();

        if (validatePassword(userEntity, oldPassword)) {

            userEntity.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(userEntity);
        } else
            throw new InvalidPasswordException();
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        UserEntity userEntity = userRepository.findById(userDto.getId())
                .orElseThrow(DataNotFoundException::new);

        if (userDto.getFirstName() != null)
            userEntity.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null)
            userEntity.setLastName(userDto.getLastName());
        if (userDto.getRoles() != null)
            userEntity.setRoles(userDto.getRoles());
        return UserMapper.MAPPER.toMap(userRepository.save(userEntity));
    }

    @Override
    public void changeStore(Long userId, Long storeId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(DataNotFoundException::new);

        if (!userEntity.getStore().getId().equals(storeId)) {
            userEntity.setStore(storeRepository.findById(storeId)
                    .orElseThrow(DataNotFoundException::new));
            userRepository.save(userEntity);
        }
    }

    @Override
    public void delete(Long userId) {
        if (userRepository.existsById(userId))
            userRepository.deleteById(userId);
        else
            throw new DataNotFoundException();
    }

    private boolean validatePassword(UserEntity userEntity, String password) {

        return passwordEncoder.matches(password, userEntity.getPassword());
    }

    private UserEntity getCurrentUser() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(userEmail)
                .orElseThrow(DataNotFoundException::new);
    }
}
