package nix.project.store.management.controllers;

import lombok.RequiredArgsConstructor;
import nix.project.store.management.dto.AuthRequestDto;
import nix.project.store.management.security.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService tokenService;


    @PostMapping("/login")
    public ResponseEntity<String> authGetToken(@RequestBody AuthRequestDto userCredentials) {

        return new ResponseEntity<>(tokenService.generateToken(userCredentials.email()), HttpStatus.OK);
    }

}
