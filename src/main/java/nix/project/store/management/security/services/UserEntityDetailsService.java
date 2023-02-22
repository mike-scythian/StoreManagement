package nix.project.store.management.security.services;

import nix.project.store.management.models.UserEntity;
import nix.project.store.management.repositories.UserRepository;
import nix.project.store.management.security.UserEntityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserEntityDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByEmail(userEmail);

        return user.map(UserEntityUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user with such email not found" + userEmail));
    }
}
