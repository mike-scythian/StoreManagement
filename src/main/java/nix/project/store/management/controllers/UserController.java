package nix.project.store.management.controllers;

import nix.project.store.management.dto.UserCreateDto;
import nix.project.store.management.dto.UserDto;
import nix.project.store.management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto userDto) {

        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }

    @PatchMapping("/password")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {

        userService.updatePassword(newPassword, oldPassword);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUserInfo(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam int page) {

        if(page < 0)
            return new ResponseEntity<>(userService.getUsers(null), HttpStatus.OK);
        else {
            Pageable pageable = PageRequest.of(page, 10);

            return new ResponseEntity<>(userService.getUsers(pageable), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable long id) {

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> changeStore(@PathVariable long id, @RequestParam long newStoreId) {

        userService.changeStore(id, newStoreId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
