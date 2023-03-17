package nix.project.store.management.it.h2repo;

import nix.project.store.management.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestStoreH2Repository extends JpaRepository<Store, Long> {
}
