package nix.project.store.management.repositories;

import nix.project.store.management.entities.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreRepository extends CrudRepository<Store,Long> {
    List<Store> findAll();

    List<Store> findAll(Pageable pageable);

}
