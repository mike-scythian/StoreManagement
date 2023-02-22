package nix.project.store.management.repositories;

import nix.project.store.management.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long> {
    List<Store> findAll();

}
