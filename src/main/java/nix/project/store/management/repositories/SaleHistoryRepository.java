package nix.project.store.management.repositories;

import nix.project.store.management.repositories.repoDto.ProductTotalPayment;
import nix.project.store.management.models.SaleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleHistoryRepository extends JpaRepository<SaleHistory, Long> {

    List<SaleHistory> findAll();
    List<SaleHistory> findByProductAndTimeOperationBetween(Long productId, LocalDateTime timeOperationStart, LocalDateTime timeOperationEnd);
    List<SaleHistory> findByStoreAndTimeOperationBetween(Long storeId,
                                                         LocalDateTime timeOperationStart,
                                                         LocalDateTime timeOperationEnd);

    List<SaleHistory> findByProduct(Long productId);
    List<SaleHistory> findByStore(Long id);

   /* @Query("SELECT SUM(sh.payment), sh.productId FROM SaleHistory sh GROUP BY sh.productId")
    List<ProductTotalPayment> findTopTenProducts();*/
}