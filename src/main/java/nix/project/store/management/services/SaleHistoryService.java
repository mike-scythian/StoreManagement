package nix.project.store.management.services;

import nix.project.store.management.repositories.repoDto.ProductTotalPayment;
import nix.project.store.management.dto.SaleHistoryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleHistoryService {

    void createReport(Long productId, Double saleSum, Long storeId);

    List<SaleHistoryDto> getReports();

    List<SaleHistoryDto> getByStore(Long storeId);

    List<SaleHistoryDto> getByProduct(Long productId);

    List<SaleHistoryDto> getByStoreForPeriod(Long storeId, LocalDateTime startDate, LocalDateTime finishDate);

    List<SaleHistoryDto> getByProductForPeriod(Long productId, LocalDateTime startDate, LocalDateTime finishDate);

    List<ProductTotalPayment> getTopTenProducts();
}
