package nix.project.store.management.controllers;

import nix.project.store.management.dto.UserCreateDto;
import nix.project.store.management.dto.UserDto;
import nix.project.store.management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody UserCreateDto userDto) {

        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {

        userService.updatePassword(newPassword, oldPassword);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUserInfo(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable long id) {

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> changeOutlet(@PathVariable long id, @RequestParam long newOutletId) {

        userService.changeOutlet(id, newOutletId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
