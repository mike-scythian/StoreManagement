package nix.project.store.management.it.h2repo;

import nix.project.store.management.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserH2Repository extends JpaRepository<UserEntity, Long> {
}
