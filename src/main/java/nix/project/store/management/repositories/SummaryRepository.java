package nix.project.store.management.repositories;

import nix.project.store.management.entities.Summary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {

    List<Summary> findByProductAndTimeOperationBetween(Long productId,
                                                       LocalDateTime timeOperationStart,
                                                       LocalDateTime timeOperationEnd,
                                                       Pageable pageable);
    List<Summary> findByStoreAndTimeOperationBetween(Long storeId,
                                                     LocalDateTime timeOperationStart,
                                                     LocalDateTime timeOperationEnd,
                                                     Pageable pageable);

    List<Summary> findByProduct(Long productId, Pageable pageable);
    List<Summary> findByStore(Long id, Pageable pageable);


}