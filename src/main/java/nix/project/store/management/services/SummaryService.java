package nix.project.store.management.services;

import nix.project.store.management.dto.SummaryDto;
import nix.project.store.management.dto.ProductBySaleDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SummaryService {

    void createReport(Long productId, Double saleSum, Long storeId);

    List<SummaryDto> getReports(Integer page);

    List<SummaryDto> getByStore(Long storeId);

    List<SummaryDto> getByProduct(Long productId);

    List<SummaryDto> getByStoreForPeriod(Long storeId, LocalDateTime startDate, LocalDateTime finishDate);

    List<SummaryDto> getByProductForPeriod(Long productId, LocalDateTime startDate, LocalDateTime finishDate);

    List<ProductBySaleDto> getTopTenProducts();
}
