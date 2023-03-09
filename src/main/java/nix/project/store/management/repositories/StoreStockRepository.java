package nix.project.store.management.repositories;

import nix.project.store.management.entities.StoreStock;
import nix.project.store.management.entities.compositeKeys.StoreStockKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock, StoreStockKey> {
    List<StoreStock> findByStoreId(Long id);


}