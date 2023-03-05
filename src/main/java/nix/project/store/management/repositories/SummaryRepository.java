package nix.project.store.management.repositories;

import nix.project.store.management.models.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {

    List<Summary> findAll();
    List<Summary> findByProductAndTimeOperationBetween(Long productId, LocalDateTime timeOperationStart, LocalDateTime timeOperationEnd);
    List<Summary> findByStoreAndTimeOperationBetween(Long storeId,
                                                     LocalDateTime timeOperationStart,
                                                     LocalDateTime timeOperationEnd);

    List<Summary> findByProduct(Long productId);
    List<Summary> findByStore(Long id);

   /* @Query(
            value ="SELECT p_id, SUM(p) " +
                    "FROM nix_project.summary " +
                    "GROUP BY product_id",
            nativeQuery = true
    )
    List<ProductBySaleDto> findTopTenProducts();*/
}