package nix.project.store.management.repositories;

import nix.project.store.management.models.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VegetableRepository extends JpaRepository<Vegetable, Long> {
}
