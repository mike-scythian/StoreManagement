package nix.project.store.management.repositories;

import nix.project.store.management.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAll();

    List<UserEntity> findAll(Pageable pageable);

}
