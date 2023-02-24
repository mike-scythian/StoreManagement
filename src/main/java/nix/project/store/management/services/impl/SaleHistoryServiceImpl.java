package nix.project.store.management.services.impl;

import lombok.RequiredArgsConstructor;
import nix.project.store.management.repositories.repoDto.ProductTotalPayment;
import nix.project.store.management.dto.SaleHistoryDto;
import nix.project.store.management.dto.mapper.SaleHistoryMapper;
import nix.project.store.management.models.SaleHistory;
import nix.project.store.management.repositories.SaleHistoryRepository;
import nix.project.store.management.services.SaleHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleHistoryServiceImpl implements SaleHistoryService {

    private final SaleHistoryRepository saleHistoryRepository;
    @Override
    public void createReport(Long productId, Double saleSum, Long storeId) {

        SaleHistory report = SaleHistory.builder()
                .product(productId)
                .payment(saleSum)
                .timeOperation(LocalDateTime.now())
                .store(storeId)
                .build();

        saleHistoryRepository.save(report);
    }

    @Override
    public List<SaleHistoryDto> getReports() {
        return saleHistoryRepository.findAll()
                .stream()
                .map(SaleHistoryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SaleHistoryDto> getByStore(Long storeId) {

        return saleHistoryRepository.findByStore(storeId)
                .stream()
                .map(SaleHistoryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SaleHistoryDto> getByProduct(Long productId) {
        return saleHistoryRepository.findByProduct(productId)
                .stream()
                .map(SaleHistoryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SaleHistoryDto> getByStoreForPeriod(Long storeId, LocalDateTime startDate, LocalDateTime finishDate) {
        return saleHistoryRepository.findByStoreAndTimeOperationBetween(storeId,startDate,finishDate)
                .stream()
                .map(SaleHistoryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SaleHistoryDto> getByProductForPeriod(Long productId, LocalDateTime startDate, LocalDateTime finishDate) {
        return saleHistoryRepository.findByProductAndTimeOperationBetween(productId,startDate,finishDate)
                .stream()
                .map(SaleHistoryMapper.MAPPER::toMap)
                .toList();
    }

   /* @Override
    public List<ProductTotalPayment> getTopTenProducts() {

        return saleHistoryRepository.findTopTenProducts();

    }*/


}
