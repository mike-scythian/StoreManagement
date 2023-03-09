package nix.project.store.management.repositories;

import nix.project.store.management.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {


}
