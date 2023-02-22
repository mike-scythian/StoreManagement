package nix.project.store.management.repositories;

import nix.project.store.management.models.Sausage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SausageRepository extends JpaRepository<Sausage,Long> {

    List<Sausage> findAll();


}
